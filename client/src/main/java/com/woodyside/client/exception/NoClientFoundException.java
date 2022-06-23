package com.woodyside.client.exception;

public class NoClientFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "No client found!";

    public NoClientFoundException() {
        super(ERROR_MESSAGE);
    }
}
