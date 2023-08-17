package com.example.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 공통 속성화 엔티티에 넣어줘야할 애노테이션
@Data
public abstract class BaseTimeEntity { // 공통 속성 엔티티는 추상 클래스

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modDt;
}
