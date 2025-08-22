package com.shahed.notificationservice.sender;

import com.shahed.notificationservice.entity.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
