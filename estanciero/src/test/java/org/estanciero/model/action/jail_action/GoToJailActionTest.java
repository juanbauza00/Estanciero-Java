package org.estanciero.model.action.jail_action;

import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GoToJailActionTest {


    @Test
    public void testExecuteAction() {
        Player mockPlayer = Mockito.mock(Player.class);
        GoToJailAction action = new GoToJailAction(mockPlayer);

        action.executeAction();

        verify(mockPlayer, times(1)).setPosition(14);
    }
}