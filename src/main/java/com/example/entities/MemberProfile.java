package com.example.entities;

import com.example.constants.MemberLevel;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MemberProfile {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    // 열거형을 사용할 때는 필수. ORDINAL말고 STRING을 주로 씀
    private MemberLevel levelType;

    @Lob
    private String introduction;
}
