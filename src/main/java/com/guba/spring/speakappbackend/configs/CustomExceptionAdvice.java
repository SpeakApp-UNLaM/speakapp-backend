package com.guba.spring.speakappbackend.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
* Aca se van a definir los flujos no previstos.
* Ejem, en algun punto del codigo hay una exception no gestionada, para este caso
* la api devolvera un codigo de error determinado y un msj concreto.
*/
@ControllerAdvice
@Slf4j
public class CustomExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleMaxSizeException(Exception e) {
    log.error("error", e);
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body("ERROR EL SERVER " + e.getMessage());
  }
}