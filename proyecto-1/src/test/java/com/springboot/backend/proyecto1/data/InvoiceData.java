package com.springboot.backend.proyecto1.data;

import com.springboot.backend.proyecto1.model.Invoice;
import com.springboot.backend.proyecto1.model.InvoiceDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class InvoiceData {

    public static InvoiceDetail INVOICE_DETAIL_1() {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setId(1L);
        invoiceDetail.setAmount(1);
        invoiceDetail.setProduct(ProductData.PRODUCT_1());
        return invoiceDetail;
    }

    public static Invoice INVOICE_UPDATE_1() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setDescription("Descripción Actualizada");
        invoice.setObservation("Observación Actualizada");
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setDetails(new ArrayList<>(Arrays.asList(INVOICE_DETAIL_1())));
        invoice.setCustomer(CustomerData.CUSTOMER_1());
        return invoice;
    }

    public static Invoice INVOICE_1() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setDescription("Descripción");
        invoice.setObservation("Observación");
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setDetails(new ArrayList<>(Arrays.asList(INVOICE_DETAIL_1())));
        invoice.setCustomer(CustomerData.CUSTOMER_1());
        return invoice;
    }
}
