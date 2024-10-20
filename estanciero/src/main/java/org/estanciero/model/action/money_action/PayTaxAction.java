package org.estanciero.model.action.money_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.game.Game;

import java.util.Optional;

public class PayTaxAction extends PlayerActionBase {
    private Player player;
    private int amount;
    private Optional<ProvinceSlot> provinceOptional;

    public PayTaxAction(Player player, int amount, Optional<ProvinceSlot> provinceOptional) {
        this.player = player;
        this.amount = amount;
        this.provinceOptional = provinceOptional;
        this.isAutomatic = true;
    }

    public void payTax(){
        // si obtenemos una provincia de parametro, significa que un jugador cayó en un slot que pertenece a otro jugador
        // si el jugador dueño de la provincia tiene todos los slots de esa misma provincia, el precio se duplica
        if (provinceOptional != null) {

            ProvinceSlot province = provinceOptional.get();
            if (province.getOwner() != null ) {
                Player provinceOwner = province.getOwner();

                if (Game.doesPlayerHaveFullProvince(provinceOwner,province.getProvince())) {
                    int doubleAmount = amount * 2;
                    player.setMoney(player.getMoney()-doubleAmount);

                }

            }
            else {
                //DEBUG
                System.out.println("PayTaxAction Error: provinceOptional was present but had no owner");
            }

        }
        else {
            player.setMoney(player.getMoney() - amount);
        }
    }



    @Override
    public void executeAction() {
        payTax();
    }
}
