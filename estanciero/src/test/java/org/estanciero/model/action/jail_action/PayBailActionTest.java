package org.estanciero.model.action.jail_action;

import org.estanciero.model.action.jail_action.PayBailAction;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class PayBailActionTest {

    @Test
    public void testExecuteAction() {

        Player mockPlayer = Mockito.mock(Player.class);

        when(mockPlayer.getMoney()).thenReturn(5000);

        PayBailAction action = new PayBailAction(mockPlayer);


        action.executeAction();


        verify(mockPlayer, times(1)).setMoney(4000);
        verify(mockPlayer, times(1)).setInJail(false);
    }
}