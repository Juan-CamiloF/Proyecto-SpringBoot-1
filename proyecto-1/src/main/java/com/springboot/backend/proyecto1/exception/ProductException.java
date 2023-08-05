package com.springboot.backend.proyecto1.exception;

import com.springboot.backend.proyecto1.model.Product;

/**
 * Exception for {@link Product}
 */
public class ProductException extends RuntimeException {

    public ProductException(String message) {
        super(message);
    }
}
