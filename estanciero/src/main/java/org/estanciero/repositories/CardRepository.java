package org.estanciero.repositories;

import jakarta.transaction.Transactional;
import org.estanciero.model.dbEntities.SpecialCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<SpecialCardEntity, Integer> {
    List<SpecialCardEntity> findByMatchId(int id);

    @Transactional
    void deleteByMatchId(int matchId);
}
