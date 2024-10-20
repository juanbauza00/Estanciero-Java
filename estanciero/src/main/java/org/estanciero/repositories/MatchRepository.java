package org.estanciero.repositories;

import jakarta.transaction.Transactional;
import org.estanciero.model.dbEntities.MatchEntity;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
    @Override
    void deleteById(Integer integer);
}
