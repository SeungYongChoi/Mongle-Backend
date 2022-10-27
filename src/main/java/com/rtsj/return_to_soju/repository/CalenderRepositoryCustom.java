package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface CalenderRepositoryCustom {
    List<EmotionCntWithDate> getEmotionStatisticsWithPeriod(long userId, LocalDate startDate, LocalDate endDate);

    Calender findCalenderByUserIdAndLocalDate(Long userId, LocalDate date);
}
