package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.KakaoRoom;
import com.rtsj.return_to_soju.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface KakaoRoomRepository extends JpaRepository<KakaoRoom, Long> {
    Optional<KakaoRoom> findByUserAndRoomName(User user, String roomName);

}
