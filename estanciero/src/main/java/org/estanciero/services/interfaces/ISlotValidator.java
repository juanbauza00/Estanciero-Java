package org.estanciero.services.interfaces;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;

import java.util.ArrayList;

public interface ISlotValidator {
    ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted);

}
