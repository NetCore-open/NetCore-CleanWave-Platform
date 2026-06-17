package com.netcore.cleanwave.platform.logistics.application.internal.commandservices;

import com.netcore.cleanwave.platform.logistics.application.commandservices.DeliveryCommandService;
import com.netcore.cleanwave.platform.logistics.domain.model.aggregates.Delivery;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.CreateDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.DeleteDeliveryCommand;
import com.netcore.cleanwave.platform.logistics.domain.model.commands.UpdateDeliveryStatusCommand;
import com.netcore.cleanwave.platform.logistics.domain.repositories.DeliveryRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles logistics write operations.
 *
 * <p>Implements {@link DeliveryCommandService} to orchestrate task registration,
 * status updates, and task deletion for {@link Delivery} aggregates.</p>
 */
@NullMarked
@Service
public class DeliveryCommandServiceImpl implements DeliveryCommandService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryCommandServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Result<Delivery, ApplicationError> handle(CreateDeliveryCommand command) {
        try {
            var delivery = new Delivery(command);
            var savedDelivery = deliveryRepository.save(delivery);
            return Result.success(savedDelivery);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Delivery creation", e.getMessage()));
        }
    }

    @Override
    public Result<Delivery, ApplicationError> handle(UpdateDeliveryStatusCommand command) {
        var deliveryOpt = deliveryRepository.findById(command.id());
        if (deliveryOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Delivery", "Delivery task not found with ID: " + command.id()));
        }

        try {
            var delivery = deliveryOpt.get();
            delivery.updateStatus(command.status(), command.driverName(), command.driverPhone());
            var savedDelivery = deliveryRepository.save(delivery);
            return Result.success(savedDelivery);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Delivery update", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteDeliveryCommand command) {
        var deliveryOpt = deliveryRepository.findById(command.id());
        if (deliveryOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Delivery", "Delivery task not found with ID: " + command.id()));
        }

        try {
            deliveryRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Delivery deletion", e.getMessage()));
        }
    }
}
