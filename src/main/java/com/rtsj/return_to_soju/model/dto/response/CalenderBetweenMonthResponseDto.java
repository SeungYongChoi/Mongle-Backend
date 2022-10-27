package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.DailyTopic;
import com.rtsj.return_to_soju.model.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "Month Page Response")
@Data
@AllArgsConstructor
public class CalenderBetweenMonthResponseDto {
    @Schema(example = "2020-01-01")
    private LocalDate date;
    @Schema(example = "감정")
    private Emotion emotion;
    private List<String> subjectList;

    public CalenderBetweenMonthResponseDto(Calender calender) {
        this.date = calender.getDate();
        this.emotion = calender.getEmotion();
        this.subjectList = calender.getTopicList().stream().limit(5).map(DailyTopic::getName).collect(Collectors.toList());
    }
}
