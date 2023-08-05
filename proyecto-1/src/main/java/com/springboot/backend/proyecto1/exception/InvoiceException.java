package com.springboot.backend.proyecto1.exception;

import com.springboot.backend.proyecto1.model.Invoice;

/**
 * Exception for {@link Invoice} errors
 */
public class InvoiceException extends RuntimeException {
    public InvoiceException(String message) {
        super(message);
    }
}
