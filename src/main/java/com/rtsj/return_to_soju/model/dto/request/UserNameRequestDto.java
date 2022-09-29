package com.rtsj.return_to_soju.model.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserNameRequestDto {
    @NotBlank(message = "이름을 등록해주세요")
    private String userName;
}
