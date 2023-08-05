package com.springboot.backend.proyecto1.controller.request;

import com.springboot.backend.proyecto1.model.Customer;
import com.springboot.backend.proyecto1.model.InvoiceDetail;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object of Invoice for Create
 */
public class RequestCreateInvoice {
    @NotNull
    private String observation;

    private String description;

    @NotNull
    private Customer customer;

    @NotNull
    private List<InvoiceDetail> details;

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return " { " +
                "observation:'" + observation + '\'' +
                ", description:'" + description + '\'' +
                ", customer:" + customer +
                ", details:" + details +
                '}';
    }
}
