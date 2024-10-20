package org.estanciero.model.action.money_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;

public class CollectMoneyAction extends PlayerActionBase {
    private Player player;
    private int amount;

    public CollectMoneyAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
        this.onExecuteMessage=player.getPlayerNameWithColor() + " collect this money: "+amount;
        this.isAutomatic = true;
        this.onExecuteMessage = player.getPlayerNameWithColor() + " falls in a Prize slot! They collect " + amount;
    }

    public void collectMoney(){
        player.setMoney(player.getMoney() + amount);
    }

    @Override
    public void executeAction() {
        collectMoney();
    }


}
