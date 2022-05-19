package com.example.mySpringBootProject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //It checks the errors that may occur across the application
    public class UsersssException extends Exception {

        @ResponseStatus(HttpStatus.BAD_REQUEST) //It returns the status of the response
        @ExceptionHandler(MethodArgumentNotValidException.class) //Handles exceptions
        public Map<String, String> handleValidationExceptions(
                MethodArgumentNotValidException ex) { //Exception to be thrown when validation on argument @Valid fails
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }

    }

