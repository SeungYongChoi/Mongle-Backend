package com.rtsj.return_to_soju.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTopic extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "topic_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    @Column(name = "topic_name")
    private String name;
    private String roomName;
    public DailyTopic(Calender calender, String topic, String roomName){
        this.calender = calender;
        this.name = topic;
        this.roomName = roomName;
        calender.addTopic(this);
    }

}
