package org.estanciero.model.dbEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.dbEntities.AuxiliarEntities.ProvinceZoneEntity;
import org.estanciero.model.dbEntities.AuxiliarEntities.RentalPriceEntity;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="PURCHASABLE_SLOTS")
public class PurchasableSlotEntity{

    @EmbeddedId
    private PurchasableSlotId id;

    @Column(name = "board_slot_type_id")
    private int boardSlotTypeId;

    @Column(name = "province_zone_id")
    private Integer provinceZoneId;

    @Column(name = "owner_id")
    private Integer ownerId; //MAS TARDE

    @Column
    private int ranchCount;

    @Column(name = "purchase_price")
    private int purchasePrice;

}
