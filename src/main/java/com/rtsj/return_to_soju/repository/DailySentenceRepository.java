package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.DailySentence;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailySentenceRepository extends JpaRepository<DailySentence, Long> {
    List<DailySentence> findByCalenderAndEmotion(Calender calender, Emotion emotion);
    void deleteAllByCalenderAndRoomName(Calender calender, String roomName);
}
