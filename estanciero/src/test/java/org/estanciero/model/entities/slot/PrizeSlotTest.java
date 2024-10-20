package org.estanciero.model.entities.slot;

import junit.framework.TestCase;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PrizeSlotTest {

    Player player;
    PrizeSlot prizeSlot;

    @BeforeEach
    void setUp()
    {
        player = new HumanPlayer("Peon");
        prizeSlot = new PrizeSlot(10,200);
        player.setMoney(1000);
    }

    @Test
    public void testValidateActionSlot() {
        int moneyExpected = 1200;
        ArrayList<PlayerActionBase> actionsActual = prizeSlot.validateActionSlot(player);
        PlayerActionBase actionActual = actionsActual.get(0);
        actionActual.executeAction();
        int moneyActual = player.getMoney();
        Assertions.assertEquals(moneyExpected,moneyActual);

    }

    @Test
    public void testGetAmount() {
        int amountExpected = 200;
        int amountActual = prizeSlot.getAmount();
        Assertions.assertEquals(amountExpected, amountActual);
    }

    @Test
    public void testSetAmount() {
        int amountExpected = 1000;
        prizeSlot.setAmount(1000);
        int amountActual = prizeSlot.getAmount();
        Assertions.assertEquals(amountExpected,amountActual);
    }
    @Test
    public void testPrizeSlotConstructor() {

        int position = 1;
        int amount = 1000;
        PrizeSlot prizeSlot = new PrizeSlot(position, amount);
        Assertions.assertEquals(position, prizeSlot.getPosition());
        Assertions.assertEquals(amount, prizeSlot.getAmount());
    }
}