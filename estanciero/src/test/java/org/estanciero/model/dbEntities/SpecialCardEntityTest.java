package org.estanciero.model.dbEntities;

import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCardEntityTest {

    @Test
    void testGetSetSpecialCardId() {
        int expectedId = 5;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setSpecialCardId(expectedId);

        assertEquals(expectedId, cardEntity.getSpecialCardId());
    }

    @Test
    void testGetSetBoardSlotTypeId() {
        int expectedTypeId = 2;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setBoardSlotTypeId(expectedTypeId);

        assertEquals(expectedTypeId, cardEntity.getBoardSlotTypeId());
    }

    @Test
    void testGetSetCardMessage() {
        String expectedMessage = "Get out of jail free!";
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setCardMessage(expectedMessage);

        assertEquals(expectedMessage, cardEntity.getCardMessage());
    }

    @Test
    void testGetSetAmount() {
        Integer expectedAmount = null;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setAmount(expectedAmount);

        assertNull(cardEntity.getAmount());

        expectedAmount = 200;
        cardEntity.setAmount(expectedAmount);
        assertEquals(expectedAmount, cardEntity.getAmount());
    }

    @Test
    void testGetSetToPosition() {
        Integer expectedPosition = null;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setToPosition(expectedPosition);

        assertNull(cardEntity.getToPosition());

        expectedPosition = 0;
        cardEntity.setToPosition(expectedPosition);
        assertEquals(expectedPosition, cardEntity.getToPosition());
    }

    @Test
    void testGetSetActionTypeId() {
        SpecialCardActionType expectedType = SpecialCardActionType.GOJAIL;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setActionTypeId(expectedType);

        assertEquals(expectedType, cardEntity.getActionTypeId());
    }

    @Test
    void testGetSetMatchId() {
        int expectedMatchId = 123;
        SpecialCardEntity cardEntity = new SpecialCardEntity();
        cardEntity.setMatchId(expectedMatchId);

        assertEquals(expectedMatchId, cardEntity.getMatchId());
    }
}