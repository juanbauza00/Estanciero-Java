package org.estanciero.model.action.money_action;

import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class PayTaxActionTest {
    private Player player;
    private int amount;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer("homero");
        player.setMoney(2000);
        amount = 1500;
    }

    @Test
    void payTax() {
        PayTaxAction payTaxAction = new PayTaxAction(player,amount,null);
        payTaxAction.executeAction();

        Assertions.assertEquals(500,player.getMoney());
    }


}