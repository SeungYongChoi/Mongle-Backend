package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmotionScoreByWeekDto {

    List<EmotionScoreWithDayDto> scoreList = new ArrayList<>();
    //    List<EmotionsWithCountDto> emotionsList = new ArrayList<>();
    String startDate;

    private int happy = 0;
    private int neutral = 0;
    private int angry = 0;
    private int anxious = 0;
    private int tired = 0;
    private int sad = 0;


    public EmotionScoreByWeekDto(List<EmotionCntWithDate> emotionCntWithDateList, LocalDate startDate) {
        this.startDate = startDate.toString();
        for (EmotionCntWithDate each : emotionCntWithDateList) {
            this.happy += each.getHappy();
            this.neutral += each.getNeutral();
            this.angry += each.getAngry();
            this.anxious += each.getAnxious();
            this.tired += each.getTired();
            this.sad += each.getSad();

            EmotionScoreWithDayDto emotionScoreWithDayDto = new EmotionScoreWithDayDto(each);
            if (emotionScoreWithDayDto.getScore() != null) {
                scoreList.add(emotionScoreWithDayDto);
            }
        }

    }
}
