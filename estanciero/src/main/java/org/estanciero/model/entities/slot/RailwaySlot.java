package org.estanciero.model.entities.slot;
import lombok.NoArgsConstructor;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.entities.player.Player;

import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;


@NoArgsConstructor
public class RailwaySlot extends PurchasableSlot implements ISlotValidator {

    public RailwaySlot(int position) {
        super(position);
    }

    public RailwaySlot(int position, int price, int[] rentValue) {
        super(position,price, rentValue);
    }

    //como el valor de renta proviene de la cantidad de railwaySlots del dueño, el valor que necesitamos
    //se encuentra en la posicion (getRailWayCount - 1) de rentValue[]
    public int getRentValue()
    {
        if(this.getOwner().getRailwayCount() > 0){
            int railWayCount = (this.getOwner().getRailwayCount())-1;
            return (rentValue[railWayCount]);
        }
        else return 0;
    }

    public int getSellValue() {
        return (getPurchasePrice() /2);
    }
    // Validaciones
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
            //si el dueño no es el jugador que cayó en el Slot, debe pagar renta, agregar Action a la lista
            if (this.getOwner() != playerWhoCasted) {
                actions.add(new PayRentAction(this, playerWhoCasted, getRentValue()));
            }
        }
        return actions;
    }

}
