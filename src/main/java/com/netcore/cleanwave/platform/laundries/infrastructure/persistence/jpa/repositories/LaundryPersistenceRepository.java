package com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.laundries.infrastructure.persistence.jpa.entities.LaundryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaundryPersistenceRepository extends JpaRepository<LaundryPersistenceEntity, Long> {
    Optional<LaundryPersistenceEntity> findByName(String name);

    boolean existsByName(String name);
}
