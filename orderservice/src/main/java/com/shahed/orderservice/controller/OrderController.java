package com.shahed.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shahed.orderservice.dto.ApiResponse;
import com.shahed.orderservice.entity.Order;
import com.shahed.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder(@RequestBody Order order) {
        log.info("Rest request to place order: {}", order);

        try {
            Order placed = orderService.placeOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Order>builder()
                            .success(true)
                            .message("Order placed successfully")
                            .data(placed)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Order>builder()
                            .success(false)
                            .message("Failed to place order" + e.getMessage())
                            .build());
        }
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();

        return ResponseEntity.ok(
                ApiResponse.<List<Order>>builder()
                        .success(true)
                        .message("Fetched all orders")
                        .data(orderList)
                        .build());
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> ResponseEntity.ok(
                        ApiResponse.<Order>builder()
                                .success(true)
                                .message("Order found")
                                .data(order)
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Order>builder()
                                .success(false)
                                .message("Order not found")
                                .build()));
    }

    // Get orders by User Id
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByUser(@PathVariable Long userId) {

        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(
                ApiResponse.<List<Order>>builder()
                        .success(true)
                        .message("Fetched orders for user Id: " + userId)
                        .data(orders)
                        .build());
    }
}
