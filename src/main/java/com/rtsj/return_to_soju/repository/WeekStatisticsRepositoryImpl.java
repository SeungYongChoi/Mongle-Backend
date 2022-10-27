package com.rtsj.return_to_soju.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.QWeekStatistics;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.rtsj.return_to_soju.model.entity.WeekStatistics.QWeekStatistics.weekStatistics;


public class WeekStatisticsRepositoryImpl implements WeekStatisticsRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public WeekStatisticsRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<WeekStatistics> findWeekStatisticsWithStartAndEndWeek(Long userId, String startWeek, String endWeek) {
        return queryFactory.selectFrom(weekStatistics)
                .where(weekStatistics.id.user.id.eq(userId)
                        .and(weekStatistics.id.yearWeek.between(startWeek, endWeek)))
                .orderBy(weekStatistics.id.yearWeek.asc())
                .fetch();
    }

    @Override
    public List<WeekStatistics> findWeekStatisticsWithYear(Long userId, String year) {

        return queryFactory.selectFrom(weekStatistics)
                .where(weekStatistics.id.user.id.eq(userId)
                        .and(weekStatistics.id.yearWeek.contains(year)))
                .orderBy(weekStatistics.id.yearWeek.asc())
                .fetch();
    }

}
