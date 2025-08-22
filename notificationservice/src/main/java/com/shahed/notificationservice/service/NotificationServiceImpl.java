package com.shahed.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationChannel;
import com.shahed.notificationservice.entity.NotificationStatus;
import com.shahed.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(Long userId, String title, String message, NotificationChannel channel) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setChannel(channel);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    @Override
    public void sendNotification(Notification notification) {
        // Here we'll integrate Email/SMS/Push provider later

        System.out.println("Sending notifition to user: " + notification.getUserId() + " via "
                + notification.getChannel() + " : " + notification.getMessage());
        notification.setStatus(NotificationStatus.SENT);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, NotificationStatus.PENDING);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setStatus(NotificationStatus.READ);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void markAllAsRead(Long userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId,
                NotificationStatus.PENDING);
        for (Notification n : unread) {
            n.setStatus(NotificationStatus.READ);
        }

        notificationRepository.saveAll(unread);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.PENDING);
    }

    @Override
    public void undateStatus(Long notificationId, NotificationStatus status) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setStatus(status);
            notificationRepository.save(notification);
        });
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
