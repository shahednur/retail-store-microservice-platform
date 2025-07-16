package com.shahed.paymentservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.paymentservice.entity.Payment;

public interface PaymentService {

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByUserId(Long userId);

    Optional<Payment> getPaymentByOrderId(Long orderId);

    Payment processPayment(Payment payment);
}
