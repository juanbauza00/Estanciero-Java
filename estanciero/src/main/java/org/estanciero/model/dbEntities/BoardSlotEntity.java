package org.estanciero.model.dbEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BOARD_SLOTS")
public class BoardSlotEntity {
    @Id
    @Column(name = "board_slot_id")
    private int boardSlotId;

    @Column(name = "board_slot_type_id")
    private int boardSlotTypeId;

    @Column
    private Integer amountPrice;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "board_slot_id", referencedColumnName = "board_slot_id")
    private Set<PurchasableSlotEntity> purchasableSlotEntities = new HashSet<>();
}
