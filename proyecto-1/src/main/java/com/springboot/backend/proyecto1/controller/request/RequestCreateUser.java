package com.springboot.backend.proyecto1.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object of User for Create
 */
public class RequestCreateUser {

    @NotNull
    @Size(min = 5, max = 50)
    private String username;

    @NotNull
    @Size(min = 5, max = 50)
    private String password;

    private Set<String> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return " { " +
                "username:'" + username + '\'' +
                ", password:'" + password + '\'' +
                ", roles:" + roles +
                " } ";
    }
}
