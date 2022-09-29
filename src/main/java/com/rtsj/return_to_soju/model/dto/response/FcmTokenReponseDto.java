package com.rtsj.return_to_soju.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Schema(description = "FCM 토큰 등록")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenReponseDto {
    @Schema(description = "FCM Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    @NotBlank(message = "토큰 등록 필수")
    private String fcmToken;
}
