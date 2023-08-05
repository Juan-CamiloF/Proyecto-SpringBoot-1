package com.springboot.backend.proyecto1.controller.response;

import com.springboot.backend.proyecto1.model.Customer;

public class ResponseCustomer {
    private String message;
    private Customer customer;

    public ResponseCustomer(String message, Customer customer) {
        this.message = message;
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return " { " + "message: '" + message + '\'' + ", customer: " + customer + " } ";
    }
}
