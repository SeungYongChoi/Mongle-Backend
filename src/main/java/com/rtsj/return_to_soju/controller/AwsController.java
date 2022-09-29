package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.common.SuccessResponseResult;
import com.rtsj.return_to_soju.model.dto.request.UploadKakaoTextDto;
import com.rtsj.return_to_soju.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

@Tag(name = "Aws Controller", description = "Aws controller desc")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AwsController {

    private final S3Service s3Service;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "카카오톡 텍스트 보내기",
            description = "카카오톡 텍스트를 보내면 백엔드 단에서 s3 스토리지에 올린다."
    )
    @ApiResponses(value =
            @ApiResponse(responseCode = "201", description = "요청 성공",
            content = @Content(schema = @Schema(implementation = List.class)))
    )
    @PostMapping("/s3/kakao")
    public ResponseEntity uploadKakaoText(@ModelAttribute UploadKakaoTextDto dto, HttpServletRequest request) {
        log.info("카카오톡 txt 업로드 컨트롤러 시작");
        Long userId = jwtProvider.getUserIdByHeader(request);
        s3Service.uploadKakaoFile(dto.getFiles(), userId);

        return ResponseEntity.created(URI.create("/api/s3/kakao/"))
                .body(new SuccessResponseResult("file upload success"));
    }

}
