package com.rtsj.return_to_soju.exception;

public class InvalidWeekException extends RuntimeException {
    public InvalidWeekException() {
    }

    public InvalidWeekException(String message) {
        super(message);
    }

    public InvalidWeekException(String message, Throwable cause) {
        super(message, cause);
    }
}
