package org.estanciero.model.entities.player.bot_profiles;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.RailwaySlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ConservativeBotPlayerTest {

    ConservativeBotPlayer conservativeBotPlayer;
    ArrayList<PlayerActionBase> playerActions;
    RailwaySlot railwaySlot;
    ProvinceSlot provinceSlot;
    int[] rentValue = {100,200,300,400,500};

    @BeforeEach
    void setUp() {
        conservativeBotPlayer = new ConservativeBotPlayer("Pepito", BotPlayStyle.CONSERVATIVE);
        playerActions = new ArrayList<>();
        railwaySlot = new RailwaySlot(15,2300,rentValue);
        playerActions.add(new PurchaseSlotAction(railwaySlot,conservativeBotPlayer));
        provinceSlot= new ProvinceSlot(23, 3000, ProvinceName.FORMOSA, null, 0, rentValue);
    }

    @Test
    void selectAction() {
        PlayerActionBase playerActionExpected = new PurchaseSlotAction(railwaySlot, conservativeBotPlayer);
        PlayerActionBase playerActionActual = conservativeBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionActual);
    }

    @Test
    void selectActionWithPrioritizedProvince() {
        ProvinceSlot prioritizedProvinceSlot = new ProvinceSlot(24,  2500, ProvinceName.FORMOSA, null, 0, rentValue);
        playerActions.add(new PurchaseSlotAction(prioritizedProvinceSlot, conservativeBotPlayer));
        PlayerActionBase playerActionExpected = new PurchaseSlotAction(prioritizedProvinceSlot, conservativeBotPlayer);
        PlayerActionBase playerActionActual = conservativeBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionActual);
    }

    @Test
    void selectActionWithNonPrioritizedProvince() {
        conservativeBotPlayer.setProvinceCount(5); //seteo el contador de provincias en 5
        ProvinceSlot nonPrioritizedProvinceSlot = new ProvinceSlot(25,  2500, ProvinceName.BSAS, null, 0, rentValue);
        playerActions.add(new PurchaseSlotAction(nonPrioritizedProvinceSlot, conservativeBotPlayer));
        PlayerActionBase playerActionExpected = new PurchaseSlotAction(nonPrioritizedProvinceSlot, conservativeBotPlayer);
        PlayerActionBase playerActionActual = conservativeBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionActual);
    }
    @Test
    void selectActionWithUpgrade() {
        conservativeBotPlayer.setMoney(10000);
        provinceSlot.setRanchPrice(2000);
        UpgradeProvinceAction upgradeAction = new UpgradeProvinceAction(provinceSlot); //creo una accion de upgrade
        playerActions.add(0, upgradeAction);
        PlayerActionBase playerActionExpected = upgradeAction;
        PlayerActionBase playerActionActual = conservativeBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(UpgradeProvinceAction.class, playerActionExpected);
        Assertions.assertInstanceOf(UpgradeProvinceAction.class, playerActionActual);
    }

}