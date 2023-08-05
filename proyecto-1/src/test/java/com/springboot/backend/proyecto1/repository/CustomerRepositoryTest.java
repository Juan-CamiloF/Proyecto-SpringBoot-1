package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IRegionRepository regionRepository;


    @Test
    void testSaveACustomer() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer test = customerRepository.save(customer);
        assertNotNull(test);
        assertEquals(customer.getNames(), test.getNames());
        assertEquals(customer.getSurnames(), test.getSurnames());
        assertEquals(customer.getEmail(), test.getEmail());
    }

    @Test
    void testSaveACustomerWithEmailIsUse() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        customerRepository.save(customer);
        customer.setId(null);
        assertNotNull(customer);
        assertThrows(DataIntegrityViolationException.class, () -> customerRepository.save(customer));
    }

    @Test
    void testSaveACustomerWithoutRegion() {
        Customer customer = CustomerData.CUSTOMER_1();
        customer.setRegion(null);
        System.out.println(customer);
        assertThrows(DataIntegrityViolationException.class, () -> customerRepository.save(customer));
    }

    @Test
    void testFindCustomerById() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer customerDB = customerRepository.save(customer);
        Optional<Customer> test = customerRepository.findById(customerDB.getId());
        assertTrue(test.isPresent());
        assertEquals(customerDB, test.get());
    }

    @Test
    void testCustomerExistsById() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer customerTest = customerRepository.save(customer);
        boolean test = customerRepository.existsByEmail(customer.getEmail());
        assertTrue(test);
        customerRepository.delete(customerTest);
        test = customerRepository.existsByEmail(customer.getEmail());
        assertFalse(test);
    }

    @Test
    void testFindAllCustomers() {
        Customer customer1 = CustomerData.CUSTOMER_1();
        Customer customer2 = CustomerData.CUSTOMER_2();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer1.setRegion(region);
        customer2.setRegion(region);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        List<Customer> test = customerRepository.findAll();
        assertNotNull(test);
        assertEquals(2, test.size());
    }

    @Test
    void testFindAllCustomersPaginatedByRegion() {
        Customer customer1 = CustomerData.CUSTOMER_1();
        Customer customer2 = CustomerData.CUSTOMER_2();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer1.setRegion(region);
        customer2.setRegion(region);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        Page<Customer> test = customerRepository.findAllByRegion(region, PageRequest.of(0, 10));
        assertNotNull(test);
        assertEquals(2, test.getTotalElements());
    }

    @Test
    void testUpdateACustomer() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer currentCustomer = customerRepository.save(customer);
        currentCustomer.setEmail(CustomerData.CUSTOMER_EMAIL_2);
        Customer test = customerRepository.save(currentCustomer);
        assertNotNull(test);
        assertEquals(1, customerRepository.findAll().size());
        assertEquals(currentCustomer.getId(), test.getId());
        assertEquals(currentCustomer.getNames(), test.getNames());
        assertEquals(currentCustomer.getSurnames(), test.getSurnames());
        assertNotEquals(customer.getEmail(), test.getEmail());
        assertNotEquals(customer.getUpdateAt(), test.getUpdateAt());
    }

    @Test
    void testDeleteACustomer() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer customerTest = customerRepository.save(customer);
        assertNotNull(customerTest);
        customerRepository.delete(customerTest);
        assertTrue(customerRepository.findAll().isEmpty());
    }

    @Test
    void testDeleteACustomerById() {
        Customer customer = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customer.setRegion(region);
        Customer customerTest = customerRepository.save(customer);
        assertNotNull(customerTest);
        customerRepository.deleteById(customerTest.getId());
        assertTrue(customerRepository.findAll().isEmpty());
    }


}
