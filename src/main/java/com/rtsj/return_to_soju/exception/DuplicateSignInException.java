package com.rtsj.return_to_soju.exception;

public class DuplicateSignInException extends RuntimeException{
    public DuplicateSignInException() {
        super("이미 존재하는 회원입니다.");
    }

    public DuplicateSignInException(String message) {
        super(message);
    }

    public DuplicateSignInException(String message, Throwable cause) {
        super(message, cause);
    }
}
