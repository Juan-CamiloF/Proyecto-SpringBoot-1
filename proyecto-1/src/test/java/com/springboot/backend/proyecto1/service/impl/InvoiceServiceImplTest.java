package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateInvoice;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateInvoice;
import com.springboot.backend.proyecto1.data.InvoiceData;
import com.springboot.backend.proyecto1.exception.InvoiceException;
import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.repository.IInvoiceRepository;
import com.springboot.backend.proyecto1.service.ICustomerService;
import com.springboot.backend.proyecto1.service.IInvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    IInvoiceRepository invoiceRepository;

    @Mock
    ICustomerService customerService;

    InvoiceServiceImpl invoiceService;

    @BeforeEach
    void init() {
        invoiceService = new InvoiceServiceImpl(new ModelMapper(), invoiceRepository, customerService);
    }

    @Test
    void testSaveAnInvoice() {
        Invoice invoice = InvoiceData.INVOICE_1();
        when(customerService.exists(anyLong())).thenReturn(Boolean.TRUE);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        RequestCreateInvoice requestCreateInvoice = new RequestCreateInvoice();
        requestCreateInvoice.setCustomer(invoice.getCustomer());
        requestCreateInvoice.setDescription(invoice.getDescription());
        requestCreateInvoice.setObservation(invoice.getObservation());
        requestCreateInvoice.setDetails(invoice.getDetails());
        Invoice test = invoiceService.save(requestCreateInvoice);
        assertNotNull(test);
        assertEquals(invoice, test);
        verify(customerService).exists(anyLong());
        verify(invoiceRepository).save(any(Invoice.class));

    }

    @Test
    void testSaveAnInvoiceWithAnInvalidCustomer() {
        Invoice invoice = InvoiceData.INVOICE_1();
        when(customerService.exists(anyLong())).thenReturn(Boolean.FALSE);
        RequestCreateInvoice requestCreateInvoice = new RequestCreateInvoice();
        requestCreateInvoice.setCustomer(invoice.getCustomer());
        requestCreateInvoice.setDescription(invoice.getDescription());
        requestCreateInvoice.setObservation(invoice.getObservation());
        requestCreateInvoice.setDetails(invoice.getDetails());
        assertThrows(InvoiceException.class, () -> invoiceService.save(requestCreateInvoice));
        verify(customerService).exists(anyLong());
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testFindInvoiceById() {
        Invoice invoice = InvoiceData.INVOICE_1();
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));
        Invoice test = invoiceService.findById(1L);
        assertNotNull(test);
        assertEquals(invoice, test);
        verify(invoiceRepository).findById(anyLong());
    }

    @Test
    void testUpdateAnInvoice() {
        Invoice currentInvoice = InvoiceData.INVOICE_1();
        Invoice invoice = InvoiceData.INVOICE_UPDATE_1();
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        RequestUpdateInvoice requestUpdateInvoice = new RequestUpdateInvoice();
        requestUpdateInvoice.setId(currentInvoice.getId());
        requestUpdateInvoice.setDescription(invoice.getDescription());
        requestUpdateInvoice.setObservation(invoice.getObservation());
        requestUpdateInvoice.setDetails(new ArrayList<>(currentInvoice.getDetails()));
        requestUpdateInvoice.setCustomer(currentInvoice.getCustomer());
        requestUpdateInvoice.setCreatedAt(currentInvoice.getCreatedAt());
        Invoice test = invoiceService.update(currentInvoice, requestUpdateInvoice);
        assertNotNull(test);
        assertEquals(test, invoice);
        verify(invoiceRepository).save(any(Invoice.class));
    }

    @Test
    void testUpdateAnInvoiceWithInvalidId() {
        Invoice currentInvoice = InvoiceData.INVOICE_1();
        Invoice invoice = InvoiceData.INVOICE_UPDATE_1();
        RequestUpdateInvoice requestUpdateInvoice = new RequestUpdateInvoice();
        requestUpdateInvoice.setId(2L);
        requestUpdateInvoice.setDescription(invoice.getDescription());
        requestUpdateInvoice.setObservation(invoice.getObservation());
        requestUpdateInvoice.setDetails(currentInvoice.getDetails());
        requestUpdateInvoice.setCustomer(currentInvoice.getCustomer());
        requestUpdateInvoice.setCreatedAt(currentInvoice.getCreatedAt());
        assertThrows(InvoiceException.class, () -> invoiceService.update(currentInvoice, requestUpdateInvoice), "Factura no válida para actualizar");
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testUpdateAnInvoiceWithInvalidCustomer() {
        Invoice currentInvoice = InvoiceData.INVOICE_1();
        Invoice invoice = InvoiceData.INVOICE_UPDATE_1();
        RequestUpdateInvoice requestUpdateInvoice = new RequestUpdateInvoice();
        requestUpdateInvoice.setId(currentInvoice.getId());
        requestUpdateInvoice.setDescription(invoice.getDescription());
        requestUpdateInvoice.setObservation(invoice.getObservation());
        requestUpdateInvoice.setDetails(currentInvoice.getDetails());
        requestUpdateInvoice.setCustomer(new Customer());
        requestUpdateInvoice.setCreatedAt(currentInvoice.getCreatedAt());
        assertThrows(InvoiceException.class, () -> invoiceService.update(currentInvoice, requestUpdateInvoice), "No se puede actualizar el cliente en la factura");
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testFindInvoiceByNonExistentId() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InvoiceException.class, () -> invoiceService.findById(1L));
        verify(invoiceRepository).findById(anyLong());
    }

    @Test
    void testDeleteAnInvoiceById() {
        Invoice invoice = InvoiceData.INVOICE_1();
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.of(invoice));
        invoiceService.delete(1L);
        verify(invoiceRepository).findById(anyLong());
        verify(invoiceRepository).delete(any(Invoice.class));
    }

    @Test
    void testDeleteAnInvoiceByNonExistentId() {
        when(invoiceRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InvoiceException.class, () -> invoiceService.delete(1L));
        verify(invoiceRepository).findById(anyLong());
        verify(invoiceRepository, never()).deleteById(anyLong());
    }


}
