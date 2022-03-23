package com.alkemy.disney.exceptions;

import com.alkemy.disney.util.JsonResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<JsonResponseError> procesarExcepcionBadRequest(BadRequestException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<JsonResponseError> procesarExcepcionResourceNotFound(ResourceNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JsonResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }
}