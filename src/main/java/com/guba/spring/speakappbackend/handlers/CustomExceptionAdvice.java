package com.guba.spring.speakappbackend.handlers;

import com.guba.spring.speakappbackend.exceptions.NotFoundElementException;
import com.guba.spring.speakappbackend.exceptions.NotSavedElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Aca se van a definir los flujos no previstos.
 * Ejem, en algun punto del codigo hay una exception no gestionada, para este caso
 * la api devolvera un codigo de error determinado y un msj concreto.
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errorsMessage = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        var responseBody = new ErrorApiResponse(status.value(), LocalDateTime.now(), errorsMessage);

        return new ResponseEntity<>(responseBody, headers, status);
    }

    @ExceptionHandler(NotFoundElementException.class)
    public ResponseEntity<Object> handleNotFoundElementException(NotFoundElementException e) {
        log.error("error NotFoundElementException", e);
        List<String> errorsMessage = List.of(e.getMessage());

        var responseBody = new ErrorApiResponse(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), errorsMessage);
        return new ResponseEntity<>(responseBody, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotSavedElementException.class)
    public ResponseEntity<Object> handleNotSavedElementException(NotSavedElementException e) {
        log.error("error NotSavedElementException", e);
        List<String> errorsMessage = List.of(e.getMessage());

        var responseBody = new ErrorApiResponse(HttpStatus.CONFLICT.value(), LocalDateTime.now(), errorsMessage);
        return new ResponseEntity<>(responseBody, null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleMaxSizeException(Exception e) {
        log.error("error", e);
        List<String> errorsMessage = List.of(e.getMessage());

        var responseBody = new ErrorApiResponse(HttpStatus.CONFLICT.value(), LocalDateTime.now(), errorsMessage);
        return new ResponseEntity<>(responseBody, null, HttpStatus.CONFLICT);
    }

}
