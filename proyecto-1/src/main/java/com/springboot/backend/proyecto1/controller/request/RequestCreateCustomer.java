package com.springboot.backend.proyecto1.controller.request;

import com.springboot.backend.proyecto1.model.Region;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Data Transfer Object of Customer for Create
 */
public class RequestCreateCustomer {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String names;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String surnames;

    @NotEmpty
    @Email
    @Size(max = 50)
    private String email;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Region region;

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
                "names:'" + names + '\'' +
                ", surnames:'" + surnames + '\'' +
                ", email:'" + email + '\'' +
                ", dateOfBirth:" + dateOfBirth +
                ", region:" + region +
                " } ";
    }
}
