package org.estanciero.dtos;

import org.estanciero.model.entities.slot.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDtoTest {


    @Test
    void testGetPurchSlots() {

        Slot slot1 = new ProvinceSlot(1); //Purchasable
        Slot slot2 = new ProvinceSlot(2); //Purchasable
        Slot slot3 = new RailwaySlot(3); //Purchasable
        Slot slot4 = new CompanySlot(4); //Purchasable
        Slot slot5 = new JailSlot(5);
        Slot slot6 = new RestSlot(6);
        Slot slot7 = new TaxSlot(7);
        Slot slot8 = new PrizeSlot(8,500);

        List<Slot> slots = new ArrayList<>(Arrays.asList(
                slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8
        ));

        GameDto gameDto = new GameDto();
        gameDto.setSlotList(slots);

        List<PurchasableSlot> purchasableSlots = gameDto.getPurchSlots();

        assertNotNull(purchasableSlots);
        assertEquals(4, purchasableSlots.size());

        assertInstanceOf(ProvinceSlot.class,purchasableSlots.get(0));
        assertInstanceOf(ProvinceSlot.class,purchasableSlots.get(1));
        assertInstanceOf(RailwaySlot.class,purchasableSlots.get(2));
        assertInstanceOf(CompanySlot.class,purchasableSlots.get(3));
    }

    @Test
    void testIsNewGameTrue() {
        GameDto gameDto = new GameDto();
        gameDto.setNewGame(true);
        assertTrue(gameDto.isNewGame());
    }

}