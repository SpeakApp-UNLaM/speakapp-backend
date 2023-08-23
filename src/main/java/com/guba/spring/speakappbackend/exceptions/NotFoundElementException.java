package com.guba.spring.speakappbackend.exceptions;

public class NotFoundElementException extends RuntimeException {

    public NotFoundElementException(String message) {
        super(message);
    }

    public NotFoundElementException(String message, Throwable th) {
        super(message, th);
    }
}
