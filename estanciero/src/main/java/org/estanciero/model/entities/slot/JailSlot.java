package org.estanciero.model.entities.slot;

import lombok.NoArgsConstructor;
import org.estanciero.model.action.jail_action.GoToJailAction;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;

@NoArgsConstructor
public class JailSlot extends Slot implements ISlotValidator {

    public JailSlot(int position) {
        super(position);
    }

    @Override
    //si jugador cae en la casilla de jail, no ocurre nada (figura asi en el enunciado)
    //solamente va a prision si cae en el slot 35 (slot de marche preso)

    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
        ArrayList<PlayerActionBase> actions = new ArrayList<>();

        if (playerWhoCasted.getPosition() == 35) {
            actions.add(new GoToJailAction(playerWhoCasted));
        }

        return actions;
    }
}
