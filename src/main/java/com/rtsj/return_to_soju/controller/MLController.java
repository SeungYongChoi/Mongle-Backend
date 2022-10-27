
package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.common.SuccessResponseResult;
import com.rtsj.return_to_soju.model.dto.request.KakaoMLDataSaveRequestDto;
import com.rtsj.return_to_soju.model.dto.request.KakaoMLErrorRequestDto;
import com.rtsj.return_to_soju.model.dto.response.UserInfoResponseDto;
import com.rtsj.return_to_soju.service.FirebaseCloudMessageService;
import com.rtsj.return_to_soju.service.MLService;
import com.rtsj.return_to_soju.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "ML Controller", description = "ML서버로부터 데이터 받아오는 api 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MLController {

    private final MLService mlService;
    private final UserService userService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    @Operation(summary = "ML 분석 후 카톡 데이터 받아오기", description = "ML서버가 카톡 데이터 처리를 마친 후 해당 api를 호출하여 데이터를 저장한다.")
    @PostMapping("/ml/kakao")
    public ResponseEntity<SuccessResponseResult> saveKakaoMLData(@RequestBody KakaoMLDataSaveRequestDto dto) throws IOException {
        mlService.saveKakaoMLData(dto);
        String userFcmToken = userService.getUserFcmToken(dto.getUser_pk());
        String start_date = dto.getStart_date();
        String end_date = dto.getEnd_date();
        firebaseCloudMessageService.sendAnalyzeMessageTo(userFcmToken, "몽글몽글", "분석이 완료됐어요~!", start_date + "-" + end_date);
        return ResponseEntity.ok()
                .body(new SuccessResponseResult("성공"));
    }


    @Operation(summary = "ML 에러 처리", description = "ML서버에서 Error를 반환할 경우 유저에게 push알림을 보낸다.")
    @PostMapping("/ml/error")
    public ResponseEntity<SuccessResponseResult> saveKakaoMLError(@RequestBody KakaoMLErrorRequestDto dto) throws IOException {
        String userFcmToken = userService.getUserFcmToken(dto.getUser_pk());
        firebaseCloudMessageService.sendErrorMessageTo(userFcmToken,"몽글몽글", "파일 업로드에 실패했어요:(");
        return ResponseEntity.ok()
                .body(new SuccessResponseResult("Error 메세지 전송"));
    }
}
