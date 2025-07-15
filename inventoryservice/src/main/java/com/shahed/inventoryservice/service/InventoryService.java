package com.shahed.inventoryservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.inventoryservice.entity.Inventory;

public interface InventoryService {

    List<Inventory> getAllInventories();

    Optional<Inventory> getInventoryBySku(String sku);

    Optional<Inventory> getInventoryByProductId(Long productId);

    Inventory createInventory(Inventory inventory);
}
