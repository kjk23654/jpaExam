package com.example;

import com.example.constants.MemberLevel;
import com.example.entities.Member;
import com.example.entities.MemberProfile;
import com.example.repositories.MemberProfileRepository;
import com.example.repositories.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @Test
    @DisplayName("회원 추가 테스트")
    public void createMember() {
        Member member = new Member();
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setLevelType(MemberLevel.MEMBER);
        MemberProfile savedProfile = memberProfileRepository.save(memberProfile);

        member.setUserId("user1");
        member.setUserNm("사용자1");
        member.setUserPw("123456");
        member.setEmail("user1@test.org");
        member.setMobile("0100000000");
        member.setMemberProfile(savedProfile);
        member.setRegDt(LocalDateTime.now());

        Member savedMember = memberRepository.save(member);

        System.out.println("확인용1" + savedMember.getMemberProfile().getLevelType());


        savedProfile.setLevelType(MemberLevel.ADMIN);
        System.out.println("확인용2" + savedMember.getMemberProfile().getLevelType());

        memberRepository.flush();

        Member mem = memberRepository.findByUserId("user1");
        System.out.println("확인용3" + mem.getMemberProfile().getLevelType());
    }
}
