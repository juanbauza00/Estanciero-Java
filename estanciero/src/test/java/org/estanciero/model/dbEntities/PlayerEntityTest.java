package org.estanciero.model.dbEntities;

import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerEntityTest {

    @Test
    void testGetSetPlayerId() {
        int expectedId = 123;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerId(expectedId);

        assertEquals(expectedId, playerEntity.getPlayerId());
    }

    @Test
    void testGetSetPlayerStyleId() {
        BotPlayStyle expectedStyle = BotPlayStyle.AGGRESSIVE;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerStyleId(expectedStyle);

        assertEquals(expectedStyle, playerEntity.getPlayerStyleId());
    }

    @Test
    void testGetSetPlayerTypeId() {
        int expectedType = 1;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerTypeId(expectedType);

        assertEquals(expectedType, playerEntity.getPlayerTypeId());
    }

    @Test
    void testGetSetPlayerName() {
        String expectedName = "Test Player";
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerName(expectedName);

        assertEquals(expectedName, playerEntity.getPlayerName());
    }

    @Test
    void testGetSetPlayerMoney() {
        int expectedMoney = 100000;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerMoney(expectedMoney);

        assertEquals(expectedMoney, playerEntity.getPlayerMoney());
    }

    @Test
    void testGetSetPlayerPosition() {
        int expectedPosition = 5;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerPosition(expectedPosition);

        assertEquals(expectedPosition, playerEntity.getPlayerPosition());
    }

    @Test
    void testGetSetIsPlayerInJail() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPlayerInJail(true);

        assertTrue(playerEntity.isPlayerInJail());

        playerEntity.setPlayerInJail(false);
        assertFalse(playerEntity.isPlayerInJail());
    }

    @Test
    void testGetSetAmountOfFreeJailCards() {
        int expectedCards = 2;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setAmountOfFreeJailCards(expectedCards);

        assertEquals(expectedCards, playerEntity.getAmountOfFreeJailCards());
    }

    @Test
    void testGetSetMatchId() {
        int expectedMatchId = 456;
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setMatchId(expectedMatchId);

        assertEquals(expectedMatchId, playerEntity.getMatchId());
    }

    @Test
    void testGetSetPurchasableSlotEntityList() {
        List<PurchasableSlotEntity> slotList = new ArrayList<>();
        slotList.add(new PurchasableSlotEntity());

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setPurchasableSlotEntityList(slotList);

        assertEquals(slotList, playerEntity.getPurchasableSlotEntityList());
    }

    @Test
    void testIsPlayerInJail() {
        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setPlayerInJail(true);
        assertTrue(playerEntity.isPlayerInJail());

        playerEntity.setPlayerInJail(false);
        assertFalse(playerEntity.isPlayerInJail());
    }

    @Test
    void testIsPlayerTurn() {
        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setPlayerTurn(true);
        assertTrue(playerEntity.isPlayerTurn());

        playerEntity.setPlayerTurn(false);
        assertFalse(playerEntity.isPlayerTurn());
    }

    @Test
    void testIsBankrupt() {
        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setBankrupt(true);
        assertTrue(playerEntity.isBankrupt());

        playerEntity.setBankrupt(false);
        assertFalse(playerEntity.isBankrupt());
    }

    @Test
    void testGetSetProvinceCount() {
        PlayerEntity playerEntity = new PlayerEntity();
        int expectedCount = 3;

        playerEntity.setProvinceCount(expectedCount);
        assertEquals(expectedCount, playerEntity.getProvinceCount());
    }

    @Test
    void testGetSetRailwayCount() {
        PlayerEntity playerEntity = new PlayerEntity();
        int expectedCount = 2;

        playerEntity.setRailwayCount(expectedCount);
        assertEquals(expectedCount, playerEntity.getRailwayCount());
    }

    @Test
    void testGetSetCompanyCount() {
        PlayerEntity playerEntity = new PlayerEntity();
        int expectedCount = 1;

        playerEntity.setCompanyCount(expectedCount);
        assertEquals(expectedCount, playerEntity.getCompanyCount());
    }

    @Test
    void testGetSetUpgradeCount() {
        PlayerEntity playerEntity = new PlayerEntity();
        int expectedCount = 0;

        playerEntity.setUpgradeCount(expectedCount);
        assertEquals(expectedCount, playerEntity.getUpgradeCount());
    }

    @Test
    void testGetSetRestCount() {
        PlayerEntity playerEntity = new PlayerEntity();
        int expectedCount = 1;

        playerEntity.setRestCount(expectedCount);
        assertEquals(expectedCount, playerEntity.getRestCount());
    }
}