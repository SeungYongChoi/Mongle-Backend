package com.rtsj.return_to_soju.model.enums;

public enum ImageType {
    FOOD("음식"), BACKGROUND("배경"), HUMAN("사람");

    private String korean;

    ImageType(String korean) {
        this.korean = korean;
    }
}
