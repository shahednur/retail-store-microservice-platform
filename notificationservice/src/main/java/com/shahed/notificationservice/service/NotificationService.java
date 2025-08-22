package com.shahed.notificationservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationStatus;

public interface NotificationService {

    Notification createNotification(Long userId, String title, String message, String channel);

    void sendNotification(Notification notification);

    List<Notification> getAllNotifications(Long userId);

    List<Notification> getUnreadNotifications(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    long getUnreadCound(Long userId);

    void undateStatus(Long notificationId, NotificationStatus status);

    void deleteNotification(Long notificationId);
}
