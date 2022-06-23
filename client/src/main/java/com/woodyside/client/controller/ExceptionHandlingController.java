package com.woodyside.client.controller;

import com.woodyside.client.exception.ClientIsFraudException;
import com.woodyside.client.exception.EmailInUseException;
import com.woodyside.client.exception.NoClientFoundException;
import com.woodyside.client.payload.response.RestApiResponse;
import com.woodyside.client.util.DateResponseFormatter;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.woodyside.client.util.DateResponseFormatter.getTimestamp;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = NoClientFoundException.class)
    public ResponseEntity<RestApiResponse> noClientFoundFoundException(NoClientFoundException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .date(getTimestamp())
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
                .date(getTimestamp())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = EmailInUseException.class)
    public ResponseEntity<RestApiResponse> clientsEmailIsInUseException(EmailInUseException ex, WebRequest request) {
        RestApiResponse errorResponse = RestApiResponse
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .date(getTimestamp())
                .description(request.getDescription(false))
                .success(false)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
