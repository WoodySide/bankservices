package com.woodyside.transaction.exception;

public class NoTransactionPossibleException extends RuntimeException {

    private static final String ERROR_MESSAGE = "You can not commit any transactions yet! Check if you process fraud check";

    public NoTransactionPossibleException() {
        super(ERROR_MESSAGE);
    }
}
