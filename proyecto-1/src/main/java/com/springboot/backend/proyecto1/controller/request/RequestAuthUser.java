package com.springboot.backend.proyecto1.controller.request;

import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object of User for authentication
 */
public class RequestAuthUser {

    @NotNull
    private String username;

    @NotNull
    private String password;

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

    @Override
    public String toString() {
        return " { " +
                "username='" + username + '\'' +
                " } ";
    }
}
