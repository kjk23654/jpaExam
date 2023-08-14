package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FileInfo { // 파일
    // 하나의 게시글에는 여러개의 파일이 첨부될 수 있음!
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BoardData boardData;
    // @JoinColumn을 사용하지 않으면 외래키명은 BOARD_DATA_ID;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String mineType;
    private boolean isDone;

    private LocalDateTime regDt;

    private LocalDateTime modDt;
}
