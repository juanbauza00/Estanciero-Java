package org.estanciero.model.dbEntities.AuxiliarEntities;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ZoneEntityTest {

    @Test
    void testGetterSetter() {
        ZoneEntity zone = new ZoneEntity();

        zone.setZoneId(1);
        assertEquals(1, zone.getZoneId());

        zone.setZoneName("Central");
        assertEquals("Central", zone.getZoneName());
    }

    @Test
    void testProvinceZoneEntities() {
        ZoneEntity zone = new ZoneEntity();
        Set<ProvinceZoneEntity> provinceZones = new HashSet<>();

        provinceZones.add(new ProvinceZoneEntity());
        zone.getProvinceZoneEntities().addAll(provinceZones);

        assertFalse(zone.getProvinceZoneEntities().isEmpty());
    }
}