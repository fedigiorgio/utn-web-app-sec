package org.utn.web.app.sec.utnwebappsec.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidUserPasswordException extends Exception {
}
