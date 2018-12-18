package com.findme.exception;

public class InternalServerError extends Exception{

    private final Integer code = 500;
    private final String description = "Internal server error";

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
