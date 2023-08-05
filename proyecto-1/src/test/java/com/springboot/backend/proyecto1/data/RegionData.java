package com.springboot.backend.proyecto1.data;

import com.springboot.backend.proyecto1.model.Region;

import java.util.List;

public class RegionData {

    public static Region REGION_1() {
        Region region = new Region();
        region.setId(1L);
        region.setName("Región1");
        return region;
    }

    public static Region REGION_2() {
        Region region = new Region();
        region.setId(2L);
        region.setName("Región2");
        return region;
    }

    public static List<Region> REGIONS() {
        return List.of(REGION_1(), REGION_2());
    }

}
