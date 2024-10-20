package org.estanciero.builder.mappers;

import org.estanciero.model.dbEntities.SpecialCardEntity;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCardMapperTest {

    @InjectMocks
    private SpecialCardMapper specialCardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapEntityToModel() {
        List<SpecialCardEntity> cardEntityList = new ArrayList<>();
        SpecialCardEntity cardEntity1 = new SpecialCardEntity();
        cardEntity1.setSpecialCardId(1);
        cardEntity1.setBoardSlotTypeId(7);
        cardEntity1.setAmount(null);
        cardEntity1.setCardMessage("Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line");
        cardEntity1.setActionTypeId(SpecialCardActionType.MOVE);
        cardEntity1.setToPosition(26);

        SpecialCardEntity cardEntity2 = new SpecialCardEntity();
        cardEntity2.setSpecialCardId(21);
        cardEntity2.setBoardSlotTypeId(8);
        cardEntity2.setAmount(1000);
        cardEntity2.setCardMessage("You spent $1,000 on your insurance policy");
        cardEntity2.setActionTypeId(SpecialCardActionType.PAY);
        cardEntity2.setToPosition(null);

        cardEntityList.add(cardEntity1);
        cardEntityList.add(cardEntity2);

        List<SpecialCard> specialCards = specialCardMapper.mapEntityToModel(cardEntityList);

        assertNotNull(specialCards);
        assertEquals(2, specialCards.size());

        SpecialCard card1 = specialCards.get(0);
        assertEquals(1, card1.getId());
        assertEquals(SpecialCardType.LUCK, card1.getCardType());
        assertNull(card1.getMoneyAmount());
        assertEquals("Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line", card1.getCardMessage());
        assertEquals(SpecialCardActionType.MOVE, card1.getActionType());
        assertEquals(26, card1.getToPosition());

        SpecialCard card2 = specialCards.get(1);
        assertEquals(21, card2.getId());
        assertEquals(SpecialCardType.DESTINY, card2.getCardType());
        assertEquals(1000, card2.getMoneyAmount());
        assertEquals("You spent $1,000 on your insurance policy", card2.getCardMessage());
        assertEquals(SpecialCardActionType.PAY, card2.getActionType());
        assertNull(card2.getToPosition());
    }

    @Test
    void testMapModelToEntity() {
        List<SpecialCard> specialCards = new ArrayList<>();
        SpecialCard card1 = new SpecialCard();
        card1.setId(1);
        card1.setCardType(SpecialCardType.LUCK);
        card1.setMoneyAmount(null);
        card1.setCardMessage("Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line");
        card1.setActionType(SpecialCardActionType.MOVE);
        card1.setToPosition(26);

        SpecialCard card2 = new SpecialCard();
        card2.setId(21);
        card2.setCardType(SpecialCardType.DESTINY);
        card2.setMoneyAmount(1000);
        card2.setCardMessage("You spent $1,000 on your insurance policy");
        card2.setActionType(SpecialCardActionType.PAY);
        card2.setToPosition(null);

        specialCards.add(card1);
        specialCards.add(card2);

        int matchId = 1;
        List<SpecialCardEntity> cardEntityList = specialCardMapper.mapModelToEntity(specialCards, matchId);

        assertNotNull(cardEntityList);
        assertEquals(2, cardEntityList.size());

        //El id 0 = 31 -> el id 1 = 32 ...
        SpecialCardEntity cardEntity1 = cardEntityList.get(0);
        assertEquals(32, cardEntity1.getSpecialCardId());
        assertNull(cardEntity1.getAmount());
        assertEquals("Advance to Santa Fe, North Side, collect $5,000 if you pass through the Start Line", cardEntity1.getCardMessage());
        assertEquals(SpecialCardActionType.MOVE, cardEntity1.getActionTypeId());
        assertEquals(26, cardEntity1.getToPosition());
        assertEquals(1, cardEntity1.getMatchId());

        //El id 0 = 31 -> el id 1 = 32 ... el id 21 tomaría la posicion 52 (30+ 22 (22 porque es id 0 + los otros 21 ids)
        SpecialCardEntity cardEntity2 = cardEntityList.get(1);
        assertEquals(52, cardEntity2.getSpecialCardId());
        assertEquals(1000, cardEntity2.getAmount());
        assertEquals("You spent $1,000 on your insurance policy", cardEntity2.getCardMessage());
        assertEquals(SpecialCardActionType.PAY, cardEntity2.getActionTypeId());
        assertNull(cardEntity2.getToPosition());
        assertEquals(1, cardEntity2.getMatchId());
    }

}