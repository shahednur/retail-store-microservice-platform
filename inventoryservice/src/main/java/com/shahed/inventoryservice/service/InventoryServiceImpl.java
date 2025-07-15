package com.shahed.inventoryservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shahed.inventoryservice.entity.Inventory;
import com.shahed.inventoryservice.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public List<Inventory> getAllInventories() {
        log.info("Fetching all inventory records");
        return inventoryRepository.findAll();
    }

    @Transactional
    public Optional<Inventory> getInventoryBySku(String sku) {
        log.info("Fetching inventory by SKU: {}", sku);
        return inventoryRepository.findBySku(sku);
    }

    @Transactional
    public Optional<Inventory> getInventoryByProductId(Long productId) {
        log.info("Fetching inventory by productId: {}", productId);

        return inventoryRepository.findByProductId(productId);
    }

    @Transactional
    public Inventory createInventory(Inventory inventory) {
        log.info("Creating inventory record for SKU:", inventory);

        return inventoryRepository.save(inventory);
    }
}
