package com.rtsj.return_to_soju.exception;

public class NotFoundCalenderException extends RuntimeException{
    public NotFoundCalenderException() {
        super("해당 요일의 데이터(캘린더)를 찾을 수 없습니다.");
    }

    public NotFoundCalenderException(String message) {
        super(message);
    }

    public NotFoundCalenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
