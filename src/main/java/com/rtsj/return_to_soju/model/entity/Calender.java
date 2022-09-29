package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.dto.EmotionWithPercentDto;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Lob
    private String diary;
    private String diaryFeedback;
    @OneToMany(mappedBy = "calender")
    private List<Schedule> scheduleList = new ArrayList<>();
    @OneToMany(mappedBy = "calender")
    private List<DailySentence> dailySentenceList = new ArrayList<>();
    @OneToMany(mappedBy = "calender")
    private List<DailyTopic> topicList = new ArrayList<>();
    @OneToMany(mappedBy = "calender")
    private List<ImageFile> imageList = new ArrayList<>();
    private Integer happy;
    private Integer neutral;
    private Integer angry;
    private Integer anxious;
    private Integer tired;
    private Integer sad;

    public Calender(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        user.addCalender(this);
    }
    public double getEmotionPercent(int i){
        if(this.emotion == null) return 0;
        Emotion value = Emotion.values()[i];
        if(Emotion.HAPPY == value) return happy.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else if(Emotion.NEUTRAL == value)return neutral.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else if(Emotion.SAD == value)return sad.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else if(Emotion.ANXIOUS == value)return anxious.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else if(Emotion.ANGRY == value)return angry.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else if(Emotion.TIRED == value)return tired.doubleValue()/(happy+neutral+angry+anxious+tired+sad);
        else throw new IllegalArgumentException("잘못된 입력입니다.");
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
