package com.ordersystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            OrderNotFoundException.class)
    public ResponseEntity<?> handleNotFound(

            OrderNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "message",
                        ex.getMessage(),

                        "timestamp",
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(
            InvalidOrderStateException.class)
    public ResponseEntity<?> handleInvalid(

            InvalidOrderStateException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "message",
                        ex.getMessage(),

                        "timestamp",
                        LocalDateTime.now()
                ));
    }
}