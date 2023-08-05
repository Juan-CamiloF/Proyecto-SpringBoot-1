package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.controller.request.RequestCreateInvoice;
import com.springboot.backend.proyecto1.controller.request.RequestUpdateInvoice;
import com.springboot.backend.proyecto1.model.Invoice;

/**
 * Interface the Invoice
 */
public interface IInvoiceService {

    /**
     * Save a Invoice
     */
    Invoice save(RequestCreateInvoice invoice);

    /**
     * Get an Invoice by id
     */
    Invoice findById(Long id);

    /**
     * Update a Invoice
     */
    Invoice update(Invoice currentInvoice, RequestUpdateInvoice newInvoice);

    /**
     * Delete an Invoice
     */
    void delete(Long id);
}
