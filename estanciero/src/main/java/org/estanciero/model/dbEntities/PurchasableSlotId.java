package org.estanciero.model.dbEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class PurchasableSlotId implements Serializable {
    @Column(name = "board_slot_id")
    private int boardSlotId;

    @Column(name = "match_id")
    private int matchId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasableSlotId that = (PurchasableSlotId) o;
        return boardSlotId == that.boardSlotId &&
                matchId == that.matchId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardSlotId, matchId);
    }
}
