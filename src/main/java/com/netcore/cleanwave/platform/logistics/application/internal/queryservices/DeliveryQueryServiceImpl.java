package com.netcore.cleanwave.platform.logistics.application.internal.queryservices;

import com.netcore.cleanwave.platform.logistics.application.queryservices.DeliveryQueryService;
import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetAllDeliveriesQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveriesByUserQuery;
import com.netcore.cleanwave.platform.logistics.domain.model.queries.GetDeliveryByIdQuery;
import com.netcore.cleanwave.platform.logistics.domain.repositories.DeliveryRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service implementation that handles logistics read operations.
 *
 * <p>Implements {@link DeliveryQueryService} and delegates all query lookups to the {@link DeliveryRepository}.</p>
 */
@NullMarked
@Service
public class DeliveryQueryServiceImpl implements DeliveryQueryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryQueryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<Delivery> handle(GetAllDeliveriesQuery query) {
        return deliveryRepository.findAll();
    }

    @Override
    public List<Delivery> handle(GetDeliveriesByUserQuery query) {
        return deliveryRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<Delivery> handle(GetDeliveryByIdQuery query) {
        return deliveryRepository.findById(query.id());
    }
}
