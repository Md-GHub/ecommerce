package com.mdghub.project.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException
            (MethodArgumentNotValidException e){

        Map<String,String> response = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error)->{
            String name = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(name,message);
        });

        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e){
        String message = "Category Name should be atleast length of 7";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ResourceNotFound.class})
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFound e){
        String message = e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {APIException.class})
    public ResponseEntity<String> handleAPIException(APIException e){
        String message = e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
}
