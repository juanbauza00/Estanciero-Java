package org.estanciero.model.dbEntities.AuxiliarEntities;

import org.estanciero.model.dbEntities.PurchasableSlotEntity;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProvinceZoneEntityTest {

    @Test
    void testGetterSetter() {
        ProvinceZoneEntity provinceZone = new ProvinceZoneEntity();

        provinceZone.setProvinceZoneId(1);
        assertEquals(1, provinceZone.getProvinceZoneId());

        ZoneEntity zone = new ZoneEntity();
        provinceZone.setZone(zone);
        assertEquals(zone, provinceZone.getZone());

        provinceZone.setProvince("Cordoba");
        assertEquals("Cordoba", provinceZone.getProvince());

        provinceZone.setRanchPrice(10000);
        assertEquals(10000, provinceZone.getRanchPrice());

        Set<PurchasableSlotEntity> purchasableSlots = new HashSet<>();
        provinceZone.setPurchasableSlotEntities(purchasableSlots);
        assertEquals(purchasableSlots, provinceZone.getPurchasableSlotEntities());
    }

    @Test
    void testCollections() {
        ProvinceZoneEntity provinceZone = new ProvinceZoneEntity();

        Set<PurchasableSlotEntity> purchasableSlots = new HashSet<>();
        purchasableSlots.add(new PurchasableSlotEntity());

        provinceZone.getPurchasableSlotEntities().addAll(purchasableSlots);
        assertFalse(provinceZone.getPurchasableSlotEntities().isEmpty());
    }
}