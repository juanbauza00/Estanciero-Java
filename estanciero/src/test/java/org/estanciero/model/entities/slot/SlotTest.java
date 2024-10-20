package org.estanciero.model.entities.slot;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class SlotTest {

    @Mock
    Slot slot;

    @Test
    public void testNoArgsConstructor() {
        Slot slot = new Slot() {
            @Override
            public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
                return new ArrayList<>();
            }
        };
        assertNotNull(slot);
    }

    @Test
    void getPosition() {
        int expectedPosition = 5;
        Slot slot = new Slot(expectedPosition) {
            @Override
            public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
                return new ArrayList<>();
            }
        };
        assertEquals(expectedPosition, slot.getPosition());
    }


    @Test
    void setPosition() {
        int expectedPosition = 5;
        Slot slot = new Slot(0) {
            @Override
            public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
                return new ArrayList<>();
            }
        };
        slot.setPosition(expectedPosition);
        assertEquals(expectedPosition, slot.getPosition());
    }
}