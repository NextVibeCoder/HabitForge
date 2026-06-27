package com.example.habitforgeapi.exception;

public class UnauthorizedHabitAccessException extends RuntimeException {
    public UnauthorizedHabitAccessException(String message) {
        super(message);
    }
}
