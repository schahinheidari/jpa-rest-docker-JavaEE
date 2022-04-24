package com.example.tp.exceptions;

public class EntitiesNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntitiesNotFoundException(String id, String message) {
        super(message + id);
    }

    public EntitiesNotFoundException(long numCelebrite, String message) {
        super(message + numCelebrite);
    }

}
