package com.shahed.orderservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.orderservice.entity.Order;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(Long userId);

    Optional<Order> getOrderById(Long orderId);

    Order placeOrder(Order order);
}
