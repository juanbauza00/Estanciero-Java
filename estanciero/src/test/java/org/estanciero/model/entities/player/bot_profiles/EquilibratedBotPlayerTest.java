package org.estanciero.model.entities.player.bot_profiles;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.RailwaySlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class EquilibratedBotPlayerTest {

    EquilibratedBotPlayer equilibratedBotPlayer;
    ArrayList<PlayerActionBase> playerActions;
    RailwaySlot railwaySlot;
    ProvinceSlot provinceSlot;
    int[] rentValue = {100,200,300,400,500};
    @BeforeEach
    void setUp() {
        equilibratedBotPlayer = new EquilibratedBotPlayer("Ricardito",BotPlayStyle.EQUILIBRATED);
        playerActions = new ArrayList<>();
        railwaySlot = new RailwaySlot(15,2300,rentValue);
        playerActions.add(new PurchaseSlotAction(railwaySlot,equilibratedBotPlayer));
        provinceSlot= new ProvinceSlot(23);
    }
    @Test
    void selectAction() {
        PlayerActionBase playerActionExpected = new PurchaseSlotAction(railwaySlot, equilibratedBotPlayer);
        PlayerActionBase playerActionActual = equilibratedBotPlayer.selectAction(playerActions);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionExpected);
        Assertions.assertInstanceOf(PurchaseSlotAction.class, playerActionActual);
    }


    
}