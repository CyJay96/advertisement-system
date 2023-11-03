package com.advertisement.advertisementsystem.exception;

public class NoPermissionsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "There are no permissions";

    public NoPermissionsException() {
        super(DEFAULT_MESSAGE);
    }
}