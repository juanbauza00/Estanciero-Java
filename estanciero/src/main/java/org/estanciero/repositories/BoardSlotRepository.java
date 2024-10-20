package org.estanciero.repositories;

import jakarta.transaction.Transactional;
import org.estanciero.model.dbEntities.BoardSlotEntity;
import org.estanciero.model.dbEntities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface BoardSlotRepository extends JpaRepository<BoardSlotEntity, Integer> {
    @Query("SELECT bs FROM BoardSlotEntity bs WHERE bs.boardSlotTypeId NOT IN (0, 1, 2)")
    List<BoardSlotEntity>getNotPurchasableSlots();

}
