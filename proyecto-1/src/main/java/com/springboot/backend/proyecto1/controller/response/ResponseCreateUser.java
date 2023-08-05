package com.springboot.backend.proyecto1.controller.response;

import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.model.User;

import java.util.Set;

public class ResponseCreateUser {

    private String message;
    private Long id;
    private String username;
    private boolean active;
    private Set<Role> roles;

    public ResponseCreateUser(String message, User user) {
        this.message = message;
        this.id = user.getId();
        this.username = user.getUsername();
        this.active = user.isActive();
        this.roles = user.getRoles();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return " { " +
                "message:'" + message + '\'' +
                ", id:" + id +
                ", username:'" + username + '\'' +
                ", active:" + active +
                ", roles:" + roles +
                " } ";
    }
}
