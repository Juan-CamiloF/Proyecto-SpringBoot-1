package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.service.IRegionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RegionController.class)
class RegionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    IRegionService regionService;

    @Test
    @WithMockUser
    void TestGetRegions() throws Exception {
        when(regionService.findAll()).thenReturn(RegionData.REGIONS());
        mvc.perform(get("/api/v1/regions")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
