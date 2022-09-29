package com.rtsj.return_to_soju.common;

import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import com.rtsj.return_to_soju.service.FirebaseCloudMessageService;
import com.rtsj.return_to_soju.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleMessage {
    private final UserRepository userRepository;
    private final UserService userService;

    private final CalenderRepository calenderRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    @Scheduled(cron = "0 0 21 ? * WED")
    public void pushMemoryMessage(){
        log.info("메모리 메세지 보내기");
        List<String> allFcmToken = userRepository.findAllFcmToken();
        firebaseCloudMessageService.sendMessageListWithToken(allFcmToken, "몽글몽글", "행복한 기억이 도착했어요");
    }
}
