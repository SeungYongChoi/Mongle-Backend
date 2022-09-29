package com.rtsj.return_to_soju.controller;

import com.rtsj.return_to_soju.model.dto.response.statistics.CalenderIdWithDateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Memory Controller", description = "Memory controller desc")
@RestController
@RequestMapping("/api")
public class MemoryController {

    @Operation(
            summary = "행복한 기억 1개 random 으로 가져오는 api",
            description = "행복한 기억 1개 random 으로 가져오는 api"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공",
             content = @Content(schema = @Schema(implementation = CalenderIdWithDateDto.class)))
    })

    @GetMapping("/memory/new")
    public void getRandomMemory() {

    }

}
