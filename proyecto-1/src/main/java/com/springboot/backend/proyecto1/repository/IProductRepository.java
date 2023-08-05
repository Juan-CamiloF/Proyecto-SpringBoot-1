package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface of {@link Product} for CRUD operations
 */
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

}
