package com.shahed.inventoryservice.seeder;

import com.shahed.inventoryservice.entity.Inventory;
import com.shahed.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryDataSeeder implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (inventoryRepository.count() > 0) {
            log.info("Inventory data already exists. Skipping seeding.");
            return;
        }

        log.info("Seeding 10 sample inventory records...");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Inventory inventory = Inventory.builder()
                    .productId((long) i)
                    .sku("SKU-" + i)
                    .quantity(5 + random.nextInt(20)) // Quantity between 5–25
                    .build();

            inventoryRepository.save(inventory);
            log.info("Created inventory for SKU-{} with quantity {}", i, inventory.getQuantity());
        });

        log.info("Seeding completed.");
    }
}
