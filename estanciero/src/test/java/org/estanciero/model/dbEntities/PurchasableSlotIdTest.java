package org.estanciero.model.dbEntities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchasableSlotIdTest {

    @Test
    void testGetSetBoardSlotId() {
        int expectedSlotId = 10;
        PurchasableSlotId slotId = new PurchasableSlotId();
        slotId.setBoardSlotId(expectedSlotId);

        assertEquals(expectedSlotId, slotId.getBoardSlotId());
    }

    @Test
    void testGetSetMatchId() {
        int expectedMatchId = 42;
        PurchasableSlotId slotId = new PurchasableSlotId();
        slotId.setMatchId(expectedMatchId);

        assertEquals(expectedMatchId, slotId.getMatchId());
    }

    @Test
    void testEquals() {

    }

    @Test
    void testHashCode() {
        PurchasableSlotId slotId1 = new PurchasableSlotId();
        PurchasableSlotId slotId2 = new PurchasableSlotId();

        assertEquals(slotId1.hashCode(), slotId2.hashCode());
    }
}