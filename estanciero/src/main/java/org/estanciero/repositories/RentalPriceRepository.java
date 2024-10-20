package org.estanciero.repositories;

import org.estanciero.model.dbEntities.AuxiliarEntities.RentalPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalPriceRepository extends JpaRepository<RentalPriceEntity, Integer> {
    List<RentalPriceEntity> findByBoardSlotId(int boardSlotId);
}
