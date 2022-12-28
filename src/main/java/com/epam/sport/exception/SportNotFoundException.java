package com.epam.sport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SportNotFoundException extends RuntimeException {
    public SportNotFoundException(String sportName) {
        super(String.format("Sport with name %s has not found", sportName));
    }
}
