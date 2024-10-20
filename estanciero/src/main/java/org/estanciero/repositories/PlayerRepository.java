package org.estanciero.repositories;

import jakarta.transaction.Transactional;
import org.estanciero.model.dbEntities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository <PlayerEntity, Integer>{
    List<PlayerEntity> findPlayerEntitiesByMatchId (int matchId);
   // Void deleteAllByMatchId(int matchId);
    @Transactional
    void deleteByMatchId(int matchId);
}
