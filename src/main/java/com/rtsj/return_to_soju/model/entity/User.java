package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.enums.Role;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RTUSER")
@Getter
@DynamicUpdate //상속 받는다고 어노테이션까지 상속은 안되더라...
public class User extends BaseEntity implements Persistable<Long> {
    @Id
    @Column(name = "user_id")
    private Long id; //katalk PK 값으로 매핑하기
    private String mongleName;
    private String kakaoName;
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private String cloudEmail;
    private String calenderEmail;
    @Column(name = "fcm_token")
    private String fcmToken;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "kakao_update")
    private LocalDateTime kakaoUpdate;
    @OneToMany(mappedBy = "user")
    private List<Calender> calenderList = new ArrayList<>();

    public User(Long id, String nickName, KakaoTokenDto kakaoTokenDto,  Role role){
        this.id = id;
        this.kakaoName = nickName;
        this.kakaoAccessToken = kakaoTokenDto.getAccessToken();
        this.kakaoRefreshToken = kakaoTokenDto.getRefreshToken();
        this.role = role;
    }
    public void setKakaoUpdate(){
        this.kakaoUpdate = LocalDateTime.now();
    }
    public void setFcmToken(String fcmToken){this.fcmToken = fcmToken;}

    public void updateKakaoName(String nickName){
        this.kakaoName = nickName;
    }
    public void updateMongleName(String userName){this.mongleName = userName;}

    public void updateKakaoToken(KakaoTokenDto kakaoTokenDto) {
        this.kakaoAccessToken = kakaoTokenDto.getAccessToken();
        this.kakaoRefreshToken = kakaoTokenDto.getRefreshToken();
    }

    @Override
    public boolean isNew() {
        return getCreateDate() == null;
    }


    public void addCalender(Calender calender) {
        this.calenderList.add(calender);
    }
}