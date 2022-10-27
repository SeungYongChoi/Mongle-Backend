package com.rtsj.return_to_soju.model.dto.response.statistics;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionScoreWithWeekDto {

    private Float score;

    private int week;

    public EmotionScoreWithWeekDto(int week) {
        this.week = week;
    }
}
