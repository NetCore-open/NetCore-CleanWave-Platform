package com.netcore.cleanwave.platform.laundries.domain.repositories;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import java.util.List;
import java.util.Optional;

public interface LaundryRepository {
    Laundry save(Laundry laundry);
    Optional<Laundry> findById(Long id);
    List<Laundry> findAll();
    boolean existsByName(String name);
}
