package org.estanciero.model.entities.slot;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.money_action.PayTaxAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaxSlotTest {
    private Player player;
    private int tax;
    private TaxSlot taxSlot;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer("Homero");
        player.setMoney(500);
        tax = 1000;
        taxSlot = new TaxSlot(0);
        taxSlot.setTax(1000);
    }

    @Test
    void testValidateActionSlot() {
        ArrayList<PlayerActionBase> actions = new ArrayList<>();
        PayTaxAction paytaxaction = new PayTaxAction(player,tax, null);
        actions.add(paytaxaction);

        ArrayList<PlayerActionBase> actions2;
        actions2 = taxSlot.validateActionSlot(player);

        assertEquals(actions.size(), actions2.size());

        assertEquals(actions.size(), 1);
        assertEquals(actions.get(0).getClass(), PayTaxAction.class);
        assertNotNull(actions);

    }

    @Test
    void testGetTax() {
        int actual = taxSlot.getTax();
        assertEquals(1000,actual);
    }

    @Test
    void testSetTax() {
        taxSlot.setTax(500);
        assertEquals(500,taxSlot.getTax());
    }

    @Test
    void testTaxSlot(){
        taxSlot = new TaxSlot(0,50);
        assertEquals(0,taxSlot.getPosition());
        assertEquals(50,taxSlot.getTax());
    }


}