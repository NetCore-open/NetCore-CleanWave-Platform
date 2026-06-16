package com.netcore.cleanwave.platform.laundries.domain.model.commands;

public record CreateLaundryCommand(String name, String address, double rating, String imageUrl) {
}
