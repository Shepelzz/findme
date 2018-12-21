package com.findme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class InternalServerError extends Exception{

    private final Integer code = 500;
    private final String description = "Internal Server Error";

    public InternalServerError(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerError(String message) {
        super(message);
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
