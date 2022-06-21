package com.woodyside.client.controller;

import com.woodyside.client.exception.ClientIsFraudException;
import com.woodyside.client.exception.NoClientsFoundException;
import com.woodyside.client.payload.response.RestApiResponse;
import com.woodyside.client.util.DateResponseFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = NoClientsFoundException.class)
    public ResponseEntity<RestApiResponse> noClientFoundFoundException(NoClientsFoundException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .date(DateResponseFormatter.getTimestamp())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ClientIsFraudException.class)
    public ResponseEntity<RestApiResponse> clientIsFraudException(ClientIsFraudException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .date(DateResponseFormatter.getTimestamp())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
