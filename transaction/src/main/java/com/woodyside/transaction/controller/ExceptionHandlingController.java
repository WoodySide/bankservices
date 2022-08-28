package com.woodyside.transaction.controller;

import com.woodyside.transaction.exception.NoTransactionPossibleException;
import com.woodyside.transaction.payload.response.RestApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.woodyside.transaction.util.DateResponseFormatter.getTimestamp;


@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = NoTransactionPossibleException.class)
    public ResponseEntity<RestApiResponse> noClientFoundFoundException(NoTransactionPossibleException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .date(getTimestamp())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
