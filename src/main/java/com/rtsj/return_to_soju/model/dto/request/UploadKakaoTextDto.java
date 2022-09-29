package com.rtsj.return_to_soju.model.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class UploadKakaoTextDto {
    private List<MultipartFile> files = new ArrayList<>();
}
