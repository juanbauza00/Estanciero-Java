package org.estanciero.model.entities.player.bot_profiles;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.bot_profiles.AgressiveBotPlayer;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.RailwaySlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class AgressiveBotPlayerTest {

    AgressiveBotPlayer agressiveBotPlayer;
    ArrayList<PlayerActionBase> playerActions;
    RailwaySlot railwaySlot;
    ProvinceSlot provinceSlot;
    int[] rentValue = {100,200,300,400,500};

    @BeforeEach
    void setUp() {
        agressiveBotPlayer = new AgressiveBotPlayer("Malandrito", BotPlayStyle.AGGRESSIVE);
        playerActions = new ArrayList<>();
        railwaySlot = new RailwaySlot(15,2300,rentValue);
        playerActions.add(new PurchaseSlotAction(railwaySlot,agressiveBotPlayer));
        provinceSlot= new ProvinceSlot(23,  3000, ProvinceName.TUCUMAN, null, 0, rentValue);
    }

    @Test
    void selectAction() {
        PlayerActionBase playerActionExpected = new PurchaseSlotAction(railwaySlot, agressiveBotPlayer);
        PlayerActionBase playerActionActual = agressiveBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionActual);
    }


}