package com.springboot.backend.proyecto1.model;

import com.springboot.backend.proyecto1.enums.ERoleName;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for Role
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    /* Serial version ID */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20)
    @Enumerated(EnumType.STRING)
    private ERoleName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERoleName getName() {
        return name;
    }

    public void setName(ERoleName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " { " +
                "id:" + id +
                ", name:" + name +
                " } ";
    }
}
