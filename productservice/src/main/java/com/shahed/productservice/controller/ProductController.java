package com.shahed.productservice.controller;

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

import com.shahed.productservice.dto.ApiResponse;
import com.shahed.productservice.entity.Product;
import com.shahed.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        log.info("Rest request to create product: {}", product.getName());

        try {
            Product created = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<Product>builder()
                    .success(true)
                    .message("Product created successfully")
                    .data(created)
                    .build());

        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<Product>builder()
                            .success(false)
                            .message("Failed to create Product")
                            .build());
        }
    }

    // Get all products
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        log.info("Rest request to fetch all products");

        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(
                ApiResponse.<List<Product>>builder()
                        .success(true)
                        .message("Fetched all Products")
                        .data(productList)
                        .build());
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        log.info("Rest request to fetch product by ID: {}", id);

        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(
                        ApiResponse.<Product>builder()
                                .success(true)
                                .message("Product by id")
                                .data(product)
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<Product>builder()
                                .success(false)
                                .message("Product not found")
                                .build()));
    }

    // Get product by sku
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<Product>> getProductBySku(@PathVariable String sku) {
        log.info("Rest request to fetch product by SKU: {}", sku);

        return productService.getProductBySku(sku)
                .map(product -> ResponseEntity.ok(
                        ApiResponse.<Product>builder()
                                .success(true)
                                .message("Product found by sku")
                                .data(product)
                                .build()))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.<Product>builder()
                                .success(false)
                                .message("Product not found")
                                .build()));
    }
}
