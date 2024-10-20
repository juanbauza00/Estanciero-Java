package org.estanciero.builder;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.estanciero.builder.mappers.PlayerMapper;
import org.estanciero.builder.mappers.SlotMapper;
import org.estanciero.builder.mappers.SpecialCardMapper;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.dbEntities.*;
import org.estanciero.model.dbEntities.AuxiliarEntities.ProvinceZoneEntity;
import org.estanciero.model.dbEntities.AuxiliarEntities.RentalPriceEntity;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.estanciero.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@NoArgsConstructor
@Service
public class EntityHandler {
    @Autowired
    private BoardSlotRepository boardSlotRepository;

    @Autowired
    private PurchasableSlotRepository purchasableSlotRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PlayerRepository playerRepository;



    SpecialCardMapper cardMapper = new SpecialCardMapper();
    PlayerMapper playerMapper = new PlayerMapper();

    @Autowired
    SlotMapper slotMapper = new SlotMapper();





    //Revisa la cantidad de partidas si hay mas de 1 (datos base) es porque existe una partida guardada
    public boolean thereIsAnotherGame(){
        boolean existsAnotherGame = false;
        if(matchRepository.count() > 1) existsAnotherGame = true;
        return existsAnotherGame;
    }


    //OBTIENE LOS DATOS PARA COMENZAR UNA PARTIDA( NUEVA O GUARDADA)
    public GameDto getGameDto(Boolean isNewGame){
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        List<Player> playerList = null;

        //MatchId = 0 significa nueva partida
        int matchId = 0;
        if(isNewGame == false) matchId = 1;
        GameDto game = new GameDto();
        game.setNewGame(isNewGame);

        Optional<MatchEntity> match = matchRepository.findById(matchId);
        if(matchId > 0){
            //MoneyGoal
            game.setMoneyGoal(match.get().getMoneyGoal());

            //GameMode
            Integer gameModeVal = match.get().getGameMode();
            if(gameModeVal == 0) game.setGameMode(GameMode.MONEY_GOAL);
            if(gameModeVal == 1) game.setGameMode(GameMode.LASTPLAYER);

            //GameDifficulty
            Integer gameDiffVal = match.get().getGameDifficulty();
            if(gameDiffVal == 0) game.setGameDifficulty(GameDifficulty.HARD);
            if(gameDiffVal == 1) game.setGameDifficulty(GameDifficulty.MEDIUM);
            if(gameDiffVal == 2) game.setGameDifficulty(GameDifficulty.EASY);

            playerEntityList = playerRepository.findPlayerEntitiesByMatchId(matchId);
        }
        if(matchId == 0){
           // matchRepository.deleteById(1);
//            cardRepository.deleteAllByMatchId(1);
//            purchasableSlotRepository.deleteAllByIdMatches(1);
//            playerRepository.deleteAllByMatchId(1);
        }


        //Special Cards
        List<SpecialCard> specialCardFirstList = cardMapper.mapEntityToModel(cardRepository.findByMatchId(matchId));
        List<SpecialCard> specialCardDefinitiveList = new ArrayList<>();
        if(match.get().getMatchId() == 1){
            //Players
            Pair<List<Player>, List<SpecialCard>> playersAndCards = playerMapper.mapEntityToModel(playerEntityList,specialCardFirstList);
            playerList = playersAndCards.getLeft();
            specialCardDefinitiveList = playersAndCards.getRight();
        }
        if(match.get().getMatchId() == 0){
            specialCardDefinitiveList = specialCardFirstList;
        }


        //Slots
        List<BoardSlotEntity> notPurchSlots = boardSlotRepository.getNotPurchasableSlots();
        List<Slot> gameSlots = slotMapper.mapNoPurchSlotEntityToModel(notPurchSlots);
        if (match.isPresent()){
            List<PurchasableSlotEntity> purchasableSlotEntities = new ArrayList<>(purchasableSlotRepository.findAllById_MatchId(matchId));
            List<Slot> purchasableSlots = slotMapper.mapPurchSlotEntityToModel(purchasableSlotEntities, Optional.ofNullable(playerList));
            gameSlots.addAll(purchasableSlots) ;
        }


        Collections.sort(gameSlots, Comparator.comparing(Slot::getPosition));
        List<SpecialCard> luckyCards = new ArrayList<>();
        List<SpecialCard> destinyCards = new ArrayList<>();
        for(SpecialCard specialCard : specialCardDefinitiveList){
            if(specialCard.getCardType() == SpecialCardType.LUCK) luckyCards.add(specialCard);
            if(specialCard.getCardType() == SpecialCardType.DESTINY) destinyCards.add(specialCard);
        }

        game.setPlayerList(playerList);
        game.setSlotList(gameSlots);
        game.setLuckCards(luckyCards);
        game.setDestinyCards(destinyCards);
        return game;
    }

    public Boolean saveGame(GameDto game){
        boolean savedCorrectly = true;
        //Mapeo & Guardado de Match
        MatchEntity match = new MatchEntity();
        match.setMatchId(1);
        match.setMoneyGoal(game.getMoneyGoal());
        match.setGameMode(game.getGameMode().ordinal());
        match.setGameDifficulty(game.getGameDifficulty().ordinal());
        match.setMatchDate(LocalDateTime.now());

        deletePreviousGameData();
        MatchEntity savedMatch = matchRepository.save(match);

        List<PurchasableSlot> purchSlotList = game.getPurchSlots();
        List<SpecialCard> specialCardList = game.getLuckCards();
        specialCardList.addAll(game.getDestinyCards());
        List<Player> playerList = game.getPlayerList();

        //Mapeo & Guardado de players
        Pair<List<PlayerEntity>, List<SpecialCard>> playersAndCards = playerMapper.mapModelToEntity(playerList,specialCardList,1);
        List<PlayerEntity> playerEntityList = playersAndCards.getLeft();
        specialCardList = playersAndCards.getRight();
        List<PlayerEntity> savedPlayers = playerRepository.saveAll(playerEntityList);

        //Mapeo & Guardado de PurchasableSlots
        List<PurchasableSlotEntity> slotEntityList = slotMapper.mapPurchSlotModelToEntity(purchSlotList,1);
        List<PurchasableSlotEntity> savedPurchasableSlots = purchasableSlotRepository.saveAll(slotEntityList);


        //Mapeo & Guardado de cards
        List<SpecialCardEntity> specialCardEntityList = cardMapper.mapModelToEntity(specialCardList, 1);
        List<SpecialCardEntity> savedCards = cardRepository.saveAll(specialCardEntityList);

        if(savedPurchasableSlots == null || savedPlayers == null || savedCards == null) savedCorrectly = false;
        return savedCorrectly;
    }

    //Elimina los datos de purchSlots, Players, cards y match de la anterior partida.
    public void deletePreviousGameData(){
        purchasableSlotRepository.deleteByIdMatchId(1);
        playerRepository.deleteByMatchId(1);
        cardRepository.deleteByMatchId(1);
        matchRepository.deleteById(1);
    }


}
