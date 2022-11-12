package com.rtsj.return_to_soju.model.dto.dto;

import lombok.Data;

@Data
public class FcmTypeAndData {
    private String title;
    private String body;
    private String type;
    private String data;

    /**
     * 메시지 모드. 데이터 없이 메시지만 보내는 경우 사용한다.
     *
     * @param title 제목
     * @param body  내용
     */
    public FcmTypeAndData(String title, String body) {
        this.title = title;
        this.body = body;
    }

    /**
     * 데이터 모드. 메시지와 함께 데이터를 보내는 경우 사용한다.
     *
     * @param title 제목
     * @param body  내용
     * @param type  데이터 타입 (error | analyze | gift)
     * @param data  데이터 payload
     */
    public FcmTypeAndData(String title, String body, String type, String data) {
        this.title = title;
        this.body = body;
        this.type = type;
        this.data = data;
    }
}
