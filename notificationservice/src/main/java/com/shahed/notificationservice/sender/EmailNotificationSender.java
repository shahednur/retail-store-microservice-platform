package com.shahed.notificationservice.sender;

import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailNotificationSender implements NotificationSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ahiyanohmyboy@gmail.com");
        message.setSubject(notification.getTitle());
        message.setText(notification.getMessage());
        mailSender.send(message);

        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
    }
}
