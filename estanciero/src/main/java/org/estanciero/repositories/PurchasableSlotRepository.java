package org.estanciero.repositories;

import jakarta.transaction.Transactional;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasableSlotRepository extends JpaRepository<PurchasableSlotEntity, Integer> {
    List<PurchasableSlotEntity> findAllById_MatchId(int matchId);
    @Transactional
    void deleteByIdMatchId(int id);
}
