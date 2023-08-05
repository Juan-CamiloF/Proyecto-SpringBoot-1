package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.controller.request.RequestCreateCustomer;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateCustomer;
import com.springboot.backend.proyecto1.model.Customer;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface the Customer
 */
public interface ICustomerService {

    /**
     * Save a Customer
     */
    Customer save(RequestCreateCustomer cliente);

    /**
     * Get a Customer by id
     */
    Customer findById(Long id);

    /**
     * Get a list of Customer
     */
    List<Customer> findAll();

    /**
     * Get a page of Customer
     */
    Page<Customer> findAllPaginated(Integer pageNumber, Integer pageSize);

    /**
     * Get a filtered list of Customer
     */
    Page<Customer> findAllByRegion(Long id, Integer pageNumber, Integer pageSize);

    /**
     * Update a Customer
     */
    Customer update(Customer currentCliente, RequestUpdateCustomer newCliente);

    /**
     * Delete a Customer
     */
    void delete(Long id);

    /**
     * Exists a Customer
     */
    boolean exists(Long id);

    /**
     * Upload file to Customer
     */
    Customer uploadResource(Customer customer, MultipartFile file);

    /**
     * Get file to Customer
     */
    Resource getResource(String fileName);

    /**
     * Delete file to Customer
     */
    Customer deleteResource(Customer customer, String fileName);
}
