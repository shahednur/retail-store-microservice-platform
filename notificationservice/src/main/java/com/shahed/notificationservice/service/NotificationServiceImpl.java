package com.shahed.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shahed.notificationservice.dispatcher.NotificationDispatcher;
import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationChannel;
import com.shahed.notificationservice.entity.NotificationStatus;
import com.shahed.notificationservice.repository.NotificationRepository;
import com.shahed.notificationservice.sender.NotificationSender;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDispatcher dispatcher;
    private final Map<String, NotificationSender> senders;

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
    @Transactional
    public Notification sendNotification(Notification notification) {
        NotificationSender sender = senders.get(notification.getChannel().name());

        if (sender == null) {
            throw new IllegalArgumentException(
                    "Unsupported notification channel: " + notification.getChannel());
        }

        try {
            // Send via the chosen sender
            sender.send(notification);

            // Set success status only in service, not in sender
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

            System.out.printf(
                    "✅ Notification sent to user %s via %s: %s%n",
                    notification.getUserId(),
                    notification.getChannel(),
                    notification.getMessage());

            // Dispatch for async handling (websocket, push updates, etc.)
            dispatcher.dispatch(notification);

        } catch (Exception e) {
            // Handle failure gracefully
            notification.setStatus(NotificationStatus.FAILED);

            System.err.printf(
                    "❌ Failed to send notification to user %s via %s: %s%n",
                    notification.getUserId(),
                    notification.getChannel(),
                    e.getMessage());
        }

        // Save final notification state (SENT or FAILED)
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
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
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        return notificationRepository.save(notification);
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
