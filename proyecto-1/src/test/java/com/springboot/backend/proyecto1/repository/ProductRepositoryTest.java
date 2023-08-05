package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.ProductData;
import com.springboot.backend.proyecto1.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    IProductRepository productRepository;

    @Test
    void testSaveAProduct() {
        Product product = ProductData.PRODUCT_1();
        Product test = productRepository.save(product);
        assertNotNull(test);
        assertEquals(product.getName(), test.getName());
        assertEquals(product.getPrice(), test.getPrice());
    }

    @Test
    void testPaginatedFindAllProducts() {
        Product product1 = ProductData.PRODUCT_1();
        Product product2 = ProductData.PRODUCT_2();
        productRepository.save(product1);
        productRepository.save(product2);
        Page<Product> test = productRepository.findAll(PageRequest.of(0, 10));
        assertNotNull(test);
        assertEquals(2, test.getTotalElements());
    }

    @Test
    void testFindProductsByName() {
        Product product1 = ProductData.PRODUCT_1();
        Product product2 = ProductData.PRODUCT_2();
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> test = productRepository.findByNameContainingIgnoreCase(ProductData.PRODUCT_1().getName().substring(0, 3));
        assertNotNull(test);
        assertEquals(2, test.size());
    }

    @Test
    void testExistsByName() {
        Product product = ProductData.PRODUCT_1();
        productRepository.save(product);
        boolean test = productRepository.existsByName(product.getName());
        assertTrue(test);
    }
}
