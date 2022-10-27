package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.KakaoRoom;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.KakaoRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoRoomService {
    private final KakaoRoomRepository kakaoRoomRepository;

    /**
     *
     * @param user
     * @param roomName
     * @param endDate : YYYYMMDD
     */
    @Transactional
    public void saveOrUpdateKakaoRoom(User user, String roomName, String endDate){
        Optional<KakaoRoom> optionalKakaoRoom = kakaoRoomRepository.findByUserAndRoomName(user, roomName);
        if(optionalKakaoRoom.isEmpty()){
            KakaoRoom kakaoRoom = new KakaoRoom(user, roomName, endDate);
            kakaoRoomRepository.save(kakaoRoom);
            return;
        }
        KakaoRoom kakaoRoom = optionalKakaoRoom.get();
        kakaoRoom.updateLastDateTime(endDate);
    }

}
