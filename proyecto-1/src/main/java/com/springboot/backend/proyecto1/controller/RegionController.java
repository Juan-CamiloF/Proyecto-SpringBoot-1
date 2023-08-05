package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.model.Region;
import com.springboot.backend.proyecto1.service.IRegionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link Region}
 */
@RestController
@RequestMapping("/api/v1/regions")
public class RegionController {

    private final Logger logger = LoggerFactory.getLogger(RegionController.class);
    private final IRegionService regionService;

    public RegionController(IRegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * Controller to list {@link Region}
     *
     * @return list to {@link Region}
     */
    @ApiOperation("Petición para listar todas las regiones")
    @GetMapping
    public ResponseEntity<List<Region>> getRegions() {
        logger.info("Petición para obtener todas las regiones");
        List<Region> response = regionService.findAll();
        logger.info("Regiones obtenidas {}", response.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
