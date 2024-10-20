package org.estanciero.model.action.slot_action;

import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class UpgradeProvinceActionTest {

    @Test
    public void executeAction() {
        HumanPlayer testPlayer = new HumanPlayer("Test Player");
        testPlayer.setMoney(800);
        ProvinceSlot testSlot = new ProvinceSlot(1);
        testSlot.setOwner(testPlayer);
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        action.executeAction();

        assertEquals(800, testPlayer.getMoney());
        assertEquals(1, testSlot.getRanchCount());
    }

    @Test
    public void upgradeSlot() {
        HumanPlayer testPlayer = new HumanPlayer("Test Player");
        testPlayer.setMoney(800);
        ProvinceSlot testSlot = new ProvinceSlot(1);
        testSlot.setOwner(testPlayer);
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        action.upgradeSlot();

        assertEquals(800, testPlayer.getMoney());
        assertEquals(1, testSlot.getRanchCount());
    }

    @Test
    public void testGetSlot() {
        ProvinceSlot testSlot = new ProvinceSlot();
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        assertNotNull(action.getSlot());
        assertEquals(testSlot, action.getSlot());
    }

    @Test
    public void testGetOwner() {
        HumanPlayer testPlayer = new HumanPlayer("Test Player");
        ProvinceSlot testSlot = new ProvinceSlot(1);
        testSlot.setOwner(testPlayer);
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        assertEquals(testPlayer, action.getOwner());
    }

    @Test
    public void testGetRanchPrice() {
        ProvinceSlot testSlot = new ProvinceSlot(1);
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        Assertions.assertEquals(0, action.getRanchPrice());
    }

    @Test
    public void testSetSlot() {
        ProvinceSlot testSlot1 = new ProvinceSlot();
        ProvinceSlot testSlot2 = new ProvinceSlot();
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot1);

        action.setSlot(testSlot2);

        assertEquals(testSlot2, action.getSlot());
    }

    @Test
    public void testSetOwner() {
        Player testPlayer2 = new HumanPlayer("Test Player 2");
        ProvinceSlot testSlot = new ProvinceSlot();
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        action.setOwner(testPlayer2);

        assertEquals(testPlayer2, action.getOwner());
    }

    @Test
    public void testSetRanchPrice() {
        ProvinceSlot testSlot = new ProvinceSlot();
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        action.setRanchPrice(150);

        assertEquals(150, action.getRanchPrice());
    }

    @Test
    public void testSetDisplayMessage() {
        ProvinceSlot testSlot = new ProvinceSlot(1);
        UpgradeProvinceAction action = new UpgradeProvinceAction(testSlot);

        action.setDisplayMessage();

        String expectedMessage = "Upgrade the Province in position " + testSlot.getPosition() +
                "|  Province: " + testSlot.getProvince() + " Zone: " + testSlot.getZone() +
                " Price: " + testSlot.getRanchPrice();
        assertEquals(expectedMessage, action.displayMessage);
    }
}