package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.response.SentenceByEmotionWithDayDto;
import com.rtsj.return_to_soju.model.enums.Emotion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BaseController {
    @GetMapping("/")
    public ResponseEntity healthCheck(
    ) {
        return ResponseEntity.ok().body("healthCheck");
    }
}
