package com.shahed.orderservice.seeder;

import com.shahed.orderservice.entity.Order;
import com.shahed.orderservice.entity.OrderStatus;
import com.shahed.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderDataSeeder implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (orderRepository.count() > 0) {
            log.info("Orders already seeded. Skipping.");
            return;
        }

        log.info("Seeding 10 sample orders...");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Order order = Order.builder()
                    .userId((long) (i % 3 + 1)) // userId between 1–3
                    .productId((long) i)
                    .sku("SKU-" + i)
                    .quantity(1 + random.nextInt(5)) // 1–5 quantity
                    .status(OrderStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            orderRepository.save(order);
            log.info("Seeded Order {} for SKU-{}", order.getId(), order.getSku());
        });

        log.info("Order seeding completed.");
    }
}
