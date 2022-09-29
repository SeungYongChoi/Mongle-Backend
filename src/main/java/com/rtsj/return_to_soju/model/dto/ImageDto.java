package com.rtsj.return_to_soju.model.dto;

import com.rtsj.return_to_soju.model.entity.ImageFile;
import lombok.Data;

@Data
public class ImageDto {
    private String url;
    private String time;

    public ImageDto(ImageFile imageFile){
        this.url = imageFile.getUrl();
        this.time = imageFile.getTime().toString();
    }
}
