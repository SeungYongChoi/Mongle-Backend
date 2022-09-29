package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.entity.Schedule;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {

    private String name;

    private String calendar;

    private String startTime;

    private String endTime;

    public ScheduleDto(Schedule schedule){
        this.name = schedule.getName();
        this.calendar = schedule.getDomain();
        this.startTime = schedule.getStartTime().toString();
        this.endTime = schedule.getEndTime().toString();
    }

}
