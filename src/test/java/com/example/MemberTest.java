package com.example;

import com.example.entities.Member;
import com.example.repositories.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    public void auditingTest() {
        Member member = new Member();
        member.setUserId("user2");
        member.setUserNm("사용자2");
        member.setUserPw("123456");
        memberRepository.save(member);

        em.flush();
        em.close();

        Member member1 = memberRepository.findById(member.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("regDt : " + member1.getRegDt());
        System.out.println("modDt : " + member1.getModDt());
        System.out.println("createBy : " + member1.getCreateBy());
        System.out.println("modifiedBy : " + member1.getModifiedBy());
    }
}
