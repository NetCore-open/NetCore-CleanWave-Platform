package com.netcore.cleanwave.platform.notifications.application.internal.commandservices;

import com.netcore.cleanwave.platform.notifications.application.commandservices.NotificationCommandService;
import com.netcore.cleanwave.platform.notifications.domain.model.aggregates.Notification;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.CreateNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.netcore.cleanwave.platform.notifications.domain.repositories.NotificationRepository;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

/**
 * Application service implementation that handles notification write operations.
 *
 * <p>Implements {@link NotificationCommandService} to orchestrate creation,
 * marking as read, and deletion of {@link Notification} aggregates.</p>
 */
@NullMarked
@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Result<Notification, ApplicationError> handle(CreateNotificationCommand command) {
        try {
            var notification = new Notification(command);
            var savedNotification = notificationRepository.save(notification);
            return Result.success(savedNotification);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Notification creation", e.getMessage()));
        }
    }

    @Override
    public Result<Notification, ApplicationError> handle(MarkNotificationAsReadCommand command) {
        var notificationOpt = notificationRepository.findById(command.id());
        if (notificationOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Notification", "Notification not found with ID: " + command.id()));
        }

        try {
            var notification = notificationOpt.get();
            notification.markAsRead();
            var savedNotification = notificationRepository.save(notification);
            return Result.success(savedNotification);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Notification read mark", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteNotificationCommand command) {
        var notificationOpt = notificationRepository.findById(command.id());
        if (notificationOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Notification", "Notification not found with ID: " + command.id()));
        }

        try {
            notificationRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Notification deletion", e.getMessage()));
        }
    }
}
