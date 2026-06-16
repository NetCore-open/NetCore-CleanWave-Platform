package com.netcore.cleanwave.platform.notifications.interfaces.rest;

import com.netcore.cleanwave.platform.notifications.application.commandservices.NotificationCommandService;
import com.netcore.cleanwave.platform.notifications.application.queryservices.NotificationQueryService;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.DeleteNotificationCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.netcore.cleanwave.platform.notifications.domain.model.queries.GetNotificationsByUserQuery;
import com.netcore.cleanwave.platform.notifications.interfaces.rest.resources.NotificationResource;
import com.netcore.cleanwave.platform.notifications.interfaces.rest.resources.UpdateNotificationStatusResource;
import com.netcore.cleanwave.platform.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.netcore.cleanwave.platform.shared.application.result.ApplicationError;
import com.netcore.cleanwave.platform.shared.application.result.Result;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.netcore.cleanwave.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing user notification management endpoints.
 *
 * <p>Handles HTTP requests to retrieve, update read state, and delete notifications.</p>
 */
@NullMarked
@RestController
@RequestMapping(value = "/api/v1/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "User notification endpoints")
public class NotificationsController {

    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(NotificationCommandService notificationCommandService,
                                   NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId the user identifier query parameter
     * @return {@code 200 OK} with the list of notifications
     */
    @GetMapping
    public ResponseEntity<List<NotificationResource>> getNotificationsByUser(@RequestParam Long userId) {
        var query = new GetNotificationsByUserQuery(userId);
        var notifications = notificationQueryService.handle(query);
        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the unique identifier of the notification
     * @param resource       the body containing target status parameters
     * @return {@code 200 OK} with the updated notification, or an error response
     */
    @PatchMapping("/{notificationId}")
    public ResponseEntity<?> updateNotificationStatus(
            @PathVariable Long notificationId,
            @RequestBody UpdateNotificationStatusResource resource
    ) {
        if (resource.isRead()) {
            var command = new MarkNotificationAsReadCommand(notificationId);
            var result = notificationCommandService.handle(command);
            return ResponseEntityAssembler.toResponseEntityFromResult(
                    result,
                    NotificationResourceFromEntityAssembler::toResourceFromEntity,
                    HttpStatus.OK
            );
        }
        return ResponseEntity.badRequest().body("Invalid notification status transition. Only 'isRead=true' is supported.");
    }

    /**
     * Deletes a notification.
     *
     * @param notificationId the unique identifier of the notification to delete
     * @return {@code 204 No Content} on success, or an error response
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        var command = new DeleteNotificationCommand(notificationId);
        var result = notificationCommandService.handle(command);
        return switch (result) {
            case Result.Success<Void, ApplicationError> success -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
