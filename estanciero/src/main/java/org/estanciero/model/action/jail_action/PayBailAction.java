package org.estanciero.model.action.jail_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;

public class PayBailAction extends PlayerActionBase {
    /*

    */
    private Player playerInJail;

    public PayBailAction(Player playerInJail) {
        this.playerInJail = playerInJail;
        this.isAutomatic = false;
        this.onExecuteMessage = playerInJail.getPlayerNameWithColor() + "pays bail, they'll throw the dice on their next turn";
        this.displayMessage = "Pay bail to exit jail";
    }

    public void executeAction() {
        payBail();
    }

    public void payBail() {
        playerInJail.setMoney(playerInJail.getMoney() - 1000);
        playerInJail.setInJail(false);
    }
}
