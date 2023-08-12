package com.example;

import com.example.entities.BoardData;
import com.example.entities.Member;
import com.example.repositories.BoardDataRepository;
import com.example.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class BoardDataTest {


    @Autowired
    private  BoardDataRepository boardDataRepository;

    @Autowired
    private  MemberRepository memberRepository;

    @Test
    @DisplayName("다대일, 일대다 매핑 테스트")
    public void manyToOneAndOneToMany() {

        /* 회원 추가 S */
        Member member = new Member();
        member.setUserId("user1");
        member.setUserNm("사용자1");
        member.setUserPw("123456");
        member.setEmail("user01@test.org");
        member.setMobile("010-0000-0000");
        member.setRegDt(LocalDateTime.now());
        member.setModDt(LocalDateTime.now());
        Member savedMember = memberRepository.save(member);
        System.out.println(savedMember);
        /* 회원 추가 E */

        /* 게시글 추가 S */
        for(int i=1; i<=5; i++) {
            BoardData boardData = new BoardData();
            boardData.setSubject("제목" + i);
            boardData.setContents("내용" + i);
            boardData.setViewCounts(100+i);
            boardData.setMember(member);
            boardData.setRegDt(LocalDateTime.now());
            boardData.setModDt(LocalDateTime.now());

            boardDataRepository.save(boardData);
        }
        /* 게시글 추가 E */

        /* 게시글 조회 S */
        BoardData boardData = boardDataRepository.findById(Long.valueOf(2)).get();
        // 2번 데이터 조회
        System.out.println(boardData);
        System.out.println(member.getUserId());
        /* 게시글 조회 E */

        /* 일대다 매핑 테스트 S */
        Member mem = memberRepository.findById(member.getId()).get();
        System.out.println(mem.getId() + ", " + mem.getId());
        System.out.println(mem.getBoardData());
        /* 일대다 매핑 테스트 E */
    }
}
