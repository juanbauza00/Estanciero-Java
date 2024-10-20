package org.estanciero.model.action.card_action;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.SpecialCardSlot;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Getter
@Setter
public class DrawSpecialCardAction extends PlayerActionBase {

    private Player playerTakeCard;
    private  SpecialCard specialCard;
    private ArrayList<SpecialCard> deck;
    Random random = new Random() ;

    public DrawSpecialCardAction(Player playerTakeCard, ArrayList<SpecialCard> deck) {
        this.playerTakeCard = playerTakeCard;
        this.deck = deck;
        this.isAutomatic = false;
        this.onExecuteMessage = playerTakeCard.getPlayerNameWithColor() + " takes a card from the deck";
        this.displayMessage = "Take a special card from the deck";
    }


    @Override
    public void executeAction() {
        drawSpecialCard();

    }

    public SpecialCard drawSpecialCard() {

        int index = random.nextInt(deck.size());
        specialCard= deck.get(index);
        deck.removeIf(sp -> sp == specialCard);
        if(specialCard.getActionType()== SpecialCardActionType.OUTJAIL)
        {
            playerTakeCard.getOutOfJailCards().add(specialCard);
        }
        deck.add(specialCard);
        return specialCard;
    }
}
