package org.estanciero.model.dbEntities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchEntityTest {

    @Test
    void testGetterSetMatchId() {
        int expectedId = 123;
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setMatchId(expectedId);

        assertEquals(expectedId, matchEntity.getMatchId());
    }

    @Test
    void testGetterSetMatchDate() {
        LocalDateTime expectedDate = LocalDateTime.now();
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setMatchDate(expectedDate);

        assertEquals(expectedDate, matchEntity.getMatchDate());
    }

    @Test
    void testGetterSetMoneyGoal() {
        Integer expectedGoal = 10000;
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setMoneyGoal(expectedGoal);

        assertEquals(expectedGoal, matchEntity.getMoneyGoal());
    }

    @Test
    void testGetterSetGameDifficulty() {
        Integer expectedDifficulty = 2;
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setGameDifficulty(expectedDifficulty);

        assertEquals(expectedDifficulty, matchEntity.getGameDifficulty());
    }

    @Test
    void testGetterSetGameMode() {
        Integer expectedMode = 1;
        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setGameMode(expectedMode);

        assertEquals(expectedMode, matchEntity.getGameMode());
    }
}