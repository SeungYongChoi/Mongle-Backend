package com.rtsj.return_to_soju.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(description = "토큰 재발급 응답")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReissueTokenResponseDto {
    @Schema(description = "Mongle Access Token", example = "aldksfja;esoifjaslkdfaeafsdvsaer")
    private String accessToken;
    @Schema(description = "Mongle Refresh Token", example = "a;dlksfjaewovnaslkdwfeawvscvewaew")
    private String refreshToken;
    @Schema(description = "토큰타입", defaultValue = "Bearer", example = "Bearer")
    private String tokenType;
    @Schema(description = "Mongle Access ExpiredAt", example = "2022-09-03T10:15:30")
    private String accessExpiredAt;
    @Schema(description = "Mongle Refresh ExpiredAt", example = "2022-09-03T10:15:30")
    private String refreshExpiredAt;


}
