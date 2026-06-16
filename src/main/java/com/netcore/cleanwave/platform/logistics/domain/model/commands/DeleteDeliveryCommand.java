package com.netcore.cleanwave.platform.logistics.domain.model.commands;

/**
 * Command record to request the deletion of a logistics task.
 *
 * @param id the unique identifier of the delivery to delete
 */
public record DeleteDeliveryCommand(
        Long id
) {
}
