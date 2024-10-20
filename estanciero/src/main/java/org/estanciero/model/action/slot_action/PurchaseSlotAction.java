package org.estanciero.model.action.slot_action;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.CompanySlot;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.RailwaySlot;

@Getter
@Setter
public class PurchaseSlotAction extends PlayerActionBase {


    private final PurchasableSlot slot;
    private final Player playerWhoBuys;


    public PurchaseSlotAction(PurchasableSlot slot, Player playerWhoBuys){
        this.slot = slot;
        this.playerWhoBuys = playerWhoBuys;
        setDisplayMessage();
        this.onExecuteMessage = "Property purchased. ";
        this.isAutomatic = false;
    }

    private void purchaseSlot() {
        playerWhoBuys.setMoney(playerWhoBuys.getMoney() - slot.getPurchasePrice());
        slot.setOwner(playerWhoBuys);

        if (slot instanceof CompanySlot){
            int playerCompanyCount = playerWhoBuys.getCompanyCount();
            playerWhoBuys.setCompanyCount(playerCompanyCount + 1);
        } else if (slot instanceof RailwaySlot) {
            int playerRailwayCount = playerWhoBuys.getRailwayCount();
            playerWhoBuys.setRailwayCount(playerRailwayCount + 1);
        } else if (slot instanceof ProvinceSlot) {
            int playerProvinceCount = playerWhoBuys.getProvinceCount();
            playerWhoBuys.setProvinceCount(playerProvinceCount + 1);
        }

    }

    private void setDisplayMessage() {
        if (slot instanceof CompanySlot){
            this.displayMessage = "Purchase the Company in position " + slot.getPosition()+ " Price: "+ slot.getPurchasePrice();
        }
        else if (slot instanceof RailwaySlot){
            this.displayMessage = "Purchase the Railway in position " + slot.getPosition()+" Price: "+ slot.getPurchasePrice();
        }
        else if (slot instanceof ProvinceSlot) {
            ProvinceSlot province = (ProvinceSlot) slot;
            this.displayMessage = "Purchase the Province in position " + slot.getPosition() + "|  Province: " + province.getProvince() + " Zone: " + province.getZone()+" Price: "+province.getPurchasePrice();
        }
    }
    @Override
    public void executeAction() {
        purchaseSlot();
    }
}
