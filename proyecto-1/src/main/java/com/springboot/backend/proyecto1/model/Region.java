package com.springboot.backend.proyecto1.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for Region
 */
@Entity
@Table(name = "regions")
public class Region implements Serializable {

    /* Serial version ID */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


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


    @Override
    public String toString() {
        return " { " +
                "id:" + id +
                ", name:'" + name + '\'' +
                " } ";
    }
}
