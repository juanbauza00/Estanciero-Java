package org.estanciero.model.dbEntities;

import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchasableSlotEntityTest {

    @Test
    void testGetSetId() {
        PurchasableSlotId expectedId = new PurchasableSlotId();
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setId(expectedId);

        assertEquals(expectedId, entity.getId());
    }

    @Test
    void testGetSetBoardSlotTypeId() {
        int expectedTypeId = 1;
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setBoardSlotTypeId(expectedTypeId);

        assertEquals(expectedTypeId, entity.getBoardSlotTypeId());
    }

    @Test
    void testGetSetProvinceZoneId() {
        Integer expectedZoneId = 2;
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setProvinceZoneId(expectedZoneId);

        assertEquals(expectedZoneId, entity.getProvinceZoneId());
    }

    @Test
    void testGetSetOwnerId() {
        Integer expectedOwnerId = null;
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setOwnerId(expectedOwnerId);

        assertNull(entity.getOwnerId());

        expectedOwnerId = 123;
        entity.setOwnerId(expectedOwnerId);
        assertEquals(expectedOwnerId, entity.getOwnerId());
    }

    @Test
    void testGetSetRanchCount() {
        int expectedRanchCount = 3;
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setRanchCount(expectedRanchCount);

        assertEquals(expectedRanchCount, entity.getRanchCount());
    }

    @Test
    void testGetSetPurchasePrice() {
        int expectedPrice = 5000;
        PurchasableSlotEntity entity = new PurchasableSlotEntity();
        entity.setPurchasePrice(expectedPrice);

        assertEquals(expectedPrice, entity.getPurchasePrice());
    }
}