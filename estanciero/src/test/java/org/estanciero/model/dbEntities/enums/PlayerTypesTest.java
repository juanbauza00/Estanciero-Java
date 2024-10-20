package org.estanciero.model.dbEntities.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTypesTest {
    @Test
    public void testPlayerTypes() {
        assertEquals(PlayerTypes.HUMAN, PlayerTypes.HUMAN);
        assertEquals(PlayerTypes.BOT, PlayerTypes.BOT);

        for (PlayerTypes type : PlayerTypes.values()) {
            assertNotNull(type);
        }
    }
}
