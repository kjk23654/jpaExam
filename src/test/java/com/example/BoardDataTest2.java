package com.example;

import com.example.entities.BoardData;
import com.example.entities.FileInfo;
import com.example.entities.Member;
import com.example.repositories.BoardDataRepository;
import com.example.repositories.FileInfoRepository;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class BoardDataTest2 {

    @Autowired
    private BoardDataRepository boardDataRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;
    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        BoardData boardData = new BoardData();
        boardData.setSubject("게시글 제목");
        boardData.setContents("게시글 내용");
        boardData.setViewCounts(100);
        boardData.setRegDt(LocalDateTime.now());
        boardData.setModDt(LocalDateTime.now());

        for(int i=1; i<=3; i++) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName("파일명" + i);
            fileInfo.setMineType("image/png");
            fileInfo.setDone(true);
            fileInfo.setRegDt(LocalDateTime.now());

            boardData.getFileInfos().add(fileInfo);
        }

        boardDataRepository.saveAndFlush(boardData);
        em.clear();

        BoardData savedBoardData = boardDataRepository.findById(boardData.getId()).orElseThrow(EntityNotFoundException::new);

        assertEquals(3, savedBoardData.getFileInfos().size());
    }

    public BoardData createBoardData() {
        BoardData boardData = new BoardData();
        boardData.setSubject("게시글 제목");
        boardData.setContents("게시글 제목");
        boardData.setViewCounts(100);
        boardData.setRegDt(LocalDateTime.now());
        boardData.setModDt(LocalDateTime.now());

        for(int i=1; i<=3; i++) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName("파일명" + i);
            fileInfo.setMineType("image/png");
            fileInfo.setDone(true);
            fileInfo.setRegDt(LocalDateTime.now());

            boardData.getFileInfos().add(fileInfo);
        }

        Member member = new Member();
        member.setUserId("user1");
        member.setUserNm("사용자1");
        member.setUserPw("123456");
        memberRepository.save(member);

        boardData.setMember(member);
        boardDataRepository.save(boardData);

        return boardData;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        BoardData boardData = createBoardData();
        boardData.getFileInfos().remove(0);
        em.flush();
    }
}
