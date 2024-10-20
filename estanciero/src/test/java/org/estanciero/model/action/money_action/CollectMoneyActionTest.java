package org.estanciero.model.action.money_action;

import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CollectMoneyActionTest {
    private Player player;
    private int amount;

    @BeforeEach
    void setUp() {
        player = new HumanPlayer("homero");
        player.setMoney(1000);
        amount = 500;
    }

    @Test
    void collectMoney() {
        int amountExpected= 1500;
        CollectMoneyAction collectMoneyAction = new CollectMoneyAction(player,amount);
        collectMoneyAction.executeAction();
        int playerMoney = player.getMoney();
        Assertions.assertEquals(amountExpected,playerMoney);
    }


}