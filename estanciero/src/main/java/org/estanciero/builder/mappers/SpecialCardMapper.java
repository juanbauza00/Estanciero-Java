package org.estanciero.builder.mappers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.dbEntities.SpecialCardEntity;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SpecialCardMapper {
    public List<SpecialCard> mapEntityToModel(List<SpecialCardEntity> cardEntityList){
        List<SpecialCard> specialCards = new ArrayList<>();
        for(SpecialCardEntity cardEntity : cardEntityList){
            SpecialCard card = new SpecialCard();
            card.setId(cardEntity.getSpecialCardId());
            if(cardEntity.getBoardSlotTypeId() == 7) card.setCardType(SpecialCardType.LUCK);
            if(cardEntity.getBoardSlotTypeId() == 8) card.setCardType(SpecialCardType.DESTINY);
            card.setMoneyAmount(cardEntity.getAmount());
            card.setCardMessage(cardEntity.getCardMessage());
            card.setActionType(cardEntity.getActionTypeId());
            card.setToPosition(cardEntity.getToPosition());

            specialCards.add(card);
        }
        return specialCards;
    }

    public List<SpecialCardEntity> mapModelToEntity(List<SpecialCard> specialCards, int id){
        List<SpecialCardEntity> specialCardEntities = new ArrayList<>();
        //Del 0 al 30 son las cartas default
        int cardIdbase = 31;
        for(SpecialCard cardModel : specialCards){
            SpecialCardEntity cardEntity = new SpecialCardEntity();
            cardEntity.setSpecialCardId(cardModel.getId() + cardIdbase);
            if(cardModel.getCardType() == SpecialCardType.LUCK) cardEntity.setBoardSlotTypeId(7);
            if(cardModel.getCardType() == SpecialCardType.DESTINY) cardEntity.setBoardSlotTypeId(8);
            cardEntity.setAmount(cardModel.getMoneyAmount());
            cardEntity.setCardMessage(cardModel.getCardMessage());
            cardEntity.setActionTypeId(cardModel.getActionType());
            cardEntity.setToPosition(cardModel.getToPosition());
            cardEntity.setMatchId(id);

            specialCardEntities.add(cardEntity);
        }
        return specialCardEntities;
    }
}
