package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.rtsj.return_to_soju.model.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmotionsWithCountDto {

    @Schema(description = "감정 종류", example = "HAPPY")
    private Emotion emotion;
    @Schema(description = "갯수", example = "520")
    private Integer count;
}
