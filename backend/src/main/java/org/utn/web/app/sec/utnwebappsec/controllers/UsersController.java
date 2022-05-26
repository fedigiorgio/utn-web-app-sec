package org.utn.web.app.sec.utnwebappsec.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginRequest;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginResponse;
import org.utn.web.app.sec.utnwebappsec.dtos.UserDto;
import org.utn.web.app.sec.utnwebappsec.entities.InvalidUserPasswordException;
import org.utn.web.app.sec.utnwebappsec.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UsersController {

    private String CONNECTION_URL = "overrides_locally";

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll(HttpServletRequest request) {
        List<UserDto> users = getUsers("SELECT * FROM dbo.USERS").stream().map(u -> new UserDto(u)).collect(Collectors.toList());
        return users;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) throws InvalidUserPasswordException {
        String passwordMD5 = DigestUtils.md5Hex(request.getPassword());

        User authenticatedUser = getUsers("SELECT * FROM dbo.USERS WHERE USERNAME = '" + request.getUserName() + "' AND PASSWORD = '" + passwordMD5 + "'")
                .stream()
                .findFirst()
                .orElseThrow(() -> new InvalidUserPasswordException());

        authenticatedUser.login();

        saveUserToken(authenticatedUser);

        return new LoginResponse(authenticatedUser.getToken());
    }

    @RequestMapping(path = "/private", method = RequestMethod.GET)
    public UserDto getPrivate() {
        return new UserDto("fdigiorgio", "Francisco Di Giorgio", "fdigiorgio@frba.utn.edu.ar");
    }

    private List<User> getUsers(String query) {
        List<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(CONNECTION_URL);
             PreparedStatement ps = con.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getString("USERNAME"), rs.getString("FULLNAME"), rs.getString("MAIL"), rs.getString("PASSWORD"), rs.getString("TOKEN")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void saveUserToken(User user) {
        try {
            Connection con = DriverManager.getConnection(CONNECTION_URL);
            con.prepareStatement("UPDATE dbo.USERS SET TOKEN = '" + user.getToken() + "' WHERE USERNAME = '" + user.getUsername() + "'").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
