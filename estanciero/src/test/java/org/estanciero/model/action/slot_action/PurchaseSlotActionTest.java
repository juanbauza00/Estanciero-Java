package org.estanciero.model.action.slot_action;


import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseSlotActionTest  {

    Player player;
    PurchasableSlot slot;
    int[] rentValue ;

    @BeforeEach
    void setUp()
    {
        player = new HumanPlayer("Peon");
        player.setMoney(30000);
        rentValue = new int[]{100, 200, 300, 400, 500};
        slot = new ProvinceSlot(10,2000, ProvinceName.FORMOSA, ProvinceZone.CENTRO,1000,rentValue);

    }
    @Test
    void testPurchaseSlot()
    {
        Player playerExpected = player;
        int moneyExpected = 28000;
        int countOfProvincesExpected = 1;
        PurchaseSlotAction purchaseSlotAction = new PurchaseSlotAction(slot,player);
        purchaseSlotAction.executeAction();
        Assertions.assertEquals(moneyExpected,player.getMoney());
        Assertions.assertEquals(countOfProvincesExpected,player.getProvinceCount());
        Assertions.assertEquals(playerExpected,slot.getOwner());

    }

}