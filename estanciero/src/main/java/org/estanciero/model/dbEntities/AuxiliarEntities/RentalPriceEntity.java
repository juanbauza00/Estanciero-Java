package org.estanciero.model.dbEntities.AuxiliarEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.dbEntities.MatchEntity;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTAL_PRICES")
public class RentalPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_price_id")
    private int rentalPriceId;

    @Column(name = "board_slot_id")
    private int boardSlotId;

    @Column(name = "rental_count")
    private int rentalCount;

    @Column(name = "price")
    private Integer prices;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumns({
//            @JoinColumn(name = "board_slot_id", referencedColumnName = "board_slot_id"),
//            @JoinColumn(name = "match_id", referencedColumnName = "match_id")
//    })
//    private PurchasableSlotEntity purchasableSlot;

}

