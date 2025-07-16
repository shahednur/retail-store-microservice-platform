package com.shahed.notificationservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shahed.notificationservice.dto.ApiResponse;
import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    // send a new notification
    @PostMapping
    public ResponseEntity<ApiResponse<Notification>> sendNotification(@RequestBody Notification notification) {
        try {
            Notification saved = notificationService.sendNotification(notification);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Notification>builder()
                            .success(true)
                            .message("Notification sent")
                            .data(saved)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Notification>builder()
                            .success(false)
                            .message("Notification not sent")
                            .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notification>>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(ApiResponse.<List<Notification>>builder()
                .success(true)
                .message("Fetched all notifications")
                .data(notifications)
                .build());
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<ApiResponse<Notification>> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        if (notification.isPresent()) {
            return ResponseEntity.ok(ApiResponse.<Notification>builder()
                    .success(true)
                    .message("fetched notification by id")
                    .data(notification.get())
                    .build());
        } else {
            return ResponseEntity.ok(ApiResponse.<Notification>builder()
                    .success(false)
                    .message("Notification not found")
                    .build());
        }
    }

    @GetMapping("/user/{toUser}")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotificationsByToUser(@PathVariable String toUser) {
        List<Notification> users = notificationService.getNotificationsByToUser(toUser);
        return ResponseEntity.ok(ApiResponse.<List<Notification>>builder()
                .success(true)
                .message("Notification fetched by user")
                .data(users)
                .build());
    }
}
