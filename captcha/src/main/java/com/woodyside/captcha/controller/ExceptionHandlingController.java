package com.woodyside.captcha.controller;

import com.woodyside.captcha.exception.NotFoundCaptchaIdException;
import com.woodyside.captcha.payload.response.RestApiResponse;
import com.woodyside.captcha.util.DateResponseFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = NotFoundCaptchaIdException.class)
    public ResponseEntity<RestApiResponse> noClientFoundFoundException(NotFoundCaptchaIdException ex, WebRequest request) {
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
}
