package com.rtsj.return_to_soju.model.entity;

import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass//table로 사용하지 않을 것이기에, 추상화
@EntityListeners(AuditingEntityListener.class)
@Getter
@DynamicUpdate
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updateDate;
}
