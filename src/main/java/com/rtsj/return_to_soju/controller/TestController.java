package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.response.SuccessResponseDto;
import com.rtsj.return_to_soju.repository.UserRepository;
import com.rtsj.return_to_soju.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {
    private final UserRepository userRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("/gift")
    public ResponseEntity<SuccessResponseDto> gift(){
        List<String> allFcmToken = userRepository.findAllFcmToken();
        System.out.println(allFcmToken);
        firebaseCloudMessageService.sendMessageListWithToken(allFcmToken, "몽글몽글", "행복한 기억이 도착했어요");
        return ResponseEntity.ok().body(new SuccessResponseDto("성공"));
    }
}
