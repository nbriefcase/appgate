package com.neirodiaz.appgate.exception;

public class BacklogNotFoundException extends RuntimeException {

    public BacklogNotFoundException() {
    }

    public BacklogNotFoundException(String message) {
        super(message);
    }
}
