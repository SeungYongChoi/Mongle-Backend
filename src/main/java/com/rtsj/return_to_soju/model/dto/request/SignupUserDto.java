package com.rtsj.return_to_soju.model.dto.request;

import lombok.Data;

@Data
public class SignupUserDto {

    private String accessToken;
    private String refreshToken;

}
