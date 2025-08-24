package com.shahed.notificationservice.listener;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahed.notificationservice.dto.OrderPlacedEvent;
import com.shahed.notificationservice.entity.Notification;
import com.shahed.notificationservice.entity.NotificationChannel;
import com.shahed.notificationservice.entity.NotificationStatus;
import com.shahed.notificationservice.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-placed", groupId = "notification-service")
    public void handleOrderPlacedEvent(String eventData) {
        try {
            log.info("📦 Received ORDER_PLACED event: {}", eventData);
            OrderPlacedEvent event = objectMapper.readValue(eventData, OrderPlacedEvent.class);

            // Create notification for order confirmation
            Notification notification = createOrderPlacedNotification(event);
            notificationService.sendNotification(notification);

            log.info("✅ Order placed notification sent for order: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("❌ Error processing ORDER_PLACED event: {}", e.getMessage(), e);
        }
    }

    private Notification createOrderPlacedNotification(OrderPlacedEvent event) {
        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setTitle("Order Confirmation");
        notification.setMessage(String.format(
                "Dear %s, your order #%s has been placed successfully~ Total amount: $%.2f. We'll notify you once it's shipped.",
                event.getCustomerName(),
                event.getOrderId(),
                event.getTotalAmount()));
        notification.setToUserPhone(event.getCustomerPhone());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());
        return notification;
    }
}
