package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.exception.RegionException;
import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.repository.IRegionRepository;
import com.springboot.backend.proyecto1.service.IRegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    IRegionRepository regionRepository;

    RegionServiceImpl regionService;

    @BeforeEach
    void init() {
        regionService = new RegionServiceImpl(regionRepository);
    }

    @Test
    void testListFindAllRegions() {
        when(regionRepository.findAll(Sort.by("name"))).thenReturn(RegionData.REGIONS());
        List<Region> regionList = regionService.findAll();
        assertEquals(2, regionList.size());
        assertEquals(regionList.get(0).getName(), RegionData.REGION_1().getName());
        assertEquals(regionList.get(0).getId(), RegionData.REGION_1().getId());
        assertEquals(regionList.get(1).getId(), RegionData.REGION_2().getId());
        assertEquals(regionList.get(1).getName(), RegionData.REGION_2().getName());
        verify(regionRepository).findAll(Sort.by("name"));
    }

    @Test
    void testFindRegionById() {
        when(regionRepository.findById(1L)).thenReturn(Optional.of(RegionData.REGION_1()));
        Region region = regionService.findById(1L);
        assertEquals(region.getId(), RegionData.REGION_1().getId());
        assertEquals(region.getName(), RegionData.REGION_1().getName());
        verify(regionRepository).findById(1L);
    }

    @Test
    void testFindRegionByIdNonExistent() {
        when(regionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegionException.class, () -> regionService.findById(1L));
        verify(regionRepository).findById(1L);
    }

}
