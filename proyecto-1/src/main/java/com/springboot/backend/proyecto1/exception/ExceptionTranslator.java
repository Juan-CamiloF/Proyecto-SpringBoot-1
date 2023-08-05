package com.springboot.backend.proyecto1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * Exception Translator
 * */
@RestControllerAdvice
public class ExceptionTranslator {

    private static final String ERROR = "ERROR";

    private static final String URL_USERS = "/api/v1/auth/new";

    private static final String URL_CUSTOMERS = "/api/v1/customers";

    private static final String URL_INVOICES = "/api/v1/invoices";

    private static final String URL_PRODUCTS = "api/v1/product";

    /**
     * Method for User exceptions
     *
     * @param e {@link UserException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<Object> handleUserException(UserException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_USERS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for Role exceptions
     *
     * @param e {@link RoleException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {RoleException.class})
    public ResponseEntity<Object> handleRoleException(RoleException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_USERS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for Customer exceptions
     *
     * @param e {@link CustomerException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {CustomerException.class})
    public ResponseEntity<Object> handlerCustomerException(CustomerException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_CUSTOMERS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for Invoice exceptions
     *
     * @param e {@link InvoiceException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {InvoiceException.class})
    public ResponseEntity<Object> handleInvoiceException(InvoiceException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_INVOICES);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for Product exceptions
     *
     * @param e {@link ProductException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {ProductException.class})
    public ResponseEntity<Object> handleProductException(ProductException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_PRODUCTS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for Region exceptions
     *
     * @param e {@link RegionException}
     * @return badRequestMessage with {@code 400}
     */
    @ExceptionHandler(value = {RegionException.class})
    public ResponseEntity<Object> handlerRegionException(RegionException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        URL_CUSTOMERS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Method for Upload files exceptions
     *
     * @param e {@link UploadFileException}
     * @return badRequestMessage with {@code 500}
     */
    @ExceptionHandler(value = {UploadFileException.class})
    public ResponseEntity<Object> handlerUploadFileException(UploadFileException e) {
        BadRequestMessage badRequestMessage =
                new BadRequestMessage(ERROR,
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ZonedDateTime.now(),
                        URL_CUSTOMERS);
        return new ResponseEntity<>(badRequestMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method for invalid fields
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return errors;
    }
}
