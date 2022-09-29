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
    @Schema(example = "a;sdlfkaelsifjasldfjase;ife")
    private String accessToken;
    @Schema(example = "asdlkjvalkefj;lkasvl;awneof")
    private String refreshToken;
}
