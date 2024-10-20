package org.estanciero.model.entities.slot;

import junit.framework.TestCase;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.jail_action.GoToJailAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JailSlotTest  {


    Player player;
    JailSlot jailSlot;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer("Peon");
        jailSlot = new JailSlot(35);
    }
    @Test
    public void testValidateActionSlot() {
        GoToJailAction goToJailAction = new GoToJailAction(player);
        ArrayList<PlayerActionBase> actionsExpected = new ArrayList<>();
        actionsExpected.add(goToJailAction);
        PlayerActionBase actionExpected = actionsExpected.get(0);
        player.setPosition(35);
        ArrayList<PlayerActionBase> actionsActual = jailSlot.validateActionSlot(player);
        PlayerActionBase actionActual = actionsActual.get(0);
        Assertions.assertInstanceOf(GoToJailAction.class,actionExpected);
        Assertions.assertInstanceOf(GoToJailAction.class,actionActual);
    }
}