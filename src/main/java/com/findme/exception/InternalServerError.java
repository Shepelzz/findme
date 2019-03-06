package com.findme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class InternalServerError extends RuntimeException{

    public InternalServerError(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerError(String message) {
        super(message);
    }

    public Integer getCode() {
        return 500;
    }

    public String getDescription() {
        return "Internal Server Error";
    }
}
