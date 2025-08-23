package com.shahed.notificationservice.dispatcher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.sender.EmailNotificationSender;
import com.shahed.notificationservice.sender.InAppNotificationSender;
import com.shahed.notificationservice.sender.SmsNotificationSender;

@Service
public class NotificationDispatcher {

    private final EmailNotificationSender emailSender;
    private final InAppNotificationSender inAppSender;
    private final SmsNotificationSender smsSender;

    public NotificationDispatcher(
            EmailNotificationSender emailSender,
            InAppNotificationSender inAppSender,
            @Qualifier("SMS") SmsNotificationSender smsSender) { // Add @Qualifier
        this.emailSender = emailSender;
        this.inAppSender = inAppSender;
        this.smsSender = smsSender;
    }

    public void dispatch(Notification notification) {
        try {
            switch (notification.getChannel()) {
                case EMAIL -> emailSender.send(notification);
                case IN_APP -> inAppSender.send(notification);
                case SMS -> smsSender.send(notification);
                default -> throw new IllegalArgumentException(
                        "Unsupported notification channel: " + notification.getChannel());
            }
        } catch (Exception e) {
            System.err.println("❌ Dispatch failed for channel " +
                    notification.getChannel() + ": " + e.getMessage());
            throw e;
        }
    }
}
