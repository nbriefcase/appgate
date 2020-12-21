package com.neirodiaz.appgate.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
    }

    public SessionNotFoundException(String message) {
        super(message);
    }
}
