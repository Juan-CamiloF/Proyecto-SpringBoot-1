package com.springboot.backend.proyecto1.controller.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object of Customer for Create
 */
public class RequestCreateProduct {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Range(min = 0, max = 100000000)
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " { " +
                "name:'" + name + '\'' +
                ", price:" + price +
                '}';
    }
}
