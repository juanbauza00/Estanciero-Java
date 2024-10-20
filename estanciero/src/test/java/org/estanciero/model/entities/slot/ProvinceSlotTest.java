package org.estanciero.model.entities.slot;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
import java.util.Optional;

public class ProvinceSlotTest  {



    Player player;
    ProvinceSlot slot;
    int[] rentValue ;

    @BeforeEach
    void setUp()
    {
        player = new HumanPlayer("Peon");
        player.setMoney(30000);
        rentValue = new int[]{100, 200, 300, 400, 500};
        slot = new ProvinceSlot(10,2000,ProvinceName.FORMOSA,ProvinceZone.CENTRO,1000,rentValue);

    }
    @Test
    void testValidateActionSlot()
    {
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
    public void testGetAndSetRanchCount() {
        int ranchCountExpected = 5;
        slot.setRanchCount(ranchCountExpected);
        int ranchCountActual = slot.getRanchCount();
        Assertions.assertEquals(ranchCountExpected, ranchCountActual);
    }

    @Test
    public void testGetAndSetProvince() {
        ProvinceName provinceExpected = ProvinceName.SALTA;
        slot.setProvince(provinceExpected);
        ProvinceName provinceActual = slot.getProvince();
        Assertions.assertEquals(provinceExpected, provinceActual);
    }

    @Test
    public void testGetAndSetRanchPrice() {
        int ranchPriceExpected = 1000;
        slot.setRanchPrice(ranchPriceExpected);
        int ranchPriceActual = slot.getRanchPrice();
        Assertions.assertEquals(ranchPriceExpected, ranchPriceActual);
    }
    @Test
    public void testGetRentValue()
    {
        slot.setRanchCount(2);
        int expectedRent = 300;
        int actualRent = slot.getRentValue();
        Assertions.assertEquals(expectedRent, actualRent);

    }
}
