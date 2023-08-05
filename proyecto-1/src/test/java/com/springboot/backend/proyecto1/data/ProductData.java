package com.springboot.backend.proyecto1.data;

import com.springboot.backend.proyecto1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class ProductData {

    public static Product PRODUCT_1() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Producto1");
        product.setPrice(1.0);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    public static Product PRODUCT_2() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Producto2");
        product.setPrice(1.0);
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    public static Page<Product> PRODUCT_PAGE() {
        return new PageImpl<>(List.of(PRODUCT_1(), PRODUCT_2()));
    }

    public static List<Product> PRODUCT_LIST() {
        return List.of(PRODUCT_1());
    }

}
