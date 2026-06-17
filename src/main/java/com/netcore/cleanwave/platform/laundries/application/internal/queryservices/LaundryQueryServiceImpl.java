package com.netcore.cleanwave.platform.laundries.application.internal.queryservices;

import com.netcore.cleanwave.platform.laundries.application.queryservices.LaundryQueryService;
import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetAllLaundriesQuery;
import com.netcore.cleanwave.platform.laundries.domain.model.queries.GetLaundryByIdQuery;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that handles laundry read operations.
 *
 * <p>Implements {@link LaundryQueryService} and delegates all queries to the
 * {@link LaundryRepository}, keeping query orchestration logic separate from
 * command (write) operations.</p>
 */
@NullMarked
@Service
public class LaundryQueryServiceImpl implements LaundryQueryService {
    private final LaundryRepository laundryRepository;

    public LaundryQueryServiceImpl(LaundryRepository laundryRepository) {
        this.laundryRepository = laundryRepository;
    }

    /**
     * Retrieves a laundry by its persistence identity.
     *
     * @param query the query carrying the laundry id
     * @return an {@link Optional} containing the laundry if found, empty otherwise
     */
    @Override
    public Optional<Laundry> handle(GetLaundryByIdQuery query) {
        return laundryRepository.findById(query.laundryId());
    }

    /**
     * Retrieves all registered laundries.
     *
     * @param query the query (no parameters)
     * @return the list of all laundry aggregates
     */
    @Override
    public List<Laundry> handle(GetAllLaundriesQuery query) {
        return laundryRepository.findAll();
    }
}
