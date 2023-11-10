package com.advertisement.advertisementsystem.exception;

public class TokenExpirationException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Token was expired";

    public TokenExpirationException() {
        super(DEFAULT_MESSAGE);
    }
}
