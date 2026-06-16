package com.netcore.cleanwave.platform.laundries.application.internal.queryservices;

import com.netcore.cleanwave.platform.laundries.application.queryservices.LaundryQueryService;
import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetAllLaundriesQuery;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetLaundryByIdQuery;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaundryQueryServiceImpl implements LaundryQueryService {
    private final LaundryRepository laundryRepository;

    public LaundryQueryServiceImpl(LaundryRepository laundryRepository) {
        this.laundryRepository = laundryRepository;
    }

    @Override
    public Optional<Laundry> handle(GetLaundryByIdQuery query) {
        return laundryRepository.findById(query.laundryId());
    }

    @Override
    public List<Laundry> handle(GetAllLaundriesQuery query) {
        return laundryRepository.findAll();
    }
}
