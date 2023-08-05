package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateProduct;
import com.springboot.backend.proyecto1.data.ProductData;
import com.springboot.backend.proyecto1.exception.ProductException;
import com.springboot.backend.proyecto1.model.Product;
import com.springboot.backend.proyecto1.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    IProductRepository productRepository;


    ProductServiceImpl productService;

    @BeforeEach
    void init() {
        productService = new ProductServiceImpl(new ModelMapper(), productRepository);
    }

    @Test
    void testSaveAProduct() {
        Product product = ProductData.PRODUCT_1();
        when(productRepository.existsByName(anyString())).thenReturn(Boolean.FALSE);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        RequestCreateProduct requestCreateProduct = new RequestCreateProduct();
        requestCreateProduct.setName(product.getName());
        requestCreateProduct.setPrice(product.getPrice());
        Product test = productService.save(requestCreateProduct);
        assertNotNull(test);
        assertEquals(product, test);
        verify(productRepository).existsByName(anyString());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testSaveAProductWithNameExistent() {
        Product product = ProductData.PRODUCT_1();
        when(productRepository.existsByName(anyString())).thenReturn(Boolean.TRUE);
        RequestCreateProduct requestCreateProduct = new RequestCreateProduct();
        requestCreateProduct.setName(product.getName());
        requestCreateProduct.setPrice(product.getPrice());
        assertThrows(ProductException.class, () -> productService.save(requestCreateProduct));
        verify(productRepository).existsByName(anyString());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testPaginatedFindAllProduct() {
        Page<Product> page = ProductData.PRODUCT_PAGE();
        when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        assertEquals(2, productService.findAllPaginated(0, 10).getTotalElements());
    }

    @Test
    void testFindAllByProductName() {
        List<Product> list = ProductData.PRODUCT_LIST();
        when(productRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(list);
        assertEquals(1, productService.findAllByName(anyString()).size());
    }

}
