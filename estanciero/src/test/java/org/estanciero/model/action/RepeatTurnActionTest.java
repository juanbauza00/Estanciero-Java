package org.estanciero.model.action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepeatTurnActionTest {

    @Test
    void executeAction() {
        RepeatTurnAction action = new RepeatTurnAction();
        action.executeAction();

        assertTrue(true);
    }
}