package com.mdghub.project.exceptions;

public class APIException extends RuntimeException {
    private String message;
    public APIException(String message) {
        super(message);
    }

}
