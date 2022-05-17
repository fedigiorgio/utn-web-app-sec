package org.utn.web.app.sec.utnwebappsec.dtos;

public class UserDto {
    private String username;
    private String fullName;
    private String mail;

    public UserDto(String username, String fullName, String mail) {
        this.username = username;
        this.fullName = fullName;
        this.mail = mail;
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
}
