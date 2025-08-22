package com.shahed.notificationservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationStatus;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a user (latest first)
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Fetch unread notifications for a user
    List<Notification> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, NotificationStatus status);

    // Count unread notifications for badge count (like 🔔 icon)
    long countByUserIdAndStatus(Long userId, NotificationStatus status);

    List<Notification> findByUserId(Long userId);
}
