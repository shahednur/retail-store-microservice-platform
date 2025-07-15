package com.shahed.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shahed.productservice.entity.Product;
import com.shahed.productservice.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Transactional
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<Product> getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
