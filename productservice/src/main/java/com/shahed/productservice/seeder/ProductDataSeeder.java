package com.shahed.productservice.seeder;

import com.shahed.productservice.entity.Product;
import com.shahed.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            log.info("Product data already exists. Skipping seeding.");
            return;
        }

        log.info("Seeding 10 sample products...");

        IntStream.rangeClosed(1, 10).forEach(i -> {
            String sku = "SKU-" + i;
            String name = "Product " + i;
            String description = "This is product number " + i;
            double price = 10.0 + random.nextInt(90); // 10 to 99
            String category = i % 2 == 0 ? "Electronics" : "Clothing";

            Product product = Product.builder()
                    .sku(sku)
                    .name(name)
                    .description(description)
                    .price(price)
                    .category(category)
                    .build();

            productRepository.save(product);
            log.info("Created product: {} - {}", sku, name);
        });

        log.info("Seeding completed.");
    }
}
