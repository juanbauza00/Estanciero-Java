package org.estanciero.model.entities.slot;


import lombok.NoArgsConstructor;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;

@NoArgsConstructor
public class CompanySlot extends PurchasableSlot  implements ISlotValidator {

    public CompanySlot(int position) {
        super(position);
    }

    public CompanySlot(int position, int price, int[] rentValue) {
        super(position,price,rentValue);
    }





    //obtiene el valor de rent value que puede ser 100 o 200 dependiendo de la companyCount del owner
    //el valor es luego multiplicado por el valor de los dados dentro de PayRentAction siempre y cuando la action
    //tenga de parametro un slot de tipo CompanySlot
    @Override
    public int getRentValue()
    {
        if (this.getOwner() != null) {
            if (this.getOwner().getCompanyCount() > 0) {
                return rentValue[0];
            } else if (this.getOwner().getCompanyCount() > 1) {
                return rentValue[1];
            }
        }
        return 0;
    }
    public int getSellValue() {
        return (getPurchasePrice() /2);
    }

    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {

         ArrayList<PlayerActionBase> actions = new ArrayList<>();

         // si slot no tiene dueño
        if (this.getOwner() == null) {
            //si el jugador puede comprar la propiedad, agregar la Action a la lista
            if (validatePlayerCanBuy(playerWhoCasted)) {
                actions.add(new PurchaseSlotAction(this, playerWhoCasted));

            }
        }
        //si slot tiene dueño
        else {
            // el jugador no es el dueño, pagar la renta
            if(this.getOwner() != playerWhoCasted ){
                actions.add(new PayRentAction(this, playerWhoCasted, getRentValue()));
            }
        }


        return  actions;
    }
}
