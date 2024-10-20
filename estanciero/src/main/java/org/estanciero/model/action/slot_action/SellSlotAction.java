package org.estanciero.model.action.slot_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.CompanySlot;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.RailwaySlot;

public class SellSlotAction extends PlayerActionBase {

    private Player playerWhoCasted;
    private PurchasableSlot slot;
    public SellSlotAction( PurchasableSlot slot, Player playerWhoCasted) {
        this.playerWhoCasted = playerWhoCasted;
        this.slot = slot;
        setDisplayMessage();
        this.onExecuteMessage = "Slot sold.";

    }

    public void executeAction() {
        sellSlot();
    }

    public void sellSlot() {
        int sellValue = slot.getSellValue();
        slot.setOwner(null);
        playerWhoCasted.setMoney(playerWhoCasted.getMoney() + sellValue);

        if (slot instanceof ProvinceSlot) {
            playerWhoCasted.setProvinceCount(playerWhoCasted.getProvinceCount() - 1);
        }
        else if (slot instanceof CompanySlot) {
            playerWhoCasted.setCompanyCount(playerWhoCasted.getCompanyCount() - 1);
        }
        else if (slot instanceof RailwaySlot) {
            playerWhoCasted.setRailwayCount(playerWhoCasted.getRailwayCount() - 1);
        }
    }

    public void setDisplayMessage() {
        if (slot instanceof ProvinceSlot provinceSlot) {
            this.displayMessage ="Sell the province " + provinceSlot.getProvince() + " "+ provinceSlot.getZone();
        }
        if (slot instanceof CompanySlot companySlot) {
            this.displayMessage = "Sell the Company in slot " + companySlot.getPosition();
        }
        if (slot instanceof RailwaySlot railwaySlot) {
            this.displayMessage = "Sell the Railway in slot " + railwaySlot.getPosition();
        }
    }
}
