package com.advertisement.advertisementsystem.exception;

public class TokenValidationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Token is not valid";

    public TokenValidationException() {
        super(DEFAULT_MESSAGE);
    }
}
