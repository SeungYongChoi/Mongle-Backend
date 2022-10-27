package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;

import java.util.List;

public interface WeekStatisticsRepositoryCustom {
    public List<WeekStatistics> findWeekStatisticsWithStartAndEndWeek(Long userId, String startWeek, String endWeek);

    public List<WeekStatistics> findWeekStatisticsWithYear(Long userId, String year);
}
