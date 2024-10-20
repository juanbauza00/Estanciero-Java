package org.estanciero.model.dbEntities;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoardSlotEntityTest {

    @Test
    void testGetBoardSlotId() {
        BoardSlotEntity entity = new BoardSlotEntity(1, 2, 100, new HashSet<>());

        int boardSlotId = entity.getBoardSlotId();

        assertEquals(1, boardSlotId, "getBoardSlotId should return the set value");
    }

    @Test
    void testGetBoardSlotTypeId() {
        BoardSlotEntity entity = new BoardSlotEntity(1, 2, 100, new HashSet<>());

        int boardSlotTypeId = entity.getBoardSlotTypeId();

        assertEquals(2, boardSlotTypeId, "getBoardSlotTypeId should return the set value");
    }

    @Test
    void testGetAmountPrice() {
        BoardSlotEntity entity = new BoardSlotEntity(1, 2, 100, new HashSet<>());

        Integer amountPrice = entity.getAmountPrice();

        assertEquals(100, amountPrice, "getAmountPrice should return the set value");
    }

    @Test
    void testGetPurchasableSlotEntities() {
        Set<PurchasableSlotEntity> purchasableSlots = new HashSet<>();
        purchasableSlots.add(new PurchasableSlotEntity());
        BoardSlotEntity entity = new BoardSlotEntity(1, 2, 100, purchasableSlots);

        Set<PurchasableSlotEntity> retrievedSlots = entity.getPurchasableSlotEntities();

        assertEquals(purchasableSlots, retrievedSlots, "getPurchasableSlotEntities should return the same set");
    }

    @Test
    void testSetBoardSlotId() {
        BoardSlotEntity entity = new BoardSlotEntity();

        entity.setBoardSlotId(3);

        assertEquals(3, entity.getBoardSlotId(), "setBoardSlotId should update the value");
    }

    @Test
    void testSetBoardSlotTypeId() {
        BoardSlotEntity entity = new BoardSlotEntity();

        entity.setBoardSlotTypeId(4);

        assertEquals(4, entity.getBoardSlotTypeId(), "setBoardSlotTypeId should update the value");
    }

    @Test
    void testSetAmountPrice() {
        BoardSlotEntity entity = new BoardSlotEntity();

        entity.setAmountPrice(200);

        assertEquals(200, entity.getAmountPrice(), "setAmountPrice should update the value");
    }

    @Test
    void testSetPurchasableSlotEntities() {
        Set<PurchasableSlotEntity> purchasableSlots = new HashSet<>();
        purchasableSlots.add(new PurchasableSlotEntity());
        BoardSlotEntity entity = new BoardSlotEntity();

        entity.setPurchasableSlotEntities(purchasableSlots);

        assertEquals(purchasableSlots, entity.getPurchasableSlotEntities(), "setPurchasableSlotEntities should update the reference");
    }
}