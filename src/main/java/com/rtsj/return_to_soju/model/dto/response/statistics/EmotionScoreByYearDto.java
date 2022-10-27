package com.rtsj.return_to_soju.model.dto.response.statistics;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmotionScoreByYearDto {
    List<EmotionsScoreWithMonthDto> scoreList = new ArrayList<>();
    private int happy = 0;
    private int neutral = 0;
    private int angry = 0;
    private int anxious = 0;
    private int tired = 0;
    private int sad = 0;

}
