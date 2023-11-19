package com.project.system_integration.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
