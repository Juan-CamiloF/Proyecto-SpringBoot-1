package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.data.InvoiceData;
import com.springboot.backend.proyecto1.data.ProductData;
import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class InvoiceDetailsRepositoryTest {

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IInvoiceRepository invoiceRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IRegionRepository regionRepository;


    @Test
    void testSaveInvoiceWithInvoiceDetails() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Product product1 = productRepository.save(ProductData.PRODUCT_1());
        Product product2 = productRepository.save(ProductData.PRODUCT_2());
        InvoiceDetail invoiceDetail1 = InvoiceData.INVOICE_DETAIL_1();
        invoiceDetail1.setId(null);
        invoiceDetail1.setProduct(product1);
        InvoiceDetail invoiceDetail2 = InvoiceData.INVOICE_DETAIL_1();
        invoiceDetail2.setProduct(product2);
        invoiceDetail2.setId(null);
        Invoice invoice = InvoiceData.INVOICE_1();
        invoice.setCustomer(customer);
        invoice.setDetails(List.of(invoiceDetail1, invoiceDetail2));
        Invoice test = invoiceRepository.save(invoice);
        assertNotNull(test);
        assertEquals(2, test.getDetails().size());
    }

    @Test
    void testUpdateInvoiceWithInvoiceDetails() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Product product1 = productRepository.save(ProductData.PRODUCT_1());
        Product product2 = productRepository.save(ProductData.PRODUCT_2());
        InvoiceDetail invoiceDetail1 = InvoiceData.INVOICE_DETAIL_1();
        invoiceDetail1.setId(null);
        invoiceDetail1.setProduct(product1);
        InvoiceDetail invoiceDetail2 = InvoiceData.INVOICE_DETAIL_1();
        invoiceDetail2.setProduct(product2);
        invoiceDetail2.setId(null);
        Invoice invoiceData = InvoiceData.INVOICE_1();
        invoiceData.setCustomer(customer);
        invoiceData.setDetails(Arrays.asList(invoiceDetail1, invoiceDetail2));
        Invoice invoice = invoiceRepository.save(invoiceData);
        invoice.getDetails().clear();
        invoice.getDetails().add(invoiceDetail1);
        Invoice test = invoiceRepository.save(invoice);
        assertNotNull(test);
        assertEquals(1, test.getDetails().size());
    }
}
