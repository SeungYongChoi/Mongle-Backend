package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "SELECT rtuser.fcm_token FROM rtuser", nativeQuery = true)
    List<String> findAllFcmToken();


    //토큰 -> 유저를 찾고 -> 유저의 데일리 감정 중 행복 -> 그날의 날짜를 반환
    @Query(value = "select calender.date from rtuser, calender " +
            "where rtuser.user_id = calender.user_id " +
            "and rtuser.fcm_token = :token " +
            "and calender.emotion ='HAPPY' " +
            "and calender.happy != 0 " +
            "order by Rand() LIMIT 1;", nativeQuery = true)
    LocalDate findMemoryByFcmToken(@Param("token")String token);

    void deleteById(Long userId);
}
