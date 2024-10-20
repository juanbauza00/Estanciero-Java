package org.estanciero.model.entities.slot;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;


@NoArgsConstructor
public abstract class Slot implements ISlotValidator {
    private int position;

    public Slot(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public abstract ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted); //TODO VER
}
