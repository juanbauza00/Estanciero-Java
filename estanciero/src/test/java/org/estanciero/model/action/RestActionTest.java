package org.estanciero.model.action;

import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.RestSlot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestActionTest  {

    private Player playerRest;
    private  RestSlot restSlot;

    @BeforeEach
    void setUp()
    {
        playerRest= new HumanPlayer("Zelarayan");
        restSlot= new RestSlot(15);
    }
    @Test
    void restAction()
    {
        int restCounterExpected= 1;
        RestAction restAction = new RestAction(restSlot,playerRest);
        restAction.executeAction();
        int restCounterActual=playerRest.getRestCount();
        Assertions.assertEquals(restCounterExpected,restCounterActual);
    }

}