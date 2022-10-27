package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatisticsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeekStatisticsRepository extends JpaRepository<WeekStatistics, WeekStatisticsId>, WeekStatisticsRepositoryCustom {
}
