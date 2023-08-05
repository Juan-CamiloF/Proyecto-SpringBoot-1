package com.springboot.backend.proyecto1.exception;

import com.springboot.backend.proyecto1.model.Customer;

/**
 * Exception for {@link Customer} errors
 */
public class CustomerException extends RuntimeException {
    public CustomerException(String message) {
        super(message);
    }
}
