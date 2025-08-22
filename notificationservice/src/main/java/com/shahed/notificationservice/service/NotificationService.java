package com.shahed.notificationservice.service;

import java.util.List;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationChannel;
import com.shahed.notificationservice.entity.NotificationStatus;

public interface NotificationService {

    Notification createNotification(Long userId, String title, String message, NotificationChannel channel);

    Notification sendNotification(Notification notification);

    List<Notification> getAllNotifications(Long userId);

    List<Notification> getUnreadNotifications(Long userId);

    List<Notification> getUserNotifications(Long userId);

    Notification markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    long getUnreadCount(Long userId);

    void undateStatus(Long notificationId, NotificationStatus status);

    void deleteNotification(Long notificationId);
}
