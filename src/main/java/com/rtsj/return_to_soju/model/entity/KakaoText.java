package com.rtsj.return_to_soju.model.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoText extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "kakao_id")
    private Long id;

    //s3
    @Column(name = "kakao_url")
    private String url;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;
    //OneToMany -> DailySentence 생략

    public KakaoText(String url, User user) {
        this.url = url;
        this.user = user;
    }
}
