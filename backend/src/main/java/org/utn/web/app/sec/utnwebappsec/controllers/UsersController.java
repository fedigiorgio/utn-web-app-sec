package org.utn.web.app.sec.utnwebappsec.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginRequest;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginResponse;
import org.utn.web.app.sec.utnwebappsec.dtos.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {

    private String CONNECTION_URL = "overrides_locally";

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll(HttpServletRequest request) {

        List<UserDto> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(CONNECTION_URL);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM dbo.USERS")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new UserDto(rs.getString("USERNAME"), rs.getString("FULLNAME"), rs.getString("MAIL")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @RequestMapping(method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        if (request.getUserName() != "fdigiorgio" && request.getPassword() != "password123"){
            throw new Exception();
        }

        return new LoginResponse("fake-token");
    }

    @RequestMapping(path = "/private", method = RequestMethod.GET)
    public UserDto getPrivate() {
        return new UserDto("fdigiorgio", "Francisco Di Giorgio", "fdigiorgio@frba.utn.edu.ar");
    }
}
