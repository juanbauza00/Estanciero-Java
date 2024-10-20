package org.estanciero.model.entities.player;

import junit.framework.TestCase;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest  {

    private HumanPlayer player;

    @BeforeEach
    void setUp()
    {
         player = new HumanPlayer("Juan");
    }

    @Test
    void testIsPlayerTurn() {
        player.setPlayerTurn(true);
        assertTrue(player.isPlayerTurn());
    }

    @Test
    void testSetPlayerTurn() {
        player.setPlayerTurn(true);
        assertTrue(player.isPlayerTurn());
    }

    @Test
    void testGetPlayerNameWithColor() {
        player.setName("Pepas");
        assertEquals("\u001B[34mPepas\u001B[0m", player.getPlayerNameWithColor());
    }

    @Test
    void testGetName() {
        player.setName("Pepas");
        assertEquals("Pepas", player.getName());
    }

    @Test
    void testGetId() {
        player.setId(1);
        assertEquals(1, player.getId());
    }

    @Test
    void testGetMoney() {
        player.setMoney(1000);
        assertEquals(1000, player.getMoney());
    }

    @Test
    void testGetPosition() {
        player.setPosition(5);
        assertEquals(5, player.getPosition());
    }

    @Test
    void testIsInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
    }

    @Test
    void testGetOutOfJailCards() {
        List<SpecialCard> cards = new ArrayList<>();
        player.setOutOfJailCards(cards);
        assertEquals(cards, player.getOutOfJailCards());
    }

    @Test
    void testGetRailwayCount() {
        player.setRailwayCount(2);
        assertEquals(2, player.getRailwayCount());
    }

    @Test
    void testGetCompanyCount() {
        player.setCompanyCount(3);
        assertEquals(3, player.getCompanyCount());
    }

    @Test
    void testGetRestCount() {
        player.setRestCount(4);
        assertEquals(4, player.getRestCount());
    }

    @Test
    void testGetProvinceCount() {
        player.setProvinceCount(5);
        assertEquals(5, player.getProvinceCount());
    }

    @Test
    void testGetUpgradeCount() {
        player.setUpgradeCount(6);
        assertEquals(6, player.getUpgradeCount());
    }

    @Test
    void testIsBankrupt() {
        player.setBankrupt(true);
        assertTrue(player.isBankrupt());
    }

    @Test
    void testSetName() {
        player.setName("John");
        assertEquals("John", player.getName());
    }

    @Test
    void testSetId() {
        player.setId(1);
        assertEquals(1, player.getId());
    }

    @Test
    void testSetMoney() {
        player.setMoney(1000);
        assertEquals(1000, player.getMoney());
    }

    @Test
    void testSetPosition() {
        player.setPosition(5);
        assertEquals(5, player.getPosition());
    }

    @Test
    void testSetInJail() {
        player.setInJail(true);
        assertTrue(player.isInJail());
    }

    @Test
    void testSetOutOfJailCards() {
        List<SpecialCard> cards = new ArrayList<>();
        player.setOutOfJailCards(cards);
        assertEquals(cards, player.getOutOfJailCards());
    }

    @Test
    void testSetRailwayCount() {
        player.setRailwayCount(2);
        assertEquals(2, player.getRailwayCount());
    }

    @Test
    void testSetCompanyCount() {
        player.setCompanyCount(3);
        assertEquals(3, player.getCompanyCount());
    }

    @Test
    void testSetRestCount() {
        player.setRestCount(4);
        assertEquals(4, player.getRestCount());
    }

    @Test
    void testSetProvinceCount() {
        player.setProvinceCount(5);
        assertEquals(5, player.getProvinceCount());
    }

    @Test
    void testSetUpgradeCount() {
        player.setUpgradeCount(6);
        assertEquals(6, player.getUpgradeCount());
    }

    @Test
    void testSetBankrupt() {
        player.setBankrupt(true);
        assertTrue(player.isBankrupt());
    }
}