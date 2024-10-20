package org.estanciero.model.dbEntities.AuxiliarEntities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalPriceEntityTest {

    @Test
    void testGetterSetter() {
        RentalPriceEntity rentalPrice = new RentalPriceEntity();

        rentalPrice.setRentalPriceId(1);
        assertEquals(1, rentalPrice.getRentalPriceId());

        rentalPrice.setBoardSlotId(2);
        assertEquals(2, rentalPrice.getBoardSlotId());

        rentalPrice.setRentalCount(3);
        assertEquals(3, rentalPrice.getRentalCount());

        rentalPrice.setPrices(100);
        assertEquals(100, rentalPrice.getPrices());
    }

    @Test
    void testPriceCanBeNull() {
        RentalPriceEntity rentalPrice = new RentalPriceEntity();
        rentalPrice.setPrices(null);
        assertNull(rentalPrice.getPrices());
    }
}