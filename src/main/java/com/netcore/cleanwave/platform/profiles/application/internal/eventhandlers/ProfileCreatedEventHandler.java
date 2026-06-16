package com.netcore.cleanwave.platform.profiles.application.internal.eventhandlers;

import com.netcore.cleanwave.platform.profiles.domain.model.events.ProfileCreatedEvent;
import com.netcore.cleanwave.platform.profiles.interfaces.events.ProfileCreatedIntegrationEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application-layer event handler for the {@link ProfileCreatedEvent} domain event.
 *
 * <p>Listens for {@link ProfileCreatedEvent} instances published after a profile
 * is persisted and re-publishes them as {@link ProfileCreatedIntegrationEvent}
 * so that other bounded contexts can subscribe to profile creation without
 * coupling to the Profiles domain model.</p>
 */
@NullMarked
@Service("profilesProfileCreatedEventHandler")
public class ProfileCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public ProfileCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Handles a {@link ProfileCreatedEvent} by translating and re-publishing it
     * as a {@link ProfileCreatedIntegrationEvent}.
     *
     * @param event the domain event carrying the newly created profile's data
     */
    @EventListener
    public void on(ProfileCreatedEvent event) {
        eventPublisher.publishEvent(new ProfileCreatedIntegrationEvent(
                event.profileId(),
                event.firstName(),
                event.lastName(),
                event.email(),
                event.street(),
                event.number(),
                event.city(),
                event.postalCode(),
                event.country()));
    }
}
