package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.exception.InvalidWeekException;
import com.rtsj.return_to_soju.model.dto.dto.EmotionCntWithDate;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByWeekDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.StatisticsResponseDto;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import com.rtsj.return_to_soju.repository.WeekStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final CalenderRepository calenderRepository;

    private final UserRepository userRepository;
    private final CalendarUtil calendarUtil;
    private final WeekStatisticsRepository weekStatisticsRepository;

    public EmotionScoreByWeekDto getWeekStatistics(Long userId, int year, int month, int week) {
        LocalDate[] startEndDate = calendarUtil.findWeekWithYearAndMonth(year, month, week);
        LocalDate startDate = startEndDate[0];
        LocalDate endDate = startEndDate[1];

        log.info(userId + "유저 " + startDate + "부터 " + endDate + "까지의 감정 점수 가져오기");
        List<EmotionCntWithDate> emotionCntWithDateList = calenderRepository.getEmotionStatisticsWithPeriod(userId, startDate, endDate);

        return new EmotionScoreByWeekDto(emotionCntWithDateList, startDate);

    }

    public StatisticsResponseDto getMonthStatistics(Long userId, Integer year, Integer month) {
        int firstWeekInMonth = calendarUtil.getFirstWeekInMonth(year, month);
        int lastWeekInMonth = calendarUtil.getLastWeekInMonth(year, month);
        String startWeek = year + "/" + firstWeekInMonth;
        String lastWeek = year + "/" + lastWeekInMonth;

        List<WeekStatistics> result = weekStatisticsRepository.findWeekStatisticsWithStartAndEndWeek(userId, startWeek, lastWeek);

        return StatisticsResponseDto.byMonth(result, year, month, firstWeekInMonth, lastWeekInMonth);
    }


    public StatisticsResponseDto getYearStatistics(Long userId, Integer year) {
        List<WeekStatistics> result = weekStatisticsRepository.findWeekStatisticsWithYear(userId, String.valueOf(year));

        return StatisticsResponseDto.byYear(result, year);

    }
}
