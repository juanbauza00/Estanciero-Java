package org.estanciero.builder;
import org.apache.commons.lang3.tuple.Pair;
import org.estanciero.builder.mappers.PlayerMapper;
import org.estanciero.builder.mappers.SlotMapper;
import org.estanciero.builder.mappers.SpecialCardMapper;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.dbEntities.MatchEntity;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;
import org.estanciero.model.dbEntities.PurchasableSlotId;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.estanciero.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntityHandlerTest {

    @Mock
    private BoardSlotRepository boardSlotRepository;

    @Mock
    private PurchasableSlotRepository purchasableSlotRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private SpecialCardMapper cardMapper;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private SlotMapper slotMapper;

    @Mock
    List<SpecialCard> specialCardsList;

    @Mock
    List<Slot> notPurchSlotList;

    @Mock
    List<Slot> newPurchSlotList;

    @Mock
    List<Slot> savedPurchSlotList;


    @Mock
    GameDto savedGameDto;

    @InjectMocks
    private EntityHandler entityHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //Cards form new and saved GameDto
        specialCardsList = new ArrayList<>();
        List<SpecialCard> luckyCardList = new ArrayList<>();
        List<SpecialCard> destinyCardList = new ArrayList<>();

        SpecialCard card1 = new SpecialCard();
        card1.setId(1);
        card1.setCardType(SpecialCardType.LUCK);
        card1.setMoneyAmount(null);
        card1.setCardMessage("Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line");
        card1.setActionType(SpecialCardActionType.MOVE);
        card1.setToPosition(26);

        SpecialCard card2 = new SpecialCard();
        card2.setId(21);
        card2.setCardType(SpecialCardType.DESTINY);
        card2.setMoneyAmount(1000);
        card2.setCardMessage("You spent $1,000 on your insurance policy");
        card2.setActionType(SpecialCardActionType.PAY);
        card2.setToPosition(null);

        luckyCardList.add(card1);
        destinyCardList.add(card2);
        specialCardsList.add(card1);
        specialCardsList.add(card2);

        //Card for one of players
        SpecialCard playerCard = new SpecialCard();
        card1.setId(9);
        card1.setCardType(SpecialCardType.LUCK);
        card1.setMoneyAmount(null);
        card1.setCardMessage("Out of jail card");
        card1.setActionType(SpecialCardActionType.OUTJAIL);
        card1.setToPosition(null);

        //Player for savedGameDto
        //Human player model
        HumanPlayer humanPlayer = new HumanPlayer("humanEntity",1);
        humanPlayer.setMoney(1000);
        humanPlayer.setPosition(34);
        humanPlayer.setInJail(false);
        humanPlayer.setProvinceCount(3);
        humanPlayer.setRailwayCount(0);
        humanPlayer.setCompanyCount(0);
        humanPlayer.setUpgradeCount(1);
        humanPlayer.setRestCount(0);
        humanPlayer.setPlayerTurn(true);
        humanPlayer.setBankrupt(false);

        List<SpecialCard> specialCardHumanPlayerList = new ArrayList<>();
        specialCardHumanPlayerList.add(playerCard);
        humanPlayer.setOutOfJailCards(specialCardHumanPlayerList);

        //Bot player model
        BotPlayer botPlayer = new BotPlayer("botEntity", BotPlayStyle.AGGRESSIVE);
        botPlayer.setId(2);
        botPlayer.setMoney(2000);
        botPlayer.setPosition(14);
        botPlayer.setInJail(true);
        botPlayer.setProvinceCount(0);
        botPlayer.setRailwayCount(2);
        botPlayer.setCompanyCount(1);
        botPlayer.setUpgradeCount(0);
        botPlayer.setRestCount(0);
        botPlayer.setPlayerTurn(false);
        botPlayer.setBankrupt(true);

        List<Player> savedPlayers = new ArrayList<>(Arrays.asList(humanPlayer,botPlayer));
        //Players in newGameDto are null

        //Slots for newGameDto
        List<Slot> newSlotList = new ArrayList<>();
        notPurchSlotList = new ArrayList<>();

        Slot newJailSlot = new JailSlot(14);
        newSlotList.add(newJailSlot);
        notPurchSlotList.add(newJailSlot);

        Slot newRestSlot = new RestSlot(21);
        newSlotList.add(newRestSlot);
        notPurchSlotList.add(newRestSlot);

        Slot newTaxSlot = new TaxSlot(41,2000);
        newSlotList.add(newTaxSlot);
        notPurchSlotList.add(newTaxSlot);

        Slot newPrizeSlot = new PrizeSlot(7,2500);
        newSlotList.add(newPrizeSlot);
        notPurchSlotList.add(newPrizeSlot);

        newPurchSlotList = new ArrayList<>();
        ProvinceSlot newProvinceSlot = new ProvinceSlot(34);
        newProvinceSlot.setPurchasePrice(6400);
        int[] rentVal1 = {550, 2850, 8500, 19000, 23000, 27000};
        newProvinceSlot.setRentValue(rentVal1);
        newProvinceSlot.setProvinceZoneId(18);
        newProvinceSlot.setProvince(ProvinceName.CORDOBA);
        newProvinceSlot.setZone(ProvinceZone.NORTE);
        newProvinceSlot.setRanchCount(0);
        newSlotList.add(newProvinceSlot);
        newPurchSlotList.add(newProvinceSlot);

        //Railway Slot
        RailwaySlot newRailwaySlot = new RailwaySlot(12);
        newRailwaySlot.setPurchasePrice(3600);
        int[] rentVal2 = {500, 1000, 2000, 4000};
        newRailwaySlot.setRentValue(rentVal2);
        newSlotList.add(newRailwaySlot);
        newPurchSlotList.add(newRailwaySlot);



        //Slots for savedGameDto
        List<Slot> savedSlotModels = new ArrayList<>();
        savedSlotModels.add(newJailSlot);
        savedSlotModels.add(newRestSlot);
        savedSlotModels.add(newTaxSlot);
        savedSlotModels.add(newPrizeSlot);

        savedPurchSlotList = new ArrayList<>();

        ProvinceSlot savedProvinceSlot = new ProvinceSlot(34);
        savedProvinceSlot.setPurchasePrice(6400);
        savedProvinceSlot.setRentValue(rentVal1);
        savedProvinceSlot.setProvinceZoneId(18);
        savedProvinceSlot.setProvince(ProvinceName.CORDOBA);
        savedProvinceSlot.setZone(ProvinceZone.NORTE);
        savedProvinceSlot.setOwner(savedPlayers.get(0));
        savedProvinceSlot.setRanchCount(2);
        savedSlotModels.add(savedProvinceSlot);
        savedPurchSlotList.add(savedProvinceSlot);

        //Railway Slot
        RailwaySlot savedRailwaySlot = new RailwaySlot(12);
        savedRailwaySlot.setPurchasePrice(3600);
        savedRailwaySlot.setRentValue(rentVal2);
        savedRailwaySlot.setOwner(savedPlayers.get(1));
        savedSlotModels.add(savedRailwaySlot);
        savedPurchSlotList.add(savedRailwaySlot);

        savedGameDto = new GameDto();
        savedGameDto.setLuckCards(luckyCardList);
        savedGameDto.setDestinyCards(destinyCardList);
        savedGameDto.setPlayerList(savedPlayers);
        savedGameDto.setSlotList(savedSlotModels);
        savedGameDto.setNewGame(false);
        savedGameDto.setGameMode(GameMode.MONEY_GOAL);
        savedGameDto.setMoneyGoal(30000);
        savedGameDto.setGameDifficulty(GameDifficulty.MEDIUM);


    }

    @Test
    void testThereIsAnotherGame_True() {
        when(matchRepository.count()).thenReturn(2L);

        boolean result = entityHandler.thereIsAnotherGame();

        assertTrue(result);
        verify(matchRepository, times(1)).count();
    }

    @Test
    void testThereIsAnotherGame_False() {
        when(matchRepository.count()).thenReturn(1L);

        boolean result = entityHandler.thereIsAnotherGame();

        assertFalse(result);
        verify(matchRepository, times(1)).count();
    }

    @Test
    void testGetGameDto_NewGame() {
        //New game
        MatchEntity newMatch = new MatchEntity();
        newMatch.setMatchId(0);
        newMatch.setMatchDate(LocalDateTime.now());
        List<PurchasableSlotEntity> entityTestList = new ArrayList<>();
        PurchasableSlotId entityId = new PurchasableSlotId();
        entityId.setBoardSlotId(1);
        entityId.setMatchId(1);
        PurchasableSlotEntity entityTest = new PurchasableSlotEntity();
        entityTestList.add(entityTest);


        when(matchRepository.findById(0)).thenReturn(Optional.of(newMatch));
        when(cardRepository.findByMatchId(0)).thenReturn(Collections.emptyList());
        when(cardRepository.findByMatchId(1)).thenReturn(Collections.emptyList());
        when(cardMapper.mapEntityToModel(Collections.emptyList())).thenReturn(specialCardsList);
        when(playerMapper.mapEntityToModel(Collections.emptyList(), Collections.emptyList())).thenReturn(Pair.of(Collections.emptyList(),specialCardsList));
        when(boardSlotRepository.getNotPurchasableSlots()).thenReturn(Collections.emptyList());
        when(purchasableSlotRepository.findAllById_MatchId(0)).thenReturn(Collections.emptyList());
        when(slotMapper.mapPurchSlotEntityToModel(Collections.emptyList(), Optional.empty())).thenReturn(newPurchSlotList);
        when(slotMapper.mapNoPurchSlotEntityToModel(Collections.emptyList())).thenReturn(notPurchSlotList);

        GameDto gameDtoTest = entityHandler.getGameDto(true);

        assertNotNull(gameDtoTest);
        assertNull(gameDtoTest.getPlayerList());
        assertEquals(6, gameDtoTest.getSlotList().size());
        assertEquals(1, gameDtoTest.getLuckCards().size());
        assertEquals(1, gameDtoTest.getDestinyCards().size());

    }

    @Test
    void testGetGameDto_SavedGame() {

        MatchEntity match = new MatchEntity();
        match.setMatchDate(LocalDateTime.now());
        match.setMatchId(1);
        match.setGameMode(0);
        match.setGameDifficulty(1);
        match.setMoneyGoal(30000);

        when(matchRepository.findById(1)).thenReturn(Optional.of(match));
        when(playerRepository.findPlayerEntitiesByMatchId(1)).thenReturn(Collections.emptyList());
        when(cardRepository.findByMatchId(1)).thenReturn(Collections.emptyList());
        when(playerMapper.mapEntityToModel(anyList(), anyList())).thenReturn(Pair.of(savedGameDto.getPlayerList(),specialCardsList));
        when(boardSlotRepository.getNotPurchasableSlots()).thenReturn(Collections.emptyList());
        when(purchasableSlotRepository.findAllById_MatchId(1)).thenReturn(Collections.emptyList());
        when(slotMapper.mapPurchSlotEntityToModel(Collections.emptyList(), Optional.ofNullable(savedGameDto.getPlayerList()))).thenReturn(savedPurchSlotList);
        when(slotMapper.mapNoPurchSlotEntityToModel(Collections.emptyList())).thenReturn(notPurchSlotList);

        GameDto gameDto = entityHandler.getGameDto(false);

        assertNotNull(gameDto);
        assertEquals(2, gameDto.getPlayerList().size());
        assertEquals(6, gameDto.getSlotList().size());
        assertEquals(1, gameDto.getLuckCards().size());
        assertEquals(1, gameDto.getDestinyCards().size());
    }

    @Test
    void testSaveGame() {
        GameDto gameDto = savedGameDto;

        when(playerMapper.mapModelToEntity(anyList(), anyList(), anyInt())).thenReturn(Pair.of(Collections.emptyList(),Collections.emptyList()));
        when(slotMapper.mapPurchSlotModelToEntity(anyList(), anyInt())).thenReturn(Collections.emptyList());
        when(cardMapper.mapModelToEntity(anyList(), anyInt())).thenReturn(Collections.emptyList());

        boolean result = entityHandler.saveGame(gameDto);

        assertTrue(result);

        verify(playerMapper, times(1)).mapModelToEntity(anyList(), anyList(), anyInt());
        verify(slotMapper, times(1)).mapPurchSlotModelToEntity(anyList(), anyInt());
        verify(cardMapper, times(1)).mapModelToEntity(anyList(), anyInt());
        verify(playerRepository, times(1)).saveAll(anyList());
        verify(purchasableSlotRepository, times(1)).saveAll(anyList());
        verify(cardRepository, times(1)).saveAll(anyList());
        verify(matchRepository, times(1)).save(any());
    }

    @Test
    void testDeletePreviousGameData() {
        doNothing().when(purchasableSlotRepository).deleteByIdMatchId(1);
        doNothing().when(playerRepository).deleteByMatchId(1);
        doNothing().when(cardRepository).deleteByMatchId(1);
        doNothing().when(matchRepository).deleteById(1);

        entityHandler.deletePreviousGameData();

        verify(purchasableSlotRepository, times(1)).deleteByIdMatchId(1);
        verify(playerRepository, times(1)).deleteByMatchId(1);
        verify(cardRepository, times(1)).deleteByMatchId(1);
        verify(matchRepository, times(1)).deleteById(1);
    }
}
