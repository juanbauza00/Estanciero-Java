package org.estanciero.model.action.slot_action;

import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.CompanySlot;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.RailwaySlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellSlotActionTest {
    @Test
    void sellProvinceSlotReducesPlayerProvinceCountAndIncreasesMoneyCorrectly() {
        Player player = new HumanPlayer("Peon");
        player.setProvinceCount(3);
        player.setMoney(10000);
        ProvinceSlot provinceSlot = new ProvinceSlot(1, 5000, ProvinceName.BSAS, ProvinceZone.CENTRO, 3000, new int[]{100, 200, 300});
        provinceSlot.setOwner(player);
        int sellValue = provinceSlot.getSellValue(); // Assuming this method exists and returns half of purchase price
        SellSlotAction sellSlotAction = new SellSlotAction(provinceSlot, player);
        sellSlotAction.executeAction();
        Assertions.assertEquals(2, player.getProvinceCount());
        Assertions.assertEquals(10000 + sellValue, player.getMoney());
    }

    @Test
    void sellCompanySlotReducesPlayerCompanyCountAndIncreasesMoneyCorrectly() {
        Player player = new HumanPlayer("Peon");
        player.setCompanyCount(2);
        player.setMoney(10000);
        int[] rentValue = {100, 200, 300, 400, 500};
        CompanySlot companySlot = new CompanySlot(2, 4000, rentValue);
        companySlot.setOwner(player);
        int sellValue = companySlot.getSellValue(); // Assuming this method exists and returns half of purchase price
        SellSlotAction sellSlotAction = new SellSlotAction(companySlot, player);
        sellSlotAction.executeAction();
        Assertions.assertEquals(1, player.getCompanyCount());
        Assertions.assertEquals(10000 + sellValue, player.getMoney());
    }

    @Test
    void sellRailwaySlotReducesPlayerRailwayCountAndIncreasesMoneyCorrectly() {
        Player player = new HumanPlayer("Peon");
        player.setRailwayCount(1);
        player.setMoney(10000);
        int[] rentValue = {100, 200, 300, 400, 500};
        RailwaySlot railwaySlot = new RailwaySlot(3, 6000, rentValue);
        railwaySlot.setOwner(player);
        int sellValue = railwaySlot.getSellValue(); // Assuming this method exists and returns half of purchase price
        SellSlotAction sellSlotAction = new SellSlotAction(railwaySlot, player);
        sellSlotAction.executeAction();
        Assertions.assertEquals(0, player.getRailwayCount());
        Assertions.assertEquals(10000 + sellValue, player.getMoney());
    }
    @Test
    void sellSlotSetsOwnerToNull() {
        Player player = new HumanPlayer("Peon");
        player.setMoney(10000);
        ProvinceSlot provinceSlot = new ProvinceSlot(1, 5000, ProvinceName.BSAS, ProvinceZone.CENTRO, 3000, new int[]{100, 200, 300});
        provinceSlot.setOwner(player);
        SellSlotAction sellSlotAction = new SellSlotAction(provinceSlot, player);
        sellSlotAction.executeAction();
        Assertions.assertNull(provinceSlot.getOwner());
    }
}