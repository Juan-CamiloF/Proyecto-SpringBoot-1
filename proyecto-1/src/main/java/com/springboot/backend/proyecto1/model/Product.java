package com.springboot.backend.proyecto1.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity for Product
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {

    /* Serial version id */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist()
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return " { " +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", price:" + price +
                ", createdAt:" + createdAt +
                " } ";
    }
}
