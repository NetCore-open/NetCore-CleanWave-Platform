package com.netcore.cleanwave.platform.laundries.interfaces.rest.resources;

public record LaundryResource(
        Long id,
        String name,
        String address,
        double rating,
        String imageUrl,
        String status
) {
}
