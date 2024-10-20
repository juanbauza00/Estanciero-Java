package org.estanciero.model.dbEntities.AuxiliarEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROVINCE_ZONES")
public class ProvinceZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_zone_id")
    private int provinceZoneId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    @Column(name = "province")
    private String province;

    @Column(name = "ranch_price")
    private int ranchPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_slot_id")
    private Set<PurchasableSlotEntity> purchasableSlotEntities = new HashSet<>();

//    @OneToMany(mappedBy = "provinceZone", cascade = CascadeType.ALL)
//    private Set<PurchasableSlotEntity> purchasableSlotEntities = new HashSet<>();
}
