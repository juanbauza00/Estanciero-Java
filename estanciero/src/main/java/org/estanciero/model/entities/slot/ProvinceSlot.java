package org.estanciero.model.entities.slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class ProvinceSlot extends PurchasableSlot implements ISlotValidator {
    private int provinceZoneId;
    @Getter
    @Setter
    private int ranchCount;
    @Setter
    @Getter
    private ProvinceName province;
    @Setter
    @Getter
    private ProvinceZone zone;
    @Setter
    @Getter
    private int ranchPrice;

    public ProvinceSlot(int position) {
        super(position);
        this.province = null;
        this.zone = null;

    }

    public ProvinceSlot(int position, int price, ProvinceName province,
                        ProvinceZone zone, int ranchPrice, int[] rentValue) {
        super(position,price,rentValue);
        this.province = province;
        this.zone = zone;

    }


    @Override
    public int getRentValue() {
        return rentValue[ranchCount];
    }

    public int getSellValue() {
        //(purchasePrice  +  (ranchCount * ranchPrice) / 2)
        return ((getPurchasePrice() + getRanchCount() * ranchPrice)/2);
    }

    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {

        ArrayList<PlayerActionBase> actions = new ArrayList<>();

        //si no tiene dueño
        if(this.getOwner() == null){
            //si el jugador puede comprar la propiedad, agregar la Action a la lista
            if (validatePlayerCanBuy(playerWhoCasted)) {
                actions.add(new PurchaseSlotAction(this, playerWhoCasted));
            }
        }
        //sí tiene dueño
        else {
            // si el jugador no es el dueño, pagar la renta
            if (this.getOwner() != playerWhoCasted) {
                actions.add(new PayRentAction(this, playerWhoCasted, getRentValue()));
            }

        }

        return actions;
    }
}
