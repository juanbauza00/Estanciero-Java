package org.estanciero.model.entities.slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.action.money_action.PayTaxAction;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;

@Getter
@Setter
public class TaxSlot extends Slot implements ISlotValidator {

    private int tax;

    public TaxSlot(int position, int tax) {
        super(position);
        this.tax = tax;
    }

    public TaxSlot(int position) {
        super(position);
    }

    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
        ArrayList<PlayerActionBase> actions = new ArrayList<>();
        actions.add(new PayTaxAction(playerWhoCasted,tax, null));

        return actions;
    }
}
