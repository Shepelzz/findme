package com.findme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException extends Exception{
    private final Integer code = 400;
    private final String description = "Bad request exception";

    public BadRequestException(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
