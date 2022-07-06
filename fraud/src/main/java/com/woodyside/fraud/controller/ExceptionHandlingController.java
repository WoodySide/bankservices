package com.woodyside.fraud.controller;


import com.woodyside.fraud.exception.ClientIsFraudException;
import com.woodyside.fraud.payload.response.RestApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.woodyside.fraud.util.DateResponseFormatter.getTimestamp;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = ClientIsFraudException.class)
    public ResponseEntity<RestApiResponse> clientIsFraudException(ClientIsFraudException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .date(getTimestamp())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
