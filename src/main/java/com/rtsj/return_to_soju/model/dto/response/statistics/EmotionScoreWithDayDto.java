package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmotionScoreWithDayDto {

    private Float score;
    private LocalDate date;

    @JsonIgnore
    private final int happyScore = 5;
    @JsonIgnore
    private final int neutralScore = 3;
    @JsonIgnore
    private final int angryScore = 1;
    @JsonIgnore
    private final int anxiousScore = 2;
    @JsonIgnore
    private final int tiredScore = 2;
    @JsonIgnore
    private final int sadScore = 0;

    /**
     * 행복 100
     * 분노 0
     * 피곤 0
     * (500) / 100 * 100 / 5
     * @param emotionCntWithDate
     */
    public EmotionScoreWithDayDto(EmotionCntWithDate emotionCntWithDate) {
        float totalScore = 0F;
        float totalCnt = 0F;

        totalScore +=
                (emotionCntWithDate.getHappy() * happyScore) +
                        (emotionCntWithDate.getNeutral() * neutralScore) +
                        (emotionCntWithDate.getAngry() * angryScore) +
                        (emotionCntWithDate.getAnxious() * anxiousScore) +
                        (emotionCntWithDate.getTired() * tiredScore) +
                        (emotionCntWithDate.getSad() * sadScore);
        totalCnt +=
                emotionCntWithDate.getHappy() +
                        emotionCntWithDate.getNeutral() +
                        emotionCntWithDate.getAngry() +
                        emotionCntWithDate.getAnxious() +
                        emotionCntWithDate.getTired() +
                        emotionCntWithDate.getSad();

        this.score = totalScore / totalCnt * 100 / 5;
        this.date = emotionCntWithDate.getDate();

    }
}
