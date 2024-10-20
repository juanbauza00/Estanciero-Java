package org.estanciero.model.entities.special_cards;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class SpecialCardTest {




    @Test
    public void testToString() {
        SpecialCard card = new SpecialCard();
        card.setCardType(SpecialCardType.LUCK);
        card.setMoneyAmount(100);
        card.setActionType(SpecialCardActionType.MOVE);
        card.setToPosition(13);
        card.setCardMessage("Test message");

        String expected = "TYPE=" + card.getCardType() +
                ", AMOUNT=" + card.getMoneyAmount() +
                ", MESSAGE='" + card.getCardMessage() + '\'' +
                ", ACTION=" + card.getActionType() +
                ", MOVE TO =" + card.getToPosition();

        assertEquals(expected, card.toString());
    }


   /* public void testDefaultConstructor() {
        SpecialCard card = new SpecialCard();

        assertNull(card.getCardType());
        assertEquals(0, card.getMoneyAmount());
        assertNull(card.getActionType());
        assertEquals(-1, card.getToPosition());
        assertEquals("", card.getCardMessage());
    }*/

}