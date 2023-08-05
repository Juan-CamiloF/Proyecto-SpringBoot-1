package com.springboot.backend.proyecto1.controller.response;

public class ResponseAuthUser {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public ResponseAuthUser(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return " { " +
                "jwt='" + jwt + '\'' +
                " } ";
    }
}
