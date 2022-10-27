package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.common.SuccessResponseResult;
import com.rtsj.return_to_soju.model.dto.response.SuccessResponseDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByWeekDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByYearDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.StatisticsResponseDto;
import com.rtsj.return_to_soju.model.entity.WeekStatistics.WeekStatistics;
import com.rtsj.return_to_soju.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name="Statistics Controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "일년 기준 감정 점수 가져오기",
            description = "현재를 기준으로 1년전까지의 감정 점수를 월별로 조회하는 api"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(schema = @Schema(implementation = EmotionScoreByYearDto.class)))
    })
    @GetMapping("/statistics/year")
    public ResponseEntity<SuccessResponseResult> getYearStatistics(HttpServletRequest request,
                                  @NotNull @RequestParam("year") Integer year) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        StatisticsResponseDto result = statisticsService.getYearStatistics(userId, year);

        return ResponseEntity.ok(new SuccessResponseResult(result));
    }

    @Operation(
            summary = "월 기준 감정 점수 가져오기",
            description = "현재를 기준으로 1주 간격으로 총 4주 간의 감정 점수를 조회하는 api"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(schema = @Schema(implementation = StatisticsResponseDto.class)))
    })
    @GetMapping("/statistics/month")
    public ResponseEntity<SuccessResponseResult> getMonthStatistics(HttpServletRequest request,
                                   @NotNull @RequestParam("year") Integer year,
                                   @NotNull @RequestParam("month") Integer month) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        StatisticsResponseDto result = statisticsService.getMonthStatistics(userId, year, month);

        return ResponseEntity.ok(new SuccessResponseResult(result));
    }


    @Operation(
            summary = "주 기준 감정 점수 가져오기",
            description = "현재를 기준으로 1일 간격으로 총 7일 간의 감정 점수를 조회하는 api"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(schema = @Schema(implementation = EmotionScoreByWeekDto.class)))
    })
    @GetMapping("/statistics/week")
    public ResponseEntity<SuccessResponseResult> getWeekStatistics(HttpServletRequest request,
                                                                   @NotNull @RequestParam("year") Integer year,
                                                                   @NotNull @RequestParam("month") Integer month,
                                                                   @NotNull @RequestParam("week") Integer week) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        log.info(userId + "유저 주 기준 감정 통계 api 호출");
        EmotionScoreByWeekDto result = statisticsService.getWeekStatistics(userId, year, month, week);
        log.info(userId + "유저 주 기준 감정 통계 api 응답");
        return ResponseEntity.ok(new SuccessResponseResult(result));
    }


}
