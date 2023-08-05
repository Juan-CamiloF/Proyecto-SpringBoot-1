package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.model.Region;

import java.util.List;

/**
 * Interface the Region
 */
public interface IRegionService {

    /**
     * Get a list to Region
     */
    List<Region> findAll();

    /**
     * Get a Region by id
     */
    Region findById(Long id);
    
}
