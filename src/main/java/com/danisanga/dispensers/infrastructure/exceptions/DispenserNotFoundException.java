package com.danisanga.dispensers.infrastructure.exceptions;

public class DispenserNotFoundException extends DispenserWebServiceException{
    public DispenserNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}
