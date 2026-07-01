package com.example.habitforgeapi.exception;

public class DuplicateCompletionException extends RuntimeException {
    public DuplicateCompletionException(String message) {
        super(message);
    }
}
