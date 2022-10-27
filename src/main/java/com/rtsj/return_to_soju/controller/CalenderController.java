package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.common.SuccessResponseResult;
import com.rtsj.return_to_soju.model.dto.request.WriteDiaryDto;
import com.rtsj.return_to_soju.model.dto.response.CalenderBetweenMonthResponseDto;
import com.rtsj.return_to_soju.model.dto.response.CalenderByDayDto;
import com.rtsj.return_to_soju.model.dto.response.SentenceByEmotionWithDayDto;
import com.rtsj.return_to_soju.model.enums.Emotion;
import com.rtsj.return_to_soju.service.CalenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "Calender Controller", description = "Calender controller desc")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalenderController {

    // 만들까 말까~
//    @GetMapping("/calender/{calenderId}")
    private final CalenderService calenderService;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "월 별 캘린더 데이터 조회 api",
            description = "query parameter로 필요한 년도 및 월을 받아 해당 월의 감정 및 주제들을 반환한다.  ex) /api/calender/start=2022-3(03)&end=2022-5(05) : 3월부터 5월까지 데이터"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CalenderBetweenMonthResponseDto.class))))
    })
    @GetMapping("/calender")
    public ResponseEntity<List<CalenderBetweenMonthResponseDto>> getCalenderByMonth(
            HttpServletRequest request,
            @RequestParam(value = "start") String start, //?start=2022-01&end=2022-02
            @RequestParam(value = "end") String end) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        List<CalenderBetweenMonthResponseDto> emotionBetweenMonth = calenderService.getEmotionBetweenMonth(userId, start, end);
        return ResponseEntity.ok().body(emotionBetweenMonth);
    }



    @Operation(
            summary = "일 별 캘린더 데이터 조회 api",
            description = "path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받아 해당 일의 필요한 데이터를 조회한다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = CalenderByDayDto.class)))
            }
    )
    @GetMapping("/calender/{year}/{month}/{day}")
    public ResponseEntity<CalenderByDayDto> getCalenderByDay(
            HttpServletRequest request,
            @PathVariable(name = "year") String year,
            @PathVariable(name = "month") String month,
            @PathVariable(name = "day") String day
    ) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        CalenderByDayDto dailyDataFromUser = calenderService.getDailyDataFromUser(userId, year, month, day);
        return ResponseEntity.ok().body(dailyDataFromUser);
    }


    @Operation(
            summary = "일기 작성 or 수정 api (완료)",
            description = "path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받은 후 body로 일기 내용을 입력받아 일기를 작성한다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "등록 성공")
            }
    )
    @PostMapping("/calender/{year}/{month}/{day}/diary")
    public ResponseEntity<SuccessResponseResult> writeDiary(
            @PathVariable(name = "year") String year,
            @PathVariable(name = "month") String month,
            @PathVariable(name = "day") String day,
            @RequestBody WriteDiaryDto dto,
            HttpServletRequest request
    ) throws IOException, ParseException {
        Long userId = jwtProvider.getUserIdByHeader(request);
        String diaryText = dto.getText();
        calenderService.createDiary(userId, year, month, day, diaryText);
        String uri = "/calender/" + year + "/" + month + "/" + day + "/diary";
        return ResponseEntity
                .created(URI.create(uri))
                .body(new SuccessResponseResult("일기 등록 완료"));
    }

    @Operation(
            summary = "일자 및 감정별 대화 문장 불러오는 api (완료)",
            description = "특정 일의 분석화면에서 감정이를 눌렀을 때 해당하는 대화 문장들을 불러온다."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "요청 성공 // url = 원본 파일 url",
                    content = @Content(schema = @Schema(implementation = SentenceByEmotionWithDayDto.class)))
            }
    )
    @GetMapping("/calender/{year}/{month}/{day}/sentences")
    public ResponseEntity<List> getEmotionSentence(
            @PathVariable(name = "year") String year,
            @PathVariable(name = "month") String month,
            @PathVariable(name = "day") String day,
            @RequestParam("emotion") Emotion emotion,
            HttpServletRequest request
    ) {
        Long userId = jwtProvider.getUserIdByHeader(request);
        List<SentenceByEmotionWithDayDto> sentenceByEmotionWithDay = calenderService.getSentenceByEmotionWithDay(userId, year, month, day, emotion);

        return ResponseEntity.ok()
                .body(sentenceByEmotionWithDay);
    }


    @PatchMapping("/calender/{year}/{month}/{day}/emotion")
    public ResponseEntity<SuccessResponseResult> changeEmotionByUserAndDate(
            @PathVariable(name = "year") String year,
            @PathVariable(name = "month") String month,
            @PathVariable(name = "day") String day,
            @RequestParam("value") Emotion emotion,
            HttpServletRequest request
    ){
        Long userId = jwtProvider.getUserIdByHeader(request);
        calenderService.changeEmotionByUserAndDate(userId, year, month, day, emotion);
        return ResponseEntity.ok()
                .body(new SuccessResponseResult("변경 완료!"));
    }
}
