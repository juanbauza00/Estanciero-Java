package org.estanciero.repositories;

import org.estanciero.model.dbEntities.AuxiliarEntities.ProvinceZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceZoneRepository  extends JpaRepository<ProvinceZoneEntity, Integer> {

}
