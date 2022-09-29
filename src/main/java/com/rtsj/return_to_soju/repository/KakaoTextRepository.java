package com.rtsj.return_to_soju.repository;

import com.rtsj.return_to_soju.model.entity.KakaoText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface KakaoTextRepository extends JpaRepository<KakaoText, Long> {

}
