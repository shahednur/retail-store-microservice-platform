package com.shahed.inventoryservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shahed.inventoryservice.dto.ApiResponse;
import com.shahed.inventoryservice.entity.Inventory;
import com.shahed.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Inventory>> createInventory(@RequestBody Inventory inventory) {
        log.info("Rest request to create inventory for SKU: {}", inventory.getSku());

        try {
            Inventory created = inventoryService.createInventory(inventory);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Inventory>builder()
                            .success(true)
                            .message("Inventory created successfully")
                            .data(created)
                            .build());
        } catch (Exception e) {
            log.info("Error creating inventory: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Inventory>builder()
                            .success(false)
                            .message("Failed to create inventory: " + e.getMessage())
                            .build());
        }
    }

    // Get all inventory records
    @GetMapping
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllInvetories() {
        log.info("Rest request to fetch all inventory records");
        List<Inventory> inventoryList = inventoryService.getAllInventories();
        return ResponseEntity.ok(
                ApiResponse.<List<Inventory>>builder()
                        .success(true)
                        .message("Fetched all inventories")
                        .data(inventoryList)
                        .build());
    }

    // Get inventory by SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryBySku(@PathVariable String sku) {
        log.info("Rest request to fetch inventory by SKU: {}", sku);

        return inventoryService.getInventoryBySku(sku)
                .map(inventory -> ResponseEntity.ok(ApiResponse.<Inventory>builder()
                        .success(true)
                        .message("Inventory found")
                        .data(inventory)
                        .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Inventory>builder()
                                .success(false)
                                .message("Inventory not found for sku: " + sku)
                                .build()));

    }

    // Get inventory by Product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryByProductId(@PathVariable Long productId) {
        log.info("REST request to fetch inventory by Product ID: {}", productId);

        return inventoryService.getInventoryByProductId(productId)
                .map(inventory -> ResponseEntity.ok(
                        ApiResponse.<Inventory>builder()
                                .success(true)
                                .message("Inventory found")
                                .data(inventory)
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.<Inventory>builder()
                                .success(false)
                                .message("Inventory not found for Product ID: " + productId)
                                .build()));
    }

}
