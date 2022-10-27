package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalenderRepository extends JpaRepository<Calender, Long>, CalenderRepositoryCustom {
    Optional<Calender> findByUserAndDate(User user, LocalDate date);
    List<Calender> findALLByUserAndDateBetween(User user, LocalDate start, LocalDate end);
    void deleteAllByDate(LocalDate date);

    @Modifying
    @Query(value = "" +
            "update calender c " +
            "set c.angry = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('ANGRY')), " +
            "c.anxious = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('ANXIOUS')), " +
            "c.happy = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('HAPPY')), " +
            "c.neutral = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('NEUTRAL')), " +
            "c.sad = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('SAD')), " +
            "c.tired = " +
            "(select count(*) from daily_sentence d where d.calender_id=c.calender_id and d.emotion in('TIRED')) " +
            "where c.calender_id in " +
            "(select id from " +
            "(select c2.calender_id as id " +
            "from rtuser left join calender c2 on rtuser.user_id = c2.user_id " +
            "where rtuser.user_id=:userId) as t)",
            nativeQuery = true)
    int saveCalenderEmotionCntByNatvieQuery(@Param("userId") Long userId);

    @Modifying
    @Query(value =
            "update calender c1 " +
                    "set c1.emotion = " +
                    "case " +
                        "(select * from (select greatest(c2.angry, c2.anxious, c2.happy, c2.neutral, c2.sad, c2.tired) from calender c2 where c2.calender_id = c1.calender_id) as a) " +
                    "when 0 then null " +
                    "when c1.happy then 'HAPPY' " +
                    "when c1.neutral then 'NEUTRAL' " +
                    "when c1.anxious then 'ANXIOUS' " +
                    "when c1.angry then 'ANGRY' " +
                    "when c1.sad then 'SAD' " +
                    "when c1.tired then 'TIRED' " +
                    "end " +
                    "where c1.calender_id in " +
                    "(select * from (select c3.calender_id from calender c3 join rtuser r on c3.user_id = r.user_id where r.user_id =:userId) as a)" +
                    "and c1.emotion_update=false",
            nativeQuery = true)
    int saveCalenderMainEmotionByNativeQuery(@Param("userId") Long userId);

    @Modifying
    @Query(value =
            "insert into week_statistics " +
                    "(year_week, happy, neutral, angry, anxious, sad, tired, score, user_id) " +
                    "select week, happy, neutral, angry, anxious, sad, tired, score, user_id " +
                    "from " +
                    "(SELECT DATE_FORMAT(date, '%x/%v') AS `week`," +
                    "sum(happy) as 'happy'," +
                    "sum(neutral) as 'neutral'," +
                    "sum(angry) as 'angry'," +
                    "sum(anxious) as 'anxious'," +
                    "sum(sad) as 'sad', " +
                    "sum(tired) as 'tired', " +
                    "getScore(sum(happy),sum(neutral),sum(angry),sum(anxious),sum(sad),sum(tired)) as score, " +
                    ":userId as 'user_id' " +
                    "FROM calender where user_id = :userId GROUP BY week order by week) sub_query " +
                    "on duplicate key update " +
                    "happy=sub_query.happy, " +
                    "neutral = sub_query.neutral, " +
                    "angry = sub_query.angry, " +
                    "anxious = sub_query.anxious, " +
                    "sad = sub_query.sad, " +
                    "tired = sub_query.tired, " +
                    "score = sub_query.score;",
            nativeQuery= true
    )
    int saveWeekStatisticsByNativeQuery(@Param("userId") Long userId);


}
