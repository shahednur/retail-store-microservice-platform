package com.shahed.productservice.service;

import java.util.List;
import java.util.Optional;

import com.shahed.productservice.entity.Product;

public interface ProductService {

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Optional<Product> getProductBySku(String sku);

    Product createProduct(Product product);
}
