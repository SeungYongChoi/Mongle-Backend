package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.DailyTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyTopicRepository extends JpaRepository<DailyTopic, Long> {
}
