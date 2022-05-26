package org.utn.web.app.sec.utnwebappsec.entities;

import java.util.UUID;

public class User {
    private final String username;
    private final String fullName;
    private final String mail;
    private final String password;
    private String token;

    public User(String username, String fullName, String mail, String password, String token) {
        this.username = username;
        this.fullName = fullName;
        this.mail = mail;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void login() {
        token = UUID.randomUUID().toString();
    }
}
