package com.shahed.notificationservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.notificationservice.entity.Notification;

public interface NotificationService {

    Notification sendNotification(Notification notification);

    List<Notification> getAllNotifications();

    Optional<Notification> getNotificationById(Long id);

    List<Notification> getNotificationsByToUser(String toUser);
}
