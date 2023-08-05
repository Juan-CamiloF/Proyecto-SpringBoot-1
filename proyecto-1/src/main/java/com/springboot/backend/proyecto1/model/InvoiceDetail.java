package com.springboot.backend.proyecto1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity for Invoice details
 */
@Entity
@Table(name = "invoice_details")
public class InvoiceDetail implements Serializable {

    /* Serial version ID */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    public BigDecimal getCalculateAmount() {
        return BigDecimal.valueOf(this.amount.doubleValue() * product.getPrice());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return " { " +
                "id:" + id +
                ", amount:" + amount +
                ", product:" + product +
                ", calculateAmount:" + getCalculateAmount() +
                "}";
    }
}
