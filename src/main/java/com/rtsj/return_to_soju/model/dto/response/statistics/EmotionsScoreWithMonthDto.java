package com.rtsj.return_to_soju.model.dto.response.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmotionsScoreWithMonthDto {
    @Schema(description = "감정 점수", example = "54.28")
    private Float score;
    @Schema(description = "월", example = "2022-05")
    private String month;
}
