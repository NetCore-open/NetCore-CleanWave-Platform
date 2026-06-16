package com.netcore.cleanwave.platform.laundries.domain.model.commands;

import com.netcore.cleanwave.platform.laundries.domain.model.valueobjects.LaundryStatus;

public record UpdateLaundryStatusCommand(Long laundryId, LaundryStatus status) {
}
