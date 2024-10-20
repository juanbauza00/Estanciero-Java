package org.estanciero.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private boolean isNewGame;
    private List<Slot> slotList;
    private List<SpecialCard> luckCards;
    private List<SpecialCard> destinyCards;
    private List<Player> playerList;
    private Integer moneyGoal;
    private GameDifficulty gameDifficulty;
    private GameMode gameMode;

    public List<PurchasableSlot> getPurchSlots(){
        List<PurchasableSlot> purchasableSlots = new ArrayList<>();
        for(Slot slot : this.slotList){
            if(slot instanceof PurchasableSlot) purchasableSlots.add((PurchasableSlot) slot);
        }
        return purchasableSlots;
    }
}
