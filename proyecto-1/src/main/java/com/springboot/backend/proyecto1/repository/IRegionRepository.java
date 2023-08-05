package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface of {@link Region} for CRUD operations
 */
public interface IRegionRepository extends JpaRepository<Region, Long> {
}
