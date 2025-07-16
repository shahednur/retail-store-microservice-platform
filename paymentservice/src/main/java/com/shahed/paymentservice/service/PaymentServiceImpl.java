package com.shahed.paymentservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shahed.paymentservice.entity.Payment;
import com.shahed.paymentservice.entity.PaymentStatus;
import com.shahed.paymentservice.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Transactional
    public Optional<Payment> getPaymentByOrderId(Long orderId) {
        List<Payment> result = paymentRepository.findByOrderId(orderId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Transactional
    public Payment processPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}
