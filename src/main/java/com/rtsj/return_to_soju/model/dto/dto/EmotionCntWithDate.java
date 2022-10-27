package com.rtsj.return_to_soju.model.dto.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionCntWithDate {
    private LocalDate date;

    private Integer happy;
    private Integer neutral;
    private Integer angry;
    private Integer anxious;
    private Integer tired;
    private Integer sad;

}
