package org.estanciero.builder.mappers;

import org.apache.commons.lang3.tuple.Pair;
import org.estanciero.model.dbEntities.PlayerEntity;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerMapperTest {
    @Mock
    private List<SpecialCard> cardList;
    @Mock
    private PlayerEntity humanEntity;
    @Mock
    private PlayerEntity botEntity;
    @Mock
    private Player humanPlayer;
    @Mock
    private Player botPlayer;

    @InjectMocks
    private PlayerMapper playerMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        SpecialCard card1 = new SpecialCard();
        card1.setId(9);
        card1.setCardType(SpecialCardType.LUCK);
        card1.setMoneyAmount(null);
        card1.setCardMessage("Out of jail card");
        card1.setActionType(SpecialCardActionType.OUTJAIL);
        card1.setToPosition(null);

        SpecialCard card2 = new SpecialCard();
        card2.setId(21);
        card2.setCardType(SpecialCardType.DESTINY);
        card2.setMoneyAmount(1000);
        card2.setCardMessage("You spent $1,000 on your insurance policy");
        card2.setActionType(SpecialCardActionType.PAY);
        card2.setToPosition(null);

        cardList = new ArrayList<>();
        cardList.add(card1);
        cardList.add(card2);

        //Human player entity
        humanEntity = new PlayerEntity();
        humanEntity.setPlayerName("humanEntity");
        humanEntity.setPlayerId(1);
        humanEntity.setPlayerMoney(1000);
        humanEntity.setPlayerPosition(34);
        humanEntity.setPlayerInJail(false);
        humanEntity.setProvinceCount(3);
        humanEntity.setRailwayCount(0);
        humanEntity.setCompanyCount(0);
        humanEntity.setUpgradeCount(1);
        humanEntity.setRestCount(0);
        humanEntity.setPlayerTurn(true);
        humanEntity.setBankrupt(false);
        humanEntity.setAmountOfFreeJailCards(1);
        humanEntity.setPlayerTypeId(0); // Human

        //Bot player entity
        botEntity = new PlayerEntity();
        botEntity.setPlayerName("botEntity");
        botEntity.setPlayerId(2);
        botEntity.setPlayerMoney(2000);
        botEntity.setPlayerPosition(14);
        botEntity.setPlayerInJail(true);
        botEntity.setProvinceCount(0);
        botEntity.setRailwayCount(2);
        botEntity.setCompanyCount(1);
        botEntity.setUpgradeCount(0);
        botEntity.setRestCount(0);
        botEntity.setPlayerTurn(false);
        botEntity.setBankrupt(true);
        botEntity.setAmountOfFreeJailCards(0);
        botEntity.setPlayerTypeId(1); // Bot
        botEntity.setPlayerStyleId(BotPlayStyle.AGGRESSIVE); // Bot style

        //Human player model
        humanPlayer = new HumanPlayer("humanEntity",1);
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
        specialCardHumanPlayerList.add(card1);
        humanPlayer.setOutOfJailCards(specialCardHumanPlayerList);

        //Bot player model
        botPlayer = new BotPlayer("botEntity", BotPlayStyle.AGGRESSIVE);
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
        List<SpecialCard> specialCardBotPlayerList = new ArrayList<>();
        botPlayer.setOutOfJailCards(specialCardBotPlayerList);
    }



    @Test
    void mapEntityToModel() {
        List<PlayerEntity> playerEntityList = new ArrayList<>();
        playerEntityList.add(humanEntity);
        playerEntityList.add(botEntity);
        Pair<List<Player>, List<SpecialCard>> result = playerMapper.mapEntityToModel(playerEntityList, cardList);

        List<Player> players = result.getLeft();
        List<SpecialCard> specialCards = result.getRight();
        SpecialCard card = specialCards.get(0);

        assertNotNull(players);
        assertEquals(2, players.size());
        assertNotNull(specialCards);
        assertEquals(1, specialCards.size());
        assertEquals(SpecialCardActionType.PAY, card.getActionType());

        HumanPlayer player1 = (HumanPlayer) players.get(0);
        assertEquals("humanEntity", player1.getName());
        assertEquals(1, player1.getId());
        assertEquals(1000, player1.getMoney());
        assertEquals(34, player1.getPosition());
        assertFalse(player1.isInJail());
        assertEquals(3, player1.getProvinceCount());
        assertEquals(0, player1.getRailwayCount());
        assertEquals(0, player1.getCompanyCount());
        assertEquals(1, player1.getUpgradeCount());
        assertEquals(0, player1.getRestCount());
        assertTrue(player1.isPlayerTurn());
        assertFalse(player1.isBankrupt());
        assertEquals(1, player1.getOutOfJailCards().size());
        assertEquals(1, player1.getOutOfJailCards().size());

        BotPlayer player2 = (BotPlayer) players.get(1);
        assertEquals("botEntity", player2.getName());
        assertEquals(2, player2.getId());
        assertEquals(2000, player2.getMoney());
        assertEquals(14, player2.getPosition());
        assertTrue(player2.isInJail());
        assertEquals(0, player2.getProvinceCount());
        assertEquals(2, player2.getRailwayCount());
        assertEquals(1, player2.getCompanyCount());
        assertEquals(0, player2.getUpgradeCount());
        assertEquals(0, player2.getRestCount());
        assertFalse(player2.isPlayerTurn());
        assertTrue(player2.isBankrupt());
        assertEquals(BotPlayStyle.AGGRESSIVE, player2.getBotPlayStyle());
    }

    @Test
    void mapModelToEntity() {
        List<Player> players = new ArrayList<>();
        players.add(humanPlayer);
        players.add(botPlayer);
        List<SpecialCard> cards = new ArrayList<>();
        humanEntity.setMatchId(1);
        botEntity.setMatchId(1);

        int matchId = 1;

        Pair<List<PlayerEntity>, List<SpecialCard>> result = playerMapper.mapModelToEntity(players, cards, matchId);

        List<PlayerEntity> playerEntityList = result.getLeft();
        List<SpecialCard> specialCards = result.getRight();
        SpecialCard card = specialCards.get(0);

        assertNotNull(playerEntityList);
        assertEquals(2, playerEntityList.size());
        assertNotNull(specialCards);
        assertEquals(1, specialCards.size());
        assertEquals(SpecialCardActionType.OUTJAIL, card.getActionType());

        PlayerEntity entity1 = playerEntityList.get(0);
        assertEquals("humanEntity", entity1.getPlayerName());
        assertEquals(1, entity1.getPlayerId());
        assertEquals(1000, entity1.getPlayerMoney());
        assertEquals(34, entity1.getPlayerPosition());
        assertFalse(entity1.isPlayerInJail());
        assertEquals(3, entity1.getProvinceCount());
        assertEquals(0, entity1.getRailwayCount());
        assertEquals(0, entity1.getCompanyCount());
        assertEquals(1, entity1.getUpgradeCount());
        assertEquals(0, entity1.getRestCount());
        assertTrue(entity1.isPlayerTurn());
        assertFalse(entity1.isBankrupt());
        assertEquals(1, entity1.getAmountOfFreeJailCards());
        assertEquals(0, entity1.getPlayerTypeId());
        assertEquals(1, entity1.getMatchId());

        PlayerEntity entity2 = playerEntityList.get(1);
        assertEquals("botEntity", entity2.getPlayerName());
        assertEquals(2, entity2.getPlayerId());
        assertEquals(2000, entity2.getPlayerMoney());
        assertEquals(14, entity2.getPlayerPosition());
        assertTrue(entity2.isPlayerInJail());
        assertEquals(0, entity2.getProvinceCount());
        assertEquals(2, entity2.getRailwayCount());
        assertEquals(1, entity2.getCompanyCount());
        assertEquals(0, entity2.getUpgradeCount());
        assertEquals(0, entity2.getRestCount());
        assertFalse(entity2.isPlayerTurn());
        assertTrue(entity2.isBankrupt());
        assertEquals(1, entity2.getPlayerTypeId());
        assertEquals(BotPlayStyle.AGGRESSIVE, entity2.getPlayerStyleId());
        assertEquals(1, entity2.getMatchId());

        assertTrue(reflectionEquals(humanEntity, playerEntityList.get(0)));
        assertTrue(reflectionEquals(botEntity, playerEntityList.get(1)));

    }
}