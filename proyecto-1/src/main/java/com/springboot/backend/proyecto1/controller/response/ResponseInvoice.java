package com.springboot.backend.proyecto1.controller.response;

import com.springboot.backend.proyecto1.model.Invoice;

public class ResponseInvoice {

    private String message;
    private Invoice invoice;

    public ResponseInvoice(String message, Invoice invoice) {
        this.message = message;
        this.invoice = invoice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return " { " +
                "message:'" + message + '\'' +
                ", invoice:" + invoice +
                " } ";
    }
}
