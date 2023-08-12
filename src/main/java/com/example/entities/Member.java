package com.example.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member { // 엔티티는 기본키가 반드시 있어야함

    @Id @GeneratedValue
    private Long id; // 회원번호

    @Column(nullable = false, length = 30)
    private String userId;

    @Column(nullable = false, length = 30)
    private String userNm;

    @Column(nullable = false, length = 60)
    private String userPw;

    private String email;

    @Column(length = 13)
    private String mobile;

    @OneToMany(mappedBy = "member")
    // 연관관계의 주인 = BoardData
    // Member 엔티티가 주인이 아니므로 mappedBy를 사용
    // 속성값이 member인 이유는 BoardData의 Member에 의해 관리되기 때문
    private List<BoardData> boardData = new ArrayList<>();

    @OneToOne
    private MemberProfile memberProfile;


    private LocalDateTime regDt;

    private LocalDateTime modDt;
}
