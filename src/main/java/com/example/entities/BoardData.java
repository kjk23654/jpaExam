package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "boardData", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileInfo> fileInfos = new ArrayList<>(); // 부모는 BoardData

    private LocalDateTime regDt; // 등록시간
    private LocalDateTime modDt; // 수정시간
}
