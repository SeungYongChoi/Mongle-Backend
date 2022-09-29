package com.rtsj.return_to_soju.model.dto.response.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "1주 단위로 총 4주의 감정 점수와 총 감정 갯수를 나타내는 dto")
@Data
public class EmotionScoreByMonthDto {

    List<EmotionScoreWithWeekDto> scoreList = new ArrayList<>();
    List<EmotionsWithCountDto> emotionsList = new ArrayList<>();
}
