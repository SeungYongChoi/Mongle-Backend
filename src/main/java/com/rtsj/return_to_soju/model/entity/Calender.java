package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.dto.EmotionWithPercentDto;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.security.cert.TrustAnchor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Calender extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "calender_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    private Boolean emotionUpdate = false;
    @Lob
    private String diary;
    private String diaryFeedback;
    @OneToMany(mappedBy = "calender")
    private List<Schedule> scheduleList = new ArrayList<>();
    @OneToMany(mappedBy = "calender", cascade = CascadeType.ALL)
    private List<DailySentence> dailySentenceList = new ArrayList<>();
    @OneToMany(mappedBy = "calender", cascade = CascadeType.ALL)
    private List<DailyTopic> topicList = new ArrayList<>();

    public List<DailyTopic> getTopicList() {
        Collections.shuffle(topicList);
        return topicList;
    }

    @OneToMany(mappedBy = "calender")
    private List<ImageFile> imageList = new ArrayList<>();
    private Integer happy = 0;
    private Integer neutral = 0;
    private Integer angry = 0;
    private Integer anxious = 0;
    private Integer tired = 0;
    private Integer sad = 0;

    public Calender(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        user.addCalender(this);
    }

    /**
     * emotion_update : true -> 유저나 일기가 터칭. default : false
     * @param i
     * @return
     */
    public double getEmotionPercent(int i){
        int sum = happy + neutral + angry + anxious + tired + sad;
        if(this.emotion == null || sum == 0) return 0;
        Emotion value = Emotion.values()[i];
        if(Emotion.HAPPY == value) return happy.doubleValue()/sum;
        else if(Emotion.NEUTRAL == value)return neutral.doubleValue()/sum;
        else if(Emotion.SAD == value)return sad.doubleValue()/sum;
        else if(Emotion.ANXIOUS == value)return anxious.doubleValue()/sum;
        else if(Emotion.ANGRY == value)return angry.doubleValue()/sum;
        else if(Emotion.TIRED == value)return tired.doubleValue()/sum;
        else throw new IllegalArgumentException("잘못된 입력입니다.");
    }

    public void updateEmotion(Emotion emotion) {
        this.emotion = emotion;
        this.emotionUpdate = Boolean.TRUE;
    }

    public void writeOrUpdateDiary(String diary) {
        this.diary = diary;
    }
    public void writeOrUpdateDiaryFeedback(String diaryFeedback) {
        this.diaryFeedback = diaryFeedback;
    }
    public void addTopic(DailyTopic dailyTopic) {
        this.topicList.add(dailyTopic);
    }
}
