package org.estanciero.model.action;

import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.SpecialCardSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class DrawSpecialCardActionTest  {


    SpecialCard specialCard;
    ArrayList<SpecialCard> luckCardDeck;
    ArrayList<SpecialCard> destinyCardDeck;
    Player playerTakeCard;
    SpecialCardSlot specialCardSlot;
    DrawSpecialCardAction drawSpecialCardAction;

    @BeforeEach
    void setUp()  {
        luckCardDeck= new ArrayList<>();
        destinyCardDeck= new ArrayList<>();
        specialCardSlot= new SpecialCardSlot(10);
        playerTakeCard= new HumanPlayer("Ricardo Fort");
        drawSpecialCardAction = new DrawSpecialCardAction(playerTakeCard,luckCardDeck);
    }

    @Test
    public void executeActionShouldDrawSpecialCard() {
        ArrayList<SpecialCard> deck = new ArrayList<>();
        SpecialCard specialCard = new SpecialCard(SpecialCardType.LUCK, 100, SpecialCardActionType.OUTJAIL, 19, "Test DrawSpecialCardAction");
        deck.add(specialCard);
        Player player = new HumanPlayer("Test Player");
        DrawSpecialCardAction drawSpecialCardAction = new DrawSpecialCardAction(player, deck);

        drawSpecialCardAction.executeAction();

        assertEquals(1, player.getOutOfJailCards().size());
    }

    @Test
    void drawSpecialCard()
    {
        specialCard=new SpecialCard(SpecialCardType.DESTINY,100, SpecialCardActionType.COLLECT,12,"Test DrawSpecialCardAction");
        destinyCardDeck.add(specialCard);
        DrawSpecialCardAction drawSpecialCardAction = new DrawSpecialCardAction(playerTakeCard,destinyCardDeck);
        SpecialCard specialCardExpected = drawSpecialCardAction.drawSpecialCard();
        SpecialCard specialCardActual = specialCard;
        assertEquals(specialCardExpected,specialCardActual);

    }
    @Test
    void drawSpecialCardLuck()
    {
        specialCard=new SpecialCard(SpecialCardType.LUCK,100, SpecialCardActionType.COLLECTFOREACH,19,"Test DrawSpecialCardAction");
        luckCardDeck.add(specialCard);
        DrawSpecialCardAction drawSpecialCardAction = new DrawSpecialCardAction(playerTakeCard,luckCardDeck);
        SpecialCard specialCardExpected = drawSpecialCardAction.drawSpecialCard();
        SpecialCard specialCardActual = specialCard;
        assertEquals(specialCardExpected,specialCardActual);

    }

    @Test
    public void testGetSetPlayerTakeCard() {
        Player mockPlayer = Mockito.mock(Player.class);
        drawSpecialCardAction.setPlayerTakeCard(mockPlayer);
        assertEquals(mockPlayer, drawSpecialCardAction.getPlayerTakeCard());
    }

    @Test
    public void testGetSetSpecialCard() {
        SpecialCard mockSpecialCard = Mockito.mock(SpecialCard.class);
        drawSpecialCardAction.setSpecialCard(mockSpecialCard);
        assertEquals(mockSpecialCard, drawSpecialCardAction.getSpecialCard());
    }

    @Test
    public void testGetSetDeck() {
        ArrayList<SpecialCard> mockDeck = new ArrayList<>();
        SpecialCard mockSpecialCard1 = Mockito.mock(SpecialCard.class);
        SpecialCard mockSpecialCard2 = Mockito.mock(SpecialCard.class);
        mockDeck.add(mockSpecialCard1);
        mockDeck.add(mockSpecialCard2);
        drawSpecialCardAction.setDeck(mockDeck);
        assertEquals(mockDeck, drawSpecialCardAction.getDeck());
    }

    @Test
    public void testRandom() {
        Random random = drawSpecialCardAction.getRandom();
        assertEquals(random, drawSpecialCardAction.getRandom());
    }
}