package com.netcore.cleanwave.platform.laundries.application.commandservices;

import com.netcore.cleanwave.platform.laundries.domain.model.aggregates.Laundry;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.CreateLaundryCommand;
import com.netcore.cleanwave.platform.laundries.domain.model.commands.UpdateLaundryStatusCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;

public interface LaundryCommandService {
    Result<Laundry, ApplicationError> handle(CreateLaundryCommand command);
    Result<Laundry, ApplicationError> handle(UpdateLaundryStatusCommand command);
}
