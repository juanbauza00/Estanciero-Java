package org.estanciero.model.action.slot_action;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.CompanySlot;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.RailwaySlot;

@Getter
@Setter
public class UpgradeProvinceAction extends PlayerActionBase {

    ProvinceSlot slot;
    Player owner;
    private int ranchPrice;


    public UpgradeProvinceAction(ProvinceSlot slot) {
        this.slot = slot;
        this.owner = slot.getOwner();
        this.ranchPrice = slot.getRanchPrice();
        this.onExecuteMessage = "Property upgraded ";
        setDisplayMessage();


    }
    public void setDisplayMessage() {
        if (slot != null)
        {
            ProvinceSlot province = (ProvinceSlot) slot;
            this.displayMessage = "Upgrade the Province in position " + slot.getPosition() + "|  Province: " + province.getProvince() + " Zone: " + province.getZone() +" Price: "+province.getRanchPrice();
        }
    }
    @Override
    public void executeAction() {
        upgradeSlot();
    }

    public void upgradeSlot() {
        owner.setMoney(owner.getMoney()-ranchPrice);
        slot.setRanchCount(slot.getRanchCount()+1);
    }
}
