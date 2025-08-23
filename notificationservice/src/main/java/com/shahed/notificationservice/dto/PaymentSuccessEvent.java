package com.shahed.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentSuccessEvent extends BaseEvent {
    private String paymentId;
    private String orderId;
    private Double amount;
    private String paymentMethod;
    private String customerEmail;
    private String customerPhone;
    private String transactionId;

}
