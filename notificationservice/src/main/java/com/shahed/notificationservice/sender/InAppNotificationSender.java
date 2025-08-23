package com.shahed.notificationservice.sender;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationStatus;
import com.shahed.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component("IN_APP")
@Service
@RequiredArgsConstructor
public class InAppNotificationSender implements NotificationSender {

    private final NotificationRepository repository;

    @Override
    public void send(Notification notification) {
        repository.save(notification);
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
    }
}
