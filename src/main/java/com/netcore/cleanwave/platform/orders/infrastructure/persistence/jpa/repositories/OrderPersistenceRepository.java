package com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.repositories;

import com.netcore.cleanwave.platform.orders.infrastructure.persistence.jpa.entities.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderPersistenceRepository extends JpaRepository<OrderPersistenceEntity, Long> {
    List<OrderPersistenceEntity> findByUserId(Long userId);
}
