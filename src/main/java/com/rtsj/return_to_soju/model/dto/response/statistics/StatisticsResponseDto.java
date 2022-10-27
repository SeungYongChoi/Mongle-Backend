package com.rtsj.return_to_soju.model.dto.response.statistics;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Data
@NoArgsConstructor
public class StatisticsResponseDto {
    Float[] scoreList;
    String startDate;
    private int happy = 0;
    private int neutral = 0;
    private int angry = 0;
    private int anxious = 0;
    private int tired = 0;
    private int sad = 0;

    public static StatisticsResponseDto byMonth(List<WeekStatistics> result, int year, int month, int startWeek, int lastWeek) {
        CalendarUtil calendarUtil = new CalendarUtil();

        StatisticsResponseDto dto = new StatisticsResponseDto();

        int totalWeek = lastWeek - startWeek + 1;
        dto.scoreList = new Float[totalWeek];

        dto.startDate = calendarUtil.getFirstDateWithYearMonth(year, month).toString();

        for (WeekStatistics weekStatistics : result) {
            int yearWeek = weekStatistics.getWeek();
            int monthWeek = calendarUtil.getMonthWeekWithYearWeek(year,yearWeek);

            dto.scoreList[monthWeek-1] = weekStatistics.getScore();

            dto.addEmotionCtn(weekStatistics);
        }
        return dto;
    }

    private void addEmotionCtn(WeekStatistics weekStatistics) {
        happy += weekStatistics.getHappy();
        neutral += weekStatistics.getNeutral();
        angry += weekStatistics.getAngry();
        anxious += weekStatistics.getAnxious();
        tired += weekStatistics.getTired();
        sad += weekStatistics.getSad();
    }

    public static StatisticsResponseDto byYear(List<WeekStatistics> result, int year) {
        CalendarUtil calendarUtil = new CalendarUtil();
        StatisticsResponseDto dto = new StatisticsResponseDto();
        dto.scoreList = new Float[12];

        dto.startDate = calendarUtil.getFirstDateWithYearMonth(year, 1).toString();

        Queue<WeekStatistics> queue = new LinkedList<>();
        queue.addAll(result);

        for (int i = 1; i < 12; i++) {
            int lastWeekInMonth = calendarUtil.getLastWeekInMonth(year, i);
            float score = 0;
            int scoreCnt = 0;
            while (!queue.isEmpty() && queue.peek().getWeek() <= lastWeekInMonth) {
                WeekStatistics each = queue.poll();
                if (each.getScore() == null)
                    continue;
                score += each.getScore();
                scoreCnt++;

                dto.addEmotionCtn(each);
            }

            if (scoreCnt != 0) {
                dto.scoreList[i - 1] = score / scoreCnt;
            }

        }

        return dto;
    }


}
