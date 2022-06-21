package com.woodyside.captcha.exception;

public class NotFoundCaptchaIdException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Captcha with this ID not found!";

    public NotFoundCaptchaIdException() {
        super(ERROR_MESSAGE);
    }
}
