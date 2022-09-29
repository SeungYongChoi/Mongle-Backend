package com.rtsj.return_to_soju.model.dto.response;

import com.rtsj.return_to_soju.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "유저 정보 반환")
@Data
public class UserInfoResponseDto {
    @Schema(example = "몽글 닉네임")
    private String name;
    @Schema(example = "카톡 닉네임")
    private String nickName;

    public UserInfoResponseDto(User user){
        this.name = user.getMongleName();
        this.nickName = user.getKakaoName();
    }
}
