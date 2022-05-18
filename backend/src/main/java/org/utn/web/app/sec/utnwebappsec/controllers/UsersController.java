package org.utn.web.app.sec.utnwebappsec.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginRequest;
import org.utn.web.app.sec.utnwebappsec.dtos.LoginResponse;
import org.utn.web.app.sec.utnwebappsec.dtos.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAll(HttpServletRequest request) {
        List<UserDto> response = new ArrayList<>();
        response.add(new UserDto("fdigiorgio", "Francisco Di Giorgio", "fdigiorgio@frba.utn.edu.ar"));
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return new LoginResponse("harcode-token");
    }

    @RequestMapping(path = "/private", method = RequestMethod.GET)
    public UserDto getPrivate() {
        return new UserDto("fdigiorgio", "Francisco Di Giorgio", "fdigiorgio@frba.utn.edu.ar");
    }
}
