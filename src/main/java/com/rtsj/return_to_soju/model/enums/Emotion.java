package com.rtsj.return_to_soju.model.enums;

import org.springframework.context.annotation.Bean;

public enum Emotion {

    HAPPY("행복"), NEUTRAL("중립"), ANGRY("분노"), ANXIOUS("불안"), TIRED("피곤"), SAD("슬픔"),
    ;
    //    행복(기쁨) / 슬픔 / 당황 ->피곤 / 불안 / 중립 / 분노 / + 후회  희망
//
//    행복(기쁨)희망 / 슬픔+ 후회  / 당황 ->피곤 / 불안 / 중립 / 분노 /
    // 슬픔, 피곤, 후회 -> 우울 <> 비우울
    private String korean;

    Emotion(String korean) {
        this.korean = korean;
    }
}
