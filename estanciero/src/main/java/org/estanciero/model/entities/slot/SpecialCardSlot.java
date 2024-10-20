package org.estanciero.model.entities.slot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.Game;
import org.estanciero.services.interfaces.ISlotValidator;

import java.util.ArrayList;

@Getter
@Setter
public class SpecialCardSlot extends Slot implements ISlotValidator {

    private SpecialCardType slotType;
    private ArrayList<SpecialCard> deck;

    public SpecialCardSlot(int position) {
        super(position);
    }

    public SpecialCardSlot(int position, SpecialCardType type) {
        super(position);
        this.slotType = type;
    }

    public SpecialCardSlot(int position, ArrayList<SpecialCard> deck) {
        super(position);

    }

    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) { //verificar que el mazo no vacio
        if (slotType.equals(SpecialCardType.LUCK) ) {
            this.deck = Game.getLuckCardDeck();
        }
        else {
            this.deck = Game.getDestinyCardDeck();
        }


        ArrayList<PlayerActionBase> playerActionBases = new ArrayList<>();

        if(!deck.isEmpty())
        {
            DrawSpecialCardAction drawSpecialCardAction = new DrawSpecialCardAction(playerWhoCasted,deck);
            playerActionBases.add(drawSpecialCardAction);
        }

        return playerActionBases;
    }
}
