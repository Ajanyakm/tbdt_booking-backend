package com.tbdt.booking.exception;

public class InsufficientCapacityException extends RuntimeException {

    public InsufficientCapacityException(String message) {
        super(message);
    }
}
