package com.rtsj.return_to_soju.exception;

public class NoDataUploadedException extends RuntimeException{
    public NoDataUploadedException() {
        super("데이터가 업데이트 되어 있지 않습니다.");
    }

    public NoDataUploadedException(String message) {
        super(message);
    }

    public NoDataUploadedException(String message, Throwable cause) {
        super(message, cause);
    }
}
