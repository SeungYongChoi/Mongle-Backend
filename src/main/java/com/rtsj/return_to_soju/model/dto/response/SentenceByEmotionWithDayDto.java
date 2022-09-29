package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.DailySentence;
import com.rtsj.return_to_soju.model.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SentenceByEmotionWithDayDto {

    @Schema(description = "문장 pk값")
    private Long id;

    @Schema(description = "추출된 문장")
    private String sentence;

    @Schema(description = "감정")
    private Emotion emotion;

    public SentenceByEmotionWithDayDto(DailySentence dailySentence) {
        this.id = dailySentence.getId();
        this.sentence = dailySentence.getSentence();
        this.emotion = dailySentence.getEmotion();
    }


}
