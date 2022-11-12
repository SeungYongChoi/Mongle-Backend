package com.rtsj.return_to_soju.model.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private String token; //특정 디바이스 보내는 경우
        private FcmTypeAndData data;
    }
}
