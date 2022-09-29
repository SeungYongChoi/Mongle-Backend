package com.rtsj.return_to_soju.common;

import lombok.Data;

@Data
public class SuccessResponseResult {
    private String message;
    private Object data;
    private final boolean isSuccess = true;

    public SuccessResponseResult(String message) {
        this.message = message;
    }

    public SuccessResponseResult(Object data) {
        this.data = data;
    }

    public SuccessResponseResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
