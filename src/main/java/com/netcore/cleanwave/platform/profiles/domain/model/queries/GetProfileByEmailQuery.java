package com.netcore.cleanwave.platform.profiles.domain.model.queries;

import com.netcore.cleanwave.platform.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}

