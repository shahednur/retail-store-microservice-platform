package com.shahed.paymentservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shahed.paymentservice.dto.ApiResponse;
import com.shahed.paymentservice.entity.Payment;
import com.shahed.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    // Create a new payment
    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> processPayments(@RequestBody Payment payment) {
        log.info("Rest request to process payment for order: {}", payment.getOrderId());

        try {
            Payment processed = paymentService.processPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Payment>builder()
                            .success(true)
                            .message("Payment processed successfully")
                            .data(processed)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Payment>builder()
                            .success(false)
                            .message("Payment not processed " + e.getMessage())
                            .build());
        }
    }

    // Get all payments
    @GetMapping
    public ResponseEntity<ApiResponse<List<Payment>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.<List<Payment>>builder()
                .success(true)
                .message("Payments fetched successfully")
                .data(payments)
                .build());
    }

    // Get payments by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Payment>>> getPaymentsbyUserId(@PathVariable Long userId) {
        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.<List<Payment>>builder()
                .success(true)
                .message("Fetched payments by userId")
                .data(payments)
                .build());
    }

    // Get payment by orderId
    @GetMapping("/order/{order}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByOrderId(@PathVariable Long orderId) {
        Optional<Payment> payment = paymentService.getPaymentByOrderId(orderId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(ApiResponse.<Payment>builder()
                    .success(true)
                    .message("Fetched payment by orderId")
                    .data(payment.get())
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<Payment>builder()
                            .success(false)
                            .message("Payment not found " + orderId)
                            .build());
        }
    }
}
