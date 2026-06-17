package com.netcore.cleanwave.platform.notifications.infrastructure.eventhandlers;

import com.netcore.cleanwave.platform.notifications.application.commandservices.NotificationCommandService;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.netcore.cleanwave.platform.profiles.interfaces.events.ProfileCreatedIntegrationEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that listens to {@link ProfileCreatedIntegrationEvent} to trigger
 * a welcome notification for new users.
 */
@NullMarked
@Service("notificationsProfileCreatedEventHandler")
public class ProfileCreatedEventHandler {

    private final NotificationCommandService notificationCommandService;

    public ProfileCreatedEventHandler(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }

    /**
     * Listens to the profile creation integration event and dispatches a
     * welcome notification.
     *
     * @param event the integration event
     */
    @EventListener
    public void on(ProfileCreatedIntegrationEvent event) {
        var command = new CreateNotificationCommand(
                event.profileId(),
                "¡Bienvenido a CleanWave!",
                "Hola " + event.firstName() + ", tu cuenta ha sido creada exitosamente. ¡Gracias por elegir CleanWave!",
                "SYSTEM",
                "LOW",
                null
        );
        notificationCommandService.handle(command);
    }
}
