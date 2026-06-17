package com.netcore.cleanwave.platform.iam.application.commandservices;

import com.netcore.cleanwave.platform.iam.domain.model.aggregates.User;
import com.netcore.cleanwave.platform.iam.domain.model.commands.SignInCommand;
import com.netcore.cleanwave.platform.iam.domain.model.commands.SignUpCommand;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;

public interface UserCommandService {
    Result<User, ApplicationError> handle(SignUpCommand command);
    Result<UserAndToken, ApplicationError> handle(SignInCommand command);

    record UserAndToken(User user, String token) {}
}
