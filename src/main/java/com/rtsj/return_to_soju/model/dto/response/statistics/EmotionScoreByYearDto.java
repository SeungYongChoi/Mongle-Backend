package com.rtsj.return_to_soju.model.dto.response.statistics;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmotionScoreByYearDto {
    List<EmotionsScoreWithMonthDto> scoreList = new ArrayList<>();
    List<EmotionsWithCountDto> emotionsList = new ArrayList<>();

}
