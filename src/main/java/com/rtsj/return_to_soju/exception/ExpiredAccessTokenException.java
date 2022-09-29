package com.rtsj.return_to_soju.exception;

public class ExpiredAccessTokenException extends RuntimeException{
    public ExpiredAccessTokenException() {
        super("access 토큰이 만료되었습니다");
    }

    public ExpiredAccessTokenException(String message) {
        super(message);
    }

    public ExpiredAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

