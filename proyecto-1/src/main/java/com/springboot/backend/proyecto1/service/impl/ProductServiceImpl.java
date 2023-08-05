package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateProduct;
import com.springboot.backend.proyecto1.model.Product;
import com.springboot.backend.proyecto1.repository.IProductRepository;
import com.springboot.backend.proyecto1.service.IProductService;
import com.springboot.backend.proyecto1.exception.ProductException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for Product
 */
@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ModelMapper modelMapper, IProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    /**
     * Implementation method save a Product
     *
     * @param requestCreateProduct - Product data to create
     * @return {@link Product}
     */
    @Override
    @Transactional
    public Product save(RequestCreateProduct requestCreateProduct) {
        if (productRepository.existsByName(requestCreateProduct.getName()))
            throw new ProductException("El nombre del producto ya está registrado");
        Product product = mapToEntity(requestCreateProduct);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findAllPaginated(Integer pageNumber, Integer pageSize) {
        PageRequest page = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(page);
    }

    /**
     * Implementation method for get a list of Product by name
     *
     * @param name to Product
     * @return List ot {@link Product}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Method for create Product by RequestCreateProduct
     *
     * @param requestCreateProduct -> requestCreateProduct
     * @return Product
     */
    private Product mapToEntity(RequestCreateProduct requestCreateProduct) {
        return modelMapper.map(requestCreateProduct, Product.class);
    }

}
