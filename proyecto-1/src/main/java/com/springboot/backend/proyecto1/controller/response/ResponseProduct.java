package com.springboot.backend.proyecto1.controller.response;

import com.springboot.backend.proyecto1.model.Product;

public class ResponseProduct {

    private String message;

    private Product product;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ResponseProduct(String message, Product product) {
        this.message = message;
        this.product = product;
    }

    @Override
    public String toString() {
        return " { " +
                "message:'" + message + '\'' +
                ", product:" + product +
                " } ";
    }
}
