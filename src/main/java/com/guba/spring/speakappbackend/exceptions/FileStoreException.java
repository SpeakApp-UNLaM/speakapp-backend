package com.guba.spring.speakappbackend.exceptions;

public class FileStoreException extends RuntimeException {

    public FileStoreException(String message) {
        super(message);
    }

    public FileStoreException(String message, Throwable th) {
        super(message, th);
    }

}
