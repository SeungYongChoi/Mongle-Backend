package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailySentence extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "sentence_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    //    @Lob //너무 큼
    @Column(columnDefinition = "TEXT") // 65535문자
    private String sentence;
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_id")
    private KakaoText kakaoText;

    public DailySentence(Calender calender, String sentence, Emotion emotion, String roomName) {
        this.calender = calender;
        this.sentence = sentence;
        this.emotion = emotion;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return removeExtension(roomName);
    }

    private String removeExtension(String s) {
        int lastDotIdx = s.lastIndexOf(".");
        return s.substring(0, lastDotIdx);
    }
}
