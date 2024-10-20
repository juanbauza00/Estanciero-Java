package org.estanciero.model.action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkipTurnActionTest {

    @Test
    void executeAction() {
        SkipTurnAction action = new SkipTurnAction();

        action.executeAction();
    }
}