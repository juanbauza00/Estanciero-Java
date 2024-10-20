package org.estanciero.builder.mappers;

import lombok.NoArgsConstructor;
import org.estanciero.model.dbEntities.PlayerEntity;
import org.estanciero.model.dbEntities.SpecialCardEntity;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor
public class PlayerMapper {

    public Pair<List<Player>, List<SpecialCard>> mapEntityToModel(List<PlayerEntity> playerEntityList, List<SpecialCard> gameSpecialCards){
        List<Player> players = new ArrayList<>();
        List<SpecialCard> specialCards = gameSpecialCards;
        for(PlayerEntity playerEntity : playerEntityList){
            String playerName = playerEntity.getPlayerName();
            int playerId = playerEntity.getPlayerId();
            int playerMoney = playerEntity.getPlayerMoney();
            int position = playerEntity.getPlayerPosition();
            Boolean isInJail = playerEntity.isPlayerInJail();
            int provinceCount = playerEntity.getProvinceCount();
            int railwayCount = playerEntity.getRailwayCount();
            int companyCount = playerEntity.getCompanyCount();
            int upgradeCount = playerEntity.getUpgradeCount();
            int restCount = playerEntity.getRestCount();
            Boolean isPlayerTurn = playerEntity.isPlayerTurn();
            Boolean isBankrupt = playerEntity.isBankrupt();
            int amountOfFreeJailCards = playerEntity.getAmountOfFreeJailCards();
            List<SpecialCard> outOfJailCards = new ArrayList<>();

            Iterator<SpecialCard> cardIterator = specialCards.iterator();
            while (cardIterator.hasNext() && amountOfFreeJailCards > 0) {
                SpecialCard specialCard = cardIterator.next();
                if (specialCard.getActionType() == SpecialCardActionType.OUTJAIL) {
                    outOfJailCards.add(specialCard);
                    cardIterator.remove();
                    amountOfFreeJailCards--;
                }
            }

            

            switch (playerEntity.getPlayerTypeId()){
                case 0: // Human
                    HumanPlayer humanPlayer = new HumanPlayer(playerName,playerId);
                    humanPlayer.setMoney(playerMoney);
                    humanPlayer.setPosition(position);
                    humanPlayer.setInJail(isInJail);
                    humanPlayer.setOutOfJailCards(outOfJailCards);
                    humanPlayer.setProvinceCount(provinceCount);
                    humanPlayer.setRailwayCount(railwayCount);
                    humanPlayer.setCompanyCount(companyCount);
                    humanPlayer.setUpgradeCount(upgradeCount);
                    humanPlayer.setRestCount(restCount);
                    humanPlayer.setPlayerTurn(isPlayerTurn);
                    humanPlayer.setBankrupt(isBankrupt);
                    players.add(humanPlayer);
                    break;
                case 1: // Bot
                    BotPlayer botPlayer = new BotPlayer(playerName,playerId, playerEntity.getPlayerStyleId());
                    botPlayer.setMoney(playerMoney);
                    botPlayer.setPosition(position);
                    botPlayer.setInJail(isInJail);
                    botPlayer.setOutOfJailCards(outOfJailCards);
                    botPlayer.setProvinceCount(provinceCount);
                    botPlayer.setRailwayCount(railwayCount);
                    botPlayer.setCompanyCount(companyCount);
                    botPlayer.setUpgradeCount(upgradeCount);
                    botPlayer.setRestCount(restCount);
                    botPlayer.setPlayerTurn(isPlayerTurn);
                    botPlayer.setBankrupt(isBankrupt);
                    players.add(botPlayer);
                    break;
            }
        }
        return Pair.of(players,specialCards);
    }

    public Pair<List<PlayerEntity>, List<SpecialCard>> mapModelToEntity(List<Player> players, List<SpecialCard> specialCardList, int matchId){
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        List<SpecialCard> specialCards = specialCardList;
        for(Player player : players){
            PlayerEntity playerEntity = new PlayerEntity();
            playerEntity.setPlayerId(player.getId());
            playerEntity.setPlayerName(player.getName());
            playerEntity.setPlayerMoney(player.getMoney());
            playerEntity.setPlayerPosition(player.getPosition());
            playerEntity.setPlayerInJail(player.isInJail());
            playerEntity.setProvinceCount(player.getProvinceCount());
            playerEntity.setRailwayCount(player.getRailwayCount());
            playerEntity.setCompanyCount(player.getCompanyCount());
            playerEntity.setUpgradeCount(player.getUpgradeCount());
            playerEntity.setRestCount(player.getRestCount());
            playerEntity.setPlayerTurn(player.isPlayerTurn());
            playerEntity.setBankrupt(player.isBankrupt());
            playerEntity.setMatchId(matchId);
            if(player instanceof HumanPlayer){
                playerEntity.setPlayerTypeId(0);
                playerEntity.setPlayerStyleId(null);
            }
            if(player instanceof BotPlayer){
                playerEntity.setPlayerTypeId(1);
                BotPlayer botPlayer = (BotPlayer)player;
                playerEntity.setPlayerStyleId(botPlayer.getBotPlayStyle());
            }
            if(player.getOutOfJailCards() != null){
                int freeJailCardsCount = 0;
                for(SpecialCard specialCard : player.getOutOfJailCards()){
                    specialCards.add(specialCard);
                    freeJailCardsCount ++;
                }
                playerEntity.setAmountOfFreeJailCards(freeJailCardsCount);
            }
            playerEntityList.add(playerEntity);
        }
        return Pair.of(playerEntityList, specialCards);
    }
}
