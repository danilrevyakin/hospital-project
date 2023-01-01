package com.example.hospitalproject.security.config;

import com.example.hospitalproject.security.exception.InvalidPasswordException;
import com.example.hospitalproject.security.exception.NotFoundException;
import com.example.hospitalproject.security.exception.UserExistsException;
import com.example.hospitalproject.security.exception.UsersByRoleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionHandlerAspect extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserExistsException(UserExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UsersByRoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsersByRoleNotFoundException(UserExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidPasswordException(UserExistsException e) {
        return e.getMessage();
    }
}
