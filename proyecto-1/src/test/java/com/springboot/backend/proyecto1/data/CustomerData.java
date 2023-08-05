package com.springboot.backend.proyecto1.data;


import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CustomerData {
    public static final Long CUSTOMER_ID_1 = 1L;
    public static final String CUSTOMER_NAME_1 = "Nombre 1";
    public static final String CUSTOMER_SURNAMES_1 = "Apellidos 1";
    public static final String CUSTOMER_EMAIL_1 = "correoElectronico1@gmail.com";
    public static final Long CUSTOMER_ID_2 = 2L;
    public static final String CUSTOMER_NAME_2 = "Nombre 2";
    public static final String CUSTOMER_SURNAMES_2 = "Apellidos 2";
    public static final String CUSTOMER_EMAIL_2 = "correoElectronico2@gmail.com";

    public static Region REGION() {
        Region region = new Region();
        region.setId(1L);
        region.setName("Región");
        return region;
    }

    public static Customer CUSTOMER_1() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_1);
        customer.setNames(CustomerData.CUSTOMER_NAME_1);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        customer.setFilename(null);
        customer.setUrlFilename(null);
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static Customer CUSTOMER_2() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_2);
        customer.setNames(CustomerData.CUSTOMER_NAME_2);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_2);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_2);
        customer.setFilename(null);
        customer.setUrlFilename(null);
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static Customer CUSTOMER_1_UPDATE_NAME() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_1);
        customer.setNames(CustomerData.CUSTOMER_NAME_2);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        customer.setFilename(null);
        customer.setUrlFilename(null);
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static Customer CUSTOMER_1_UPDATE_EMAIL() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_1);
        customer.setNames(CustomerData.CUSTOMER_NAME_1);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_2);
        customer.setFilename(null);
        customer.setUrlFilename(null);
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static Customer CUSTOMER_1_WITH_FILE() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_1);
        customer.setNames(CustomerData.CUSTOMER_NAME_1);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        customer.setFilename("image.png");
        customer.setUrlFilename("http://localhost/api/v1/customers/images/image.png");
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static Customer CUSTOMER_1_UPDATE_WITH_FILE() {
        Customer customer = new Customer();
        customer.setId(CustomerData.CUSTOMER_ID_1);
        customer.setNames(CustomerData.CUSTOMER_NAME_1);
        customer.setSurnames(CustomerData.CUSTOMER_SURNAMES_1);
        customer.setDateOfBirth(LocalDate.now());
        customer.setRegion(REGION());
        customer.setEmail(CustomerData.CUSTOMER_EMAIL_1);
        customer.setFilename("image2.png");
        customer.setUrlFilename("http://localhost/api/v1/customers/images/image2.png");
        customer.setUpdateAt(LocalDateTime.now());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public static List<Customer> CUSTOMER_LIST() {
        return Arrays.asList(CUSTOMER_1(), CUSTOMER_2());
    }

    public static Page<Customer> CUSTOMER_PAGE() {
        return new PageImpl<>(CUSTOMER_LIST());
    }

}
