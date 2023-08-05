package com.springboot.backend.proyecto1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.backend.proyecto1.controller.request.RequestCreateProduct;
import com.springboot.backend.proyecto1.data.ProductData;
import com.springboot.backend.proyecto1.model.Product;
import com.springboot.backend.proyecto1.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    IProductService productService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateAProduct() throws Exception {
        Product product = ProductData.PRODUCT_1();
        when(productService.save(any())).thenReturn(product);
        RequestCreateProduct requestCreateProduct = new RequestCreateProduct();
        requestCreateProduct.setName(product.getName());
        requestCreateProduct.setPrice(product.getPrice());
        String request = asJsonString(requestCreateProduct);
        mvc.perform(post("/api/v1/products")
                        .with(csrf())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El producto fue creado con éxito"))
                .andExpect(jsonPath("$.product").isNotEmpty())
                .andExpect(jsonPath("$.product.id").value(product.getId()))
                .andExpect(jsonPath("$.product.name").value(product.getName()))
                .andExpect(jsonPath("$.product.price").value(product.getPrice()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetProductPage() throws Exception {
        Page<Product> products = ProductData.PRODUCT_PAGE();
        when(productService.findAllPaginated(anyInt(), anyInt())).thenReturn(products);
        mvc.perform(get("/api/v1/products?pageNumber=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetProductsByName() throws Exception {
        List<Product> products = ProductData.PRODUCT_LIST();
        when(productService.findAllByName(anyString())).thenReturn(products);
        mvc.perform(get("/api/v1/products/Pro"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }
}
