package com.netcore.cleanwave.platform.laundries.application.queryservices;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetAllLaundriesQuery;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetLaundryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface LaundryQueryService {
    Optional<Laundry> handle(GetLaundryByIdQuery query);
    List<Laundry> handle(GetAllLaundriesQuery query);
}
