package org.estanciero.model.entities.special_cards;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class SpecialCard {

    private int id;
    private SpecialCardType cardType;
    private Integer moneyAmount;
    private String cardMessage;
    private SpecialCardActionType actionType;
    private Integer toPosition;

    public SpecialCard() {

        cardMessage = "";
        cardType = null;
        moneyAmount = 0;
        actionType = null;
        toPosition = -1;
    }
    public SpecialCard(SpecialCardType cardType, int moneyAmount, SpecialCardActionType actionType, int toPosition, String cardMessage) {
        this.cardType = cardType;
        this.moneyAmount = moneyAmount;
        this.actionType = actionType;
        this.toPosition = toPosition;
        this.cardMessage = cardMessage;
    }

    @Override
    public String toString() {
        if(toPosition == -1 && moneyAmount == 0){
            return "TYPE=" + cardType +
                    ", MESSAGE='" + cardMessage + '\'' +
                    ", ACTION=" + actionType;
        } else if(toPosition == -1){
            return "TYPE=" + cardType +
                    ", AMOUNT=" + moneyAmount +
                    ", MESSAGE='" + cardMessage + '\'' +
                    ", ACTION=" + actionType;
        } else {
            return "TYPE=" + cardType +
                    ", AMOUNT=" + moneyAmount +
                    ", MESSAGE='" + cardMessage + '\'' +
                    ", ACTION=" + actionType +
                    ", MOVE TO =" + toPosition;
        }
    }
}