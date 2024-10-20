package org.estanciero.model.action.money_action;

import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.game.Game;

import java.util.Optional;


public class PayRentAction extends PlayerActionBase {

    private Player playerWhoPays;
    private int amount;
    private PurchasableSlot slot;
    @Setter
    private Optional<Integer> diceValueOptional;


    public PayRentAction(PurchasableSlot slot, Player playerWhoPays, int amount) {
        this.slot = slot;
        this.playerWhoPays = playerWhoPays;
        this.amount = amount;
        this.isAutomatic = true;
        this.diceValueOptional = Optional.empty();
        this.onExecuteMessage = playerWhoPays.getPlayerNameWithColor() + " Pays a $"+amount+" rent";

    }

    public void payRent() {
        Player owner = slot.getOwner();

        // si la provincia es CompanySlot, getRentValue requiere el valor de los dados tirados en ese turno para su calculo
        int diceValue = 1;
        if (diceValueOptional.isPresent()) {
            diceValue = diceValueOptional.get();
        }

        if (slot instanceof ProvinceSlot provinceSlot) {
            if (Game.doesPlayerHaveFullProvince(owner, provinceSlot.getProvince())) {
                playerWhoPays.setMoney(playerWhoPays.getMoney() - ((amount * diceValue)*2));
            }
            else {
                playerWhoPays.setMoney(playerWhoPays.getMoney() - amount);
            }
        }
        else {
            playerWhoPays.setMoney(playerWhoPays.getMoney() - amount);
        }




    }

    @Override
    public void executeAction() {
        payRent();
    }

}
