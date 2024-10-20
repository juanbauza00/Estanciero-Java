package org.estanciero.model.entities.slot;

import lombok.*;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.money_action.CollectMoneyAction;
import org.estanciero.model.entities.player.Player;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class PrizeSlot extends Slot{

    private int amount;

    public PrizeSlot(int position, int amount) {
        super(position);
        this.amount = amount;

    }

    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
        ArrayList<PlayerActionBase> actions = new ArrayList<>();
        actions.add(new CollectMoneyAction(playerWhoCasted,amount));
        return actions;
    }
}
