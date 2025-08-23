package com.shahed.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPlacedEvent extends BaseEvent {
    private String orderId;
    private Double totalAmount;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String orderStatus;
}
