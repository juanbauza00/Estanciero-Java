package org.estanciero.model.dbEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.entities.special_cards.SpecialCardActionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SPECIAL_CARDS")
public class SpecialCardEntity {
    @Id
    @Column(name = "special_card_id")
    private int specialCardId;

    @Column(name = "board_slot_type_id")
    private int boardSlotTypeId;

    @Column
    private String cardMessage;

    @Column
    private Integer amount;

    @Column
    private Integer toPosition;

    @Column(name = "action_type_id")
    @Enumerated(EnumType.ORDINAL)
    private SpecialCardActionType actionTypeId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "match_id")
//    private MatchEntity match;
    @Column(name = "match_id")
    private int matchId;

}
