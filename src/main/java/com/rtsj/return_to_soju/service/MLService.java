package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.dto.KakaoMLData;
import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.dto.request.KakaoMLDataSaveRequestDto;
import com.rtsj.return_to_soju.model.entity.*;
import com.rtsj.return_to_soju.model.enums.Emotion;
import com.rtsj.return_to_soju.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class MLService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;
    private final DailySentenceRepository dailySentenceRepository;
    private final CalenderService calenderService;
    private final DailyTopicRepository dailyTopicRepository;
    private final KakaoRoomService kakaoRoomService;
    private final CalendarUtil calendarUtil;

    public void saveKakaoMLData(KakaoMLDataSaveRequestDto dto) {
        Long userId = dto.getUser_pk();
        List<KakaoMLData> data = dto.getKakao_data();
        Map<String, List<String>> keyword = dto.getKeyword();
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        saveDailySentence(user, data, dto.getRoomName());
        saveDailyTopic(user, keyword, dto.getRoomName());
        log.info("ML서버로 부터 받아온 데이터를 저장합니다.");
        kakaoRoomService.saveOrUpdateKakaoRoom(user, dto.getRoomName(), dto.getEnd_date());
        calenderRepository.saveCalenderEmotionCntByNatvieQuery(userId);
        calenderRepository.saveCalenderMainEmotionByNativeQuery(userId);
        calenderRepository.saveWeekStatisticsByNativeQuery(userId);
    }


    // 매번 calender를 찾는 쿼리문이 나감,,, 수정할 방법을 찾고싶은데 모르겠다..
    private void saveDailySentence(User user, List<KakaoMLData> datas, String roomName) {
        datas.stream()
                .forEach(data -> {
                    String time = data.getDate_time();
                    //todo Roomname, kakaoText 넣어줘야함
                    // 1. roomname 구현이 가능할진 모르겠음
                    // 2. kakaoText: 본문 파일을 알아야 당일 카톡보기를 구현햘 수 있음
                    // but 현재 이걸 어떻게 구현할지 논의해야
                    // 3. 사용자가 톡방을 한번 더 올렸을 때 중복검사 로직이 없음
                    LocalDate localDate = calendarUtil.convertKoreanStringWithDayToLocalDate(time);
                    Calender calender = calenderService.findCalenderByUserAndLocalDate(user, localDate);
                    String sentence = data.getText();
                    Emotion emotion = data.getEmotion();
                    DailySentence dailySentence = new DailySentence(calender, sentence, emotion, roomName);
                    dailySentenceRepository.save(dailySentence);
                });
    }
    private void saveDailyTopic(User user, Map<String, List<String>> keyword, String roomName){
        keyword.forEach((key, value) -> {
            LocalDate localDate = calendarUtil.convertKoreanStringToLocalDate(key);
            Calender calender = calenderService.findCalenderByUserAndLocalDate(user, localDate);
            value.forEach(topic -> {
                DailyTopic dailyTopic = new DailyTopic(calender, topic, roomName);
                dailyTopicRepository.save(dailyTopic);
            });
        });
    }


}
