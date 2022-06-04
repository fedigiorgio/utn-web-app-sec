package org.utn.web.app.sec.utnwebappsec.controllers;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginRequest;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginResponse;
import org.utn.web.app.sec.utnwebappsec.dtos.UserDto;
import org.utn.web.app.sec.utnwebappsec.entities.NotAuthorizedException;
import org.utn.web.app.sec.utnwebappsec.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private String CONNECTION_URL = "overrides_locally";

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll(@RequestParam String fullName, @RequestHeader("token") String token) throws NotAuthorizedException {

       checkUserAuthenticated(token);

        List<UserDto> users = getUsers("SELECT * FROM dbo.USERS WHERE FULLNAME LIKE '%" + fullName + "%'")
                .stream()
                .map(u -> new UserDto(u))
                .collect(Collectors.toList());

        return users;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) throws NotAuthorizedException {
        String passwordMD5 = DigestUtils.md5Hex(request.getPassword());

        User authenticatedUser = getUsers("SELECT * FROM dbo.USERS WHERE USERNAME = '" + request.getUserName() + "' AND PASSWORD = '" + passwordMD5 + "'")
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotAuthorizedException());

        authenticatedUser.login();

        saveUserToken(authenticatedUser);

        System.out.println(request.getUserName());
        System.out.println(authenticatedUser.getToken());

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

    private void checkUserAuthenticated(String token) throws NotAuthorizedException {
        if(getUsers("SELECT * FROM dbo.USERS WHERE TOKEN = '" + token + "'").stream().count() != 1){
            throw new NotAuthorizedException();
        }
    }

}
