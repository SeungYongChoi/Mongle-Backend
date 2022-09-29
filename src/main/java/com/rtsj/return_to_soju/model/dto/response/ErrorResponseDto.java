package com.rtsj.return_to_soju.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "잘못된 접근")
@Data
@AllArgsConstructor
public class ErrorResponseDto {
    @Schema(description = "응답메세지", example = "잘못된 접근입니다.")
    private String errorMessage;
    @Schema(description = "응답번호", example = "400")
    private String errorCode;
}
