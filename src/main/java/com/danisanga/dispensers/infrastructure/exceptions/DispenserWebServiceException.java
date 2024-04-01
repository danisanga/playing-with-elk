package com.danisanga.dispensers.infrastructure.exceptions;

public class DispenserWebServiceException extends RuntimeException {
    public DispenserWebServiceException(final String errorMessage) {
        super(errorMessage);
    }
}
