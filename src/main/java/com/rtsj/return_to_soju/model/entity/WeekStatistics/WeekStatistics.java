package com.rtsj.return_to_soju.model.entity.WeekStatistics;

import com.rtsj.return_to_soju.model.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WeekStatistics {

    @EmbeddedId
    private WeekStatisticsId id;

    private Integer happy;
    private Integer neutral;
    private Integer angry;
    private Integer anxious;
    private Integer tired;
    private Integer sad;

    private Float score;

    public int getWeek(){
        String yearWeek = id.getYearWeek();
        return Integer.parseInt(yearWeek.split("/")[1]);
    }

}
