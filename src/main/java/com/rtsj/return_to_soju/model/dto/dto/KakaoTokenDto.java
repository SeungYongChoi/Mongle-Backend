package com.rtsj.return_to_soju.model.dto.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoTokenDto {
    private String accessToken;
    private String refreshToken;
}