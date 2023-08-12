package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_data")
@Data
public class BoardData {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 게시글 번호
    @Column(nullable = false, length = 100)
    private String subject; // 제목
    @Lob
    @Column(nullable = false)
    private String contents; // 내용

    @Column(columnDefinition = "NUMBER(4) NOT NULL")
    private int viewCounts; // 조회수

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime regDt; // 등록시간
    private LocalDateTime modDt; // 수정시간
}
