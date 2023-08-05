package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.model.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RegionRepositoryTest {

    @Autowired
    IRegionRepository regionRepository;

    @Test
    void testListFindAllRegions() {
        regionRepository.save(RegionData.REGION_1());
        regionRepository.save(RegionData.REGION_2());
        List<Region> test = regionRepository.findAll(Sort.by("name"));
        assertNotNull(test);
        assertEquals(2, test.size());
    }

    @Test
    void testFindRegionById() {
        Region region = regionRepository.save(RegionData.REGION_1());
        Optional<Region> test = regionRepository.findById(region.getId());
        assertTrue(test.isPresent());
        assertEquals(region, test.get());
    }
}
