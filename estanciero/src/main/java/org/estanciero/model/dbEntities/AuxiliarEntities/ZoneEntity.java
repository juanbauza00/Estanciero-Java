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
@Table(name = "ZONES")
public class ZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zone_id")
    private int zoneId;

    @Column(name = "zone_name")
    private String zoneName;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private Set<ProvinceZoneEntity> provinceZoneEntities = new HashSet<>();

}
