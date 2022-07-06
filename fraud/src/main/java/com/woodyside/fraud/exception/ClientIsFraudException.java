package com.woodyside.fraud.exception;

public class ClientIsFraudException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Registered  suspicious behavior. No access!";

    public ClientIsFraudException() {
        super(ERROR_MESSAGE);
    }
}
