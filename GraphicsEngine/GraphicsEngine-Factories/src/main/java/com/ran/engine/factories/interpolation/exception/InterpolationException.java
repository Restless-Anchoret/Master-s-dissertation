package com.ran.engine.factories.interpolation.exception;

public class InterpolationException extends RuntimeException {

    public InterpolationException(String message) {
        super(message);
    }

    public InterpolationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}