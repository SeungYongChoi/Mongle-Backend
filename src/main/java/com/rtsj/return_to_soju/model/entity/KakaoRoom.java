package com.rtsj.return_to_soju.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicUpdate //상속 받는다고 어노테이션까지 상속은 안되더라...
@AllArgsConstructor
public class KakaoRoom extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String roomName;

    private String lastDateTime;

    public KakaoRoom(User user, String roomName, String lastDateTime){
        this.user = user;
        this.roomName = roomName;
        this.lastDateTime = lastDateTime;
    }

    public void updateLastDateTime(String lastDateTime){
        this.lastDateTime = lastDateTime;
    }
}
