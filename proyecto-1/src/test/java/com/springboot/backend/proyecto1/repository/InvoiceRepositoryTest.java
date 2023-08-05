package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.CustomerData;
import com.springboot.backend.proyecto1.data.InvoiceData;
import com.springboot.backend.proyecto1.data.RegionData;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.model.Region;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvoiceRepositoryTest {

    @Autowired
    IInvoiceRepository invoiceRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IRegionRepository regionRepository;

    @Test
    void testSaveAnInvoice() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Invoice invoice = InvoiceData.INVOICE_1();
        invoice.setDetails(null);
        invoice.setCustomer(customer);
        Invoice test = invoiceRepository.save(invoice);
        assertNotNull(test);
        assertEquals(invoice.getObservation(), test.getObservation());
        assertEquals(invoice.getDescription(), test.getDescription());
    }

    @Test
    void testSaveAnInvoiceWithoutCustomer() {
        Invoice invoice = InvoiceData.INVOICE_1();
        invoice.setDetails(null);
        assertThrows(DataIntegrityViolationException.class, () -> invoiceRepository.save(invoice));
    }

    @Test
    void testFindInvoiceById() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Invoice invoiceData = InvoiceData.INVOICE_1();
        invoiceData.setDetails(null);
        invoiceData.setCustomer(customer);
        Invoice invoice = invoiceRepository.save(invoiceData);
        Optional<Invoice> test = invoiceRepository.findById(invoice.getId());
        assertTrue(test.isPresent());
        assertEquals(invoice, test.get());
    }

    @Test
    void testUpdateAnInvoice() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Invoice invoiceData = InvoiceData.INVOICE_1();
        invoiceData.setDetails(null);
        invoiceData.setCustomer(customer);
        Invoice currentInvoice = invoiceRepository.save(invoiceData);
        currentInvoice.setObservation("Observación actualizada");
        Invoice updatedInvoice = invoiceRepository.save(currentInvoice);
        assertNotNull(updatedInvoice);
        assertEquals("Observación actualizada", updatedInvoice.getObservation());
    }

    @Test
    void testDeleteAnInvoice() {
        Customer customerData = CustomerData.CUSTOMER_1();
        Region region = regionRepository.save(RegionData.REGION_1());
        customerData.setRegion(region);
        Customer customer = customerRepository.save(customerData);
        Invoice invoiceData = InvoiceData.INVOICE_1();
        invoiceData.setDetails(null);
        invoiceData.setCustomer(customer);
        Invoice invoice = invoiceRepository.save(invoiceData);
        invoiceRepository.delete(invoice);
        assertTrue(invoiceRepository.findAll().isEmpty());
    }
}
