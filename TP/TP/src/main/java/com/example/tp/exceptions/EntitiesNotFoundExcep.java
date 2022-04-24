package com.example.tp.exceptions;

public class EntitiesNotFoundExcep extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntitiesNotFoundExcep(String id, String message) {
        super(message + id);
    }

    public EntitiesNotFoundExcep(long numCelebrite, String message) {
        super(message + numCelebrite);
    }

}
