package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.enums.ImageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "image_file_id")
    private Long id;

    @Column(name = "image_url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_id")
    private Calender calender;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType type;

    @Column(name = "image_time")
    private LocalDateTime time;

    // 위도
    private Double latitude;

    // 경도
    private Double longitude;



}
