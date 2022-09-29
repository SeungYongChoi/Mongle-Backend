package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByMonthDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByWeekDto;
import com.rtsj.return_to_soju.model.dto.response.statistics.EmotionScoreByYearDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Statistics Controller")
@RestController
@RequestMapping("/api")
public class StatisticsController {

    @Operation(
            summary = "일년 기준 감정 점수 가져오기",
            description = "현재를 기준으로 1년전까지의 감정 점수를 월별로 조회하는 api"
    )
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(schema = @Schema(implementation = EmotionScoreByYearDto.class)))
            })
    @GetMapping("/statistics/year")
    public void getYearStatistics() {

    }

    @Operation(
            summary = "월 기준 감정 점수 가져오기",
            description = "현재를 기준으로 1주 간격으로 총 4주 간의 감정 점수를 조회하는 api"
    )
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(schema = @Schema(implementation = EmotionScoreByMonthDto.class)))
            })
    @GetMapping("/statistics/month")
    public void getMonthStatistics() {

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
    public void getWeekStatistics() {

    }


//    @GetMapping("/statistics/{year}/{month}/{week}")
//    public void getWeekStatistics(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("week") String week) {
//
//    }
}
