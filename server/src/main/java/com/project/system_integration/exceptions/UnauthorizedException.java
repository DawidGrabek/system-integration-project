package com.project.system_integration.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
