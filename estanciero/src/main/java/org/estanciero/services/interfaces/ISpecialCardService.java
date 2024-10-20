package org.estanciero.services.interfaces;

import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.special_cards.SpecialCard;

public interface ISpecialCardService {
    public void executeCard(SpecialCard specialCard, Player player);
}
