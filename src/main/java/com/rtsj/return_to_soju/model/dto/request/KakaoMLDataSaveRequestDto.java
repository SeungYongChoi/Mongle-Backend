package com.rtsj.return_to_soju.model.dto.request;

import com.rtsj.return_to_soju.model.dto.dto.KakaoMLData;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Data
public class KakaoMLDataSaveRequestDto {
    private Long user_pk;
    private List<KakaoMLData> kakao_data;
    private String start_date;
    private String end_date;
    private Map<String, List<String>> keyword;

}

