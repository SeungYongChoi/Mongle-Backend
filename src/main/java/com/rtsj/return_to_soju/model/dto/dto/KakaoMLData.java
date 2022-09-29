package com.rtsj.return_to_soju.model.dto.dto;

import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class KakaoMLData {
    private String date_time;
    private String text;
    private Emotion emotion;
}
