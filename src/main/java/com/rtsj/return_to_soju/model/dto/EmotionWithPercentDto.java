package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmotionWithPercentDto {
    private Emotion emotion;
    private int percent;

}
