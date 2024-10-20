package org.estanciero.model.action.card_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.Game;

import java.util.ArrayList;

public class UseGetOutOfJailCardAction extends PlayerActionBase {

    Player playerWhoCasted;

    public UseGetOutOfJailCardAction(Player playerWhoCasted) {
        this.playerWhoCasted = playerWhoCasted;
    }

    public void getOutJail() {
        SpecialCard card = playerWhoCasted.getOutOfJailCards().get(0);
        if(card.getCardType()== SpecialCardType.DESTINY)
        {
            ArrayList<SpecialCard> deckDestiny= Game.getDestinyCardDeck();
            deckDestiny.add(card);
        }
        if(card.getCardType()== SpecialCardType.LUCK)
        {
            ArrayList<SpecialCard> deckLuck= Game.getLuckCardDeck();
            deckLuck.add(card);
        }
        playerWhoCasted.setInJail(false);
        playerWhoCasted.getOutOfJailCards().remove(0);
    }

    @Override
    public void executeAction() {
        getOutJail();
    }
}
