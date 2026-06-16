package com.netcore.cleanwave.platform.laundries.application.internal.commandservices;

import com.netcore.cleanwave.platform.laundries.application.commandservices.LaundryCommandService;
import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.UpdateLaundryStatusCommand;
import com.netcore.cleanwave.platform.laundries.domain.repositories.LaundryRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class LaundryCommandServiceImpl implements LaundryCommandService {
    private final LaundryRepository laundryRepository;

    public LaundryCommandServiceImpl(LaundryRepository laundryRepository) {
        this.laundryRepository = laundryRepository;
    }

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
