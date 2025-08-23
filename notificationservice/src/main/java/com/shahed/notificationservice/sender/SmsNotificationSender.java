package com.shahed.notificationservice.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shahed.notificationservice.entity.Notification;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service("SMS")
public class SmsNotificationSender implements NotificationSender {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    @PostConstruct
    private void init() {
        try {
            // Add validation for required properties
            if (accountSid == null || accountSid.isEmpty()) {
                throw new IllegalStateException("SMS account-sid is not configured");
            }
            if (authToken == null || authToken.isEmpty()) {
                throw new IllegalStateException("SMS auth-token is not configured");
            }
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                throw new IllegalStateException("SMS from-number is not configured");
            }

            Twilio.init(accountSid, authToken);
            System.out.println("✅ Twilio SMS service initialized successfully");
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Twilio SMS service: " + e.getMessage());
            throw e; // Re-throw to prevent bean creation
        }
    }

    @Override
    public void send(Notification notification) {
        try {
            String smsContent = notification.getTitle() + " : " + notification.getMessage();

            Message message = Message.creator(
                    new PhoneNumber(notification.getToUserPhone()),
                    new PhoneNumber(phoneNumber),
                    smsContent).create();

            // Don't set status here - let the service handle it
            System.out.println("📱 SMS sent with SID: " + message.getSid());

        } catch (Exception e) {
            System.err.println("❌ SMS sending failed: " + e.getMessage());
            throw new RuntimeException("Failed to send SMS: " + e.getMessage(), e);
        }
    }
}
