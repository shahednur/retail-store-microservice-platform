package com.shahed.productservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shahed.productservice.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
}
