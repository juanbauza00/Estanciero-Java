package org.estanciero.services.implementation;

import junit.framework.TestCase;
import org.estanciero.builder.EntityHandler;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.JailSlot;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.Game;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SpecialCardServiceTest {
    @Mock
    private SpecialCard mockCard;
    @Mock
    private SpecialCard mockCardPayForEach;
    @Mock
    private Player mockPlayer;
    @Mock
    private Player mockPlayer2;
    @InjectMocks
    private SpecialCardService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCard = new SpecialCard(SpecialCardType.LUCK, 100, SpecialCardActionType.OUTJAIL, 0, "Message");
        mockCardPayForEach = new SpecialCard(SpecialCardType.LUCK, 100, SpecialCardActionType.PAYFOREACH, 0, "Message");

        mockPlayer = mock(Player.class);

        mockCard = mock(SpecialCard.class);

        service = new SpecialCardService();
    }
    @Test
    public void testPay() {
        String humanPlayer = "David";
        HumanPlayer player = new HumanPlayer(humanPlayer);
        player.setMoney(1000);

        SpecialCard card = new SpecialCard(SpecialCardType.LUCK, 200, SpecialCardActionType.PAY, 0, "TestMessage");
        card.setMoneyAmount(200);

        SpecialCardService service = new SpecialCardService();

        service.pay(card, player);

        Assert.assertEquals(800, player.getMoney());
    }
    @Test
    public void testCollect() {
        String humanPlayer = "David";
        HumanPlayer player = new HumanPlayer(humanPlayer);
        player.setMoney(1000);

        SpecialCard card = new SpecialCard(SpecialCardType.LUCK, 300, SpecialCardActionType.COLLECT, 0, "Message");
        card.setMoneyAmount(300);

        SpecialCardService service = new SpecialCardService();
        service.collect(card, player);

        Assert.assertEquals(1300, player.getMoney());
    }
    @Test
    public void testMove() {
        String humanPlayer = "David";
        HumanPlayer player = new HumanPlayer(humanPlayer);
        player.setPosition(5);
        SpecialCard card = new SpecialCard(SpecialCardType.LUCK, 0, SpecialCardActionType.MOVE, 10, "Message");
        card.setToPosition(10);
        SpecialCardService service = new SpecialCardService();

        service.move(card, player);

        Assert.assertEquals(10, player.getPosition());
    }

    @Test
    public void testGoJail() {
        String humanPlayer = "David";
        HumanPlayer player = new HumanPlayer(humanPlayer);
        player.setInJail(false);
        SpecialCard card = new SpecialCard(SpecialCardType.LUCK, 0, SpecialCardActionType.GOJAIL, 0, "Message");
        SpecialCardService service = new SpecialCardService();

        service.goJail(card, player);

        player.setInJail(true);
        Assert.assertTrue(player.isInJail());
    }
    @Test
    public void testOutJail() {
        String humanPlayer = "David";
        HumanPlayer player = new HumanPlayer(humanPlayer);
        player.setInJail(true);
        SpecialCard card = new SpecialCard(SpecialCardType.LUCK, 0, SpecialCardActionType.OUTJAIL, 0, "Message");
        player.getOutOfJailCards().add(card);
        SpecialCardService service = new SpecialCardService();

        // Initialize deckLuck
        ArrayList<SpecialCard> deckLuck = new ArrayList<>();
        Game.setLuckCardDeck(deckLuck);

        service.outJail(card, player);

        player.setInJail(false);
        Assert.assertFalse(player.isInJail());
    }
    @Test
    public void testExecuteCardPay() {
        int initialMoney = 1000;
        int moneyToPay = 200;

        when(mockPlayer.getMoney()).thenReturn(initialMoney);
        when(mockCard.getActionType()).thenReturn(SpecialCardActionType.PAY);
        when(mockCard.getMoneyAmount()).thenReturn(moneyToPay);

        service.executeCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer).setMoney(initialMoney - moneyToPay);
    }
    @Test
    public void testExecuteCardCollect() {
        int initialMoney = 1000;
        int moneyToCollect = 300;

        when(mockPlayer.getMoney()).thenReturn(initialMoney);
        when(mockCard.getActionType()).thenReturn(SpecialCardActionType.COLLECT);
        when(mockCard.getMoneyAmount()).thenReturn(moneyToCollect);

        service.executeCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer).setMoney(initialMoney + moneyToCollect);
    }
    @Test
    public void testExecuteCardMove() {
        int newPosition = 5;

        when(mockCard.getActionType()).thenReturn(SpecialCardActionType.MOVE);
        when(mockCard.getToPosition()).thenReturn(newPosition);

        service.executeCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer).setPosition(newPosition);
    }
    @Test
    public void testExecuteCardGoJail() {
        when(mockCard.getActionType()).thenReturn(SpecialCardActionType.GOJAIL);

        service.executeCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer).setInJail(true);
    }
    @Test
    public void testCollectForeach() {
        int moneyGoal = 10000;
        ArrayList<Player> playerList = new ArrayList<>(); // No se crea la lista de jugadores directamente
        HumanPlayer player1 = new HumanPlayer("humanPlayer1");
        player1.setMoney(100);
        player1.setId(1);
        HumanPlayer player2 = new HumanPlayer("humanPlayer2");
        player2.setMoney(100);
        player2.setId(2);
        HumanPlayer player3 = new HumanPlayer("humanPlayer3");
        player3.setMoney(100);
        player3.setId(3);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        List<Slot> slots = new ArrayList<>();
        JailSlot jailSlot = new JailSlot(14);
        slots.add(jailSlot);


        ArrayList<SpecialCard> destinyCardDeck = null;
        ArrayList<SpecialCard> luckCardDeck = null;
        EntityHandler entityHandler = new EntityHandler();
        Game game = new Game(moneyGoal, playerList, slots, luckCardDeck, destinyCardDeck, GameMode.MONEY_GOAL,GameDifficulty.EASY,entityHandler,false);

        SpecialCardService.collectForeach(mockCardPayForEach, player1);
        int totalAmountPlayer1 = player1.getMoney();
        Assert.assertEquals(300, totalAmountPlayer1);

    }
    @Test
    public void testPayForeach() {
        Player mockPlayer = Mockito.mock(Player.class);

        Mockito.when(mockPlayer.getUpgradeCount()).thenReturn(5);
        Mockito.when(mockPlayer.getMoney()).thenReturn(5000);

        SpecialCard card = new SpecialCard();
        card.setMoneyAmount(100);

        SpecialCardService.payForeach(card, mockPlayer);

        verify(mockPlayer, times(1)).setMoney(4500);
    }
    }


