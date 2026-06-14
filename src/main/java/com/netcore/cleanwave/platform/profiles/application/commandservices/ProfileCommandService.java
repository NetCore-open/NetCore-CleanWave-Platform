package com.netcore.cleanwave.platform.profiles.application.commandservices;

import com.netcore.cleanwave.platform.profiles.domain.model.aggregates.Profile;
import com.netcore.cleanwave.platform.profiles.domain.model.commands.CreateProfileCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;

public interface ProfileCommandService {

    Result<Profile, ApplicationError> handle(CreateProfileCommand command);
}

