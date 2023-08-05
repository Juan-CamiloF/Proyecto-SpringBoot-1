package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.exception.RegionException;
import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.repository.IRegionRepository;
import com.springboot.backend.proyecto1.service.IRegionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services for Region
 */
@Service
public class RegionServiceImpl implements IRegionService {

    private final IRegionRepository regionRepository;

    public RegionServiceImpl(IRegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Implementation method get a list Region
     *
     * @return List to {@link Region}
     */
    @Override
    public List<Region> findAll() {
        return regionRepository.findAll(Sort.by("name"));
    }

    /**
     * Implementation method get Region by id
     *
     * @param id - Region id
     * @return Region
     */
    @Override
    public Region findById(Long id) {
        return regionRepository.findById(id).orElseThrow(() -> new RegionException("La región no existe"));
    }


}
