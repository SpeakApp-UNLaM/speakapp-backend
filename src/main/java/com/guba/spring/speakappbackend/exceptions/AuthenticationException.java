package com.guba.spring.speakappbackend.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable th) {
        super(message, th);
    }
}
