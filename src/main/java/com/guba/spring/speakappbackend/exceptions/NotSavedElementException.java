package com.guba.spring.speakappbackend.exceptions;

public class NotSavedElementException extends RuntimeException {

    public NotSavedElementException(String message) {
        super(message);
    }

    public NotSavedElementException(String message, Throwable th) {
        super(message, th);
    }
}
