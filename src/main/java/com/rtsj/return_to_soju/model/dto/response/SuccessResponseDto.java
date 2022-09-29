package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.exception.ResponseResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "요청 성공 응답")
@Data
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessResponseDto{
    private final String message;
    public SuccessResponseDto(String message){
        this.message = message;
    }
}
