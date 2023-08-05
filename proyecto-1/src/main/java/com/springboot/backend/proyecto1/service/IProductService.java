package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.controller.request.RequestCreateProduct;
import com.springboot.backend.proyecto1.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface the Product
 */
public interface IProductService {

    /**
     * Save a Product
     */
    Product save(RequestCreateProduct requestCreateProduct);

    /**
     * Get a page of Product
     */
    Page<Product> findAllPaginated(Integer pageNumber, Integer pageSize);

    /**
     * Get a list of Product by name
     */
    List<Product> findAllByName(String name);

}
