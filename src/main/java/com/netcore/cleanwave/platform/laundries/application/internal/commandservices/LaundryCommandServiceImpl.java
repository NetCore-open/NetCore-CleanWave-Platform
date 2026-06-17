package com.netcore.cleanwave.platform.laundries.application.internal.commandservices;

import com.netcore.cleanwave.platform.laundries.application.commandservices.LaundryCommandService;
import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.UpdateLaundryStatusCommand;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles laundry write operations.
 *
 * <p>Implements {@link LaundryCommandService} and orchestrates creation and
 * status-update operations on {@link Laundry} aggregates. Enforces the
 * uniqueness invariant on laundry name before delegating persistence to the
 * {@link LaundryRepository}.</p>
 */
@NullMarked
@Service
public class LaundryCommandServiceImpl implements LaundryCommandService {
    private final LaundryRepository laundryRepository;

    public LaundryCommandServiceImpl(LaundryRepository laundryRepository) {
        this.laundryRepository = laundryRepository;
    }

    /**
     * Handles the {@link CreateLaundryCommand} to create and persist a new laundry.
     *
     * <p>Rejects the command with a conflict error if a laundry with the same name
     * already exists. Unexpected exceptions are caught and surfaced as
     * {@code UNEXPECTED_ERROR} results.</p>
     *
     * @param command the create-laundry command
     * @return {@code Result.success} with the persisted laundry,
     *         or {@code Result.failure} with the relevant {@link ApplicationError}
     */
    @Override
    public Result<Laundry, ApplicationError> handle(CreateLaundryCommand command) {
        if (laundryRepository.existsByName(command.name())) {
            return Result.failure(ApplicationError.conflict("Laundry", "A laundry with name '" + command.name() + "' already exists"));
        }
        try {
            var laundry = new Laundry(command);
            var savedLaundry = laundryRepository.save(laundry);
            return Result.success(savedLaundry);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Laundry", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Laundry registration", e.getMessage()));
        }
    }

    /**
     * Handles the {@link UpdateLaundryStatusCommand} to change a laundry's operational status.
     *
     * @param command the update-laundry-status command carrying the laundry id and new status
     * @return {@code Result.success} with the updated laundry,
     *         or {@code Result.failure} with a {@code NOT_FOUND} error if the laundry does not exist
     */
    @Override
    public Result<Laundry, ApplicationError> handle(UpdateLaundryStatusCommand command) {
        var laundryOpt = laundryRepository.findById(command.laundryId());
        if (laundryOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Laundry", "Laundry not found"));
        }
        try {
            var laundry = laundryOpt.get();
            laundry.updateStatus(command.status());
            var savedLaundry = laundryRepository.save(laundry);
            return Result.success(savedLaundry);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Laundry status update", e.getMessage()));
        }
    }
}
