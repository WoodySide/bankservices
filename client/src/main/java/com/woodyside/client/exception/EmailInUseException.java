package com.woodyside.client.exception;

public class EmailInUseException extends RuntimeException {

    private static final String ERROR_MESSAGE = "This email is in use, try to put another one!";

    public EmailInUseException() {
        super(ERROR_MESSAGE);
    }
}
