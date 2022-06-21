package com.woodyside.client.exception;

public class NoClientsFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "No clients found!";

    public NoClientsFoundException() {
        super(ERROR_MESSAGE);
    }
}
