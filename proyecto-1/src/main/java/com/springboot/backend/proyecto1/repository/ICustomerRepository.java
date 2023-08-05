package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of {@link Customer} for CRUD operations
 */
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Customer exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Page of Customer in the Region
     */
    Page<Customer> findAllByRegion(Region region, Pageable pageable);
}
