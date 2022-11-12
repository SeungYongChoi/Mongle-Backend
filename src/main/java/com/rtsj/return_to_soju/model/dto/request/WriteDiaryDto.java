package com.rtsj.return_to_soju.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class WriteDiaryDto {

    @Schema(description = "일기", example = "오늘은 날씨가 참 밝다. ^^")
    private String text;

    @Schema(description = "암호", example = "32byte의 암호")
    @Nullable
    private String key;
}
