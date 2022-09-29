package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.dto.EmotionWithPercentDto;
import com.rtsj.return_to_soju.model.dto.ImageDto;
import com.rtsj.return_to_soju.model.dto.ScheduleDto;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CalenderByDayDto {

    private List<ImageDto> imageList = new ArrayList<>();
    private String diary;
    private String diaryFeedback;
    private List<ScheduleDto> scheduleList = new ArrayList<>();
    private List<EmotionWithPercentDto> emotionList = new ArrayList<>();

    private Emotion emotion;
    public CalenderByDayDto(Calender calender){
        this.imageList = calender.getImageList().stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
        this.diary = calender.getDiary();
        this.diaryFeedback = calender.getDiaryFeedback();
        this.scheduleList = calender.getScheduleList().stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
        for(int i=0; i<Emotion.values().length; i++){
            this.emotionList.add(new EmotionWithPercentDto(Emotion.values()[i], (int) (calender.getEmotionPercent(i)*100)));
        }
        this.emotion = calender.getEmotion();
    }
}
