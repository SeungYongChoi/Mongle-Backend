package com.rtsj.return_to_soju.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException() {
        super("존재하지 않는 유저입니다.");
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
