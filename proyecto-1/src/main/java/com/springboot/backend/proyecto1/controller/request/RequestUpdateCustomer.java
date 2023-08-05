package com.springboot.backend.proyecto1.controller.request;

import com.springboot.backend.proyecto1.model.Region;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object of Customer for Update
 */
public class RequestUpdateCustomer {

    @NotNull
    private long id;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String names;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String surnames;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updateAt;

    @NotNull
    private Region region;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return " { " +
                "id:" + id +
                ", names:'" + names + '\'' +
                ", surnames:'" + surnames + '\'' +
                ", email:'" + email + '\'' +
                ", dateOfBirth:" + dateOfBirth +
                ", createdAt:" + createdAt +
                ", updateAt:" + updateAt +
                ", region:" + region +
                " } ";
    }
}
