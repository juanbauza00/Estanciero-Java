package org.estanciero.model.entities.slot;


import lombok.NoArgsConstructor;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.game.Game;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public abstract class PurchasableSlot extends Slot {
    private int purchasePrice;
    private Player owner;
    private int sellValue;
    /*
    rentValue en ProvinceSlot: cada valor corresponde al rentValue del slot según su ranchAmount.

    rentValue en Railway Slot: cada valor corresponde al rentValue del slot según la cantidad de railways comprados.

    rentValue en Company: cada valor corresponde a un MULTIPLICADOR, según la cantidad de companys obtenidas.
    TODO HACER EJEMPLO JUAN
    */
    public int[] rentValue;

    public PurchasableSlot(int position) {
        super(position);
        this.purchasePrice = 0;
        this.owner = null;
        this.rentValue = null;
    }


    public PurchasableSlot(int position, int purchasePrice, int[] rentValue) {
        super(position);
        this.purchasePrice = purchasePrice;
        this.owner = null;
        this.rentValue = rentValue;
    }

    public Player getOwner() {
        if (owner == null) {
            return null;
        }
        else return owner;
    }

    public void setOwner(Player owner) { this.owner = owner;}

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }



    public abstract int getSellValue();

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public abstract int getRentValue();

    public void setRentValue(int[] rentValue) {
        this.rentValue = rentValue;
    }

    public boolean validatePlayerCanBuy(Player playerWhoCasted) {
        if (this.getOwner() == null) {
            return (playerWhoCasted.getMoney() >= this.getPurchasePrice());
        }
        else {
            return false;
        }

    }

    public static ArrayList<PurchasableSlot> getPurchasablesSlotWithOwner()
    {
        List<Slot> slots = Game.getSlots();
        ArrayList<PurchasableSlot> purchasableSlotsWithOwner = new ArrayList<>();
        for (Slot slot : slots )
        {
            if( slot instanceof  PurchasableSlot purchasableSlot)
            {
                if(purchasableSlot.getOwner()!=null)
                {
                    purchasableSlotsWithOwner.add(purchasableSlot);
                }

            }
        }
        return purchasableSlotsWithOwner;
    }

}
