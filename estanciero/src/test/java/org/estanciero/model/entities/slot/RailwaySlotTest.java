package org.estanciero.model.entities.slot;

import junit.framework.TestCase;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;

import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class RailwaySlotTest  {


    @BeforeEach
    void setUp()
    {
        int[] rentValue = new int[]{100, 200, 300, 400, 500};
    }
    @Test
    public void testValidateActionSlot() {
        BotPlayer mockedBotPlayer = new BotPlayer("BotPlayer", BotPlayStyle.AGGRESSIVE);
        HumanPlayer mockedHumanPlayer = new HumanPlayer("HumanPlayer");

        RailwaySlot mockedSlot = new RailwaySlot(0,100, new int[1]);
        mockedSlot.setOwner(mockedBotPlayer);

        ArrayList<PlayerActionBase> actions;
        actions = mockedSlot.validateActionSlot(mockedHumanPlayer);

        Assertions.assertEquals(1, actions.size());
        Assertions.assertEquals(actions.get(0).getClass(), PayRentAction.class);
    }
@Test
    public void testGetRentValue() {
        BotPlayer mockedBotPlayer = new BotPlayer("BotPlayer", BotPlayStyle.AGGRESSIVE);
        mockedBotPlayer.setRailwayCount(3);
        int[] rentValue = new int[]{100, 200, 300, 400, 500};
        RailwaySlot mockedSlot = new RailwaySlot(0,100, rentValue);
        mockedSlot.setOwner(mockedBotPlayer);
        int rentValueExpected = 300;
        int rentValueActual = mockedSlot.getRentValue();
        Assertions.assertEquals(rentValueExpected,rentValueActual);

    }
}