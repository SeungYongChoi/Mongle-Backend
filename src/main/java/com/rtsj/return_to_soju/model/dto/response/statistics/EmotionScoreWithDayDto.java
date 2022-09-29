package com.rtsj.return_to_soju.model.dto.response.statistics;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionScoreWithDayDto {

    private Float score;
    private LocalDate date;

}
