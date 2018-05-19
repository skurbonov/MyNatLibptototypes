package com.example.sardorbek.ptototypes.Model.new_requests;

/**
 * Created by sardorbek on 4/24/18.
 */

public class Token {
    private String token;
    private boolean isAdminToken;

    public Token() {
    }

    public Token(String token, boolean isAdminToken) {
        this.token = token;
        this.isAdminToken = isAdminToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdminToken() {
        return isAdminToken;
    }

    public void setAdminToken(boolean adminToken) {
        isAdminToken = adminToken;
    }
}
