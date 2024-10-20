package org.estanciero.model.entities.slot;

import junit.framework.TestCase;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CompanySlotTest  {


    Player player;
    PurchasableSlot slot;
    int[] rentValue ;

    @BeforeEach
    void setUp()
    {
        player = new HumanPlayer("Pablo");
        player.setMoney(30000);
        rentValue = new int[]{100, 200, 300, 400, 500};
        slot = new CompanySlot(1,29000,rentValue);
    }
    @Test
    public void testValidateActionSlot() {
        PurchaseSlotAction purchaseSlotAction = new PurchaseSlotAction(slot,player);
        ArrayList<PlayerActionBase> actionsExpected = new ArrayList<>();
        actionsExpected.add(purchaseSlotAction);
        PlayerActionBase actionExpected = actionsExpected.get(0);
        ArrayList<PlayerActionBase> actionsActual = slot.validateActionSlot(player);
        PlayerActionBase actionActual = actionsActual.get(0);
        Assertions.assertInstanceOf(PurchaseSlotAction.class,actionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class,actionActual);
    }

    @Test
    public void testGetRentValue() {
        slot.setOwner(player);
        player.setCompanyCount(1);
        int rentValueExpected = 100;
        int rentValueActual = slot.getRentValue();
        Assertions.assertEquals(rentValueExpected,rentValueActual);
    }
}