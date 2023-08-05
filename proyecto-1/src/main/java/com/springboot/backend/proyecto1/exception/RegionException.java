package com.springboot.backend.proyecto1.exception;

import com.springboot.backend.proyecto1.model.Region;

/**
 * Exception for {@link Region} errors
 */
public class RegionException extends RuntimeException {
    public RegionException(String message) {
        super(message);
    }
}
