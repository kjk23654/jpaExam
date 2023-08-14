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

    public BoardData createBoardData2() {
        BoardData boardData = new BoardData();
        boardData.setSubject("게시글 제목");
        boardData.setContents("게시글 본문");
        boardData.setViewCounts(100);
        boardData.setRegDt(LocalDateTime.now());
        boardData.setModDt(LocalDateTime.now());
        BoardData savedBoardData = boardDataRepository.save(boardData);

        for(int i=1; i<=3; i++) {
            // FileInfo (하나의 게시글에 여러개의 FileInfo가 들어오는 전제
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName("파일명" + i);
            fileInfo.setMineType("image/png");
            fileInfo.setDone(true);
            fileInfo.setRegDt(LocalDateTime.now());
            fileInfo.setBoardData(savedBoardData);
            FileInfo savedFileInfo = fileInfoRepository.save(fileInfo);
            savedBoardData.getFileInfos().add(savedFileInfo);
        }

        Member member = new Member();
        member.setUserId("user1");
        member.setUserNm("사용자1");
        member.setUserPw("123456");
        memberRepository.save(member);

        savedBoardData.setMember(member);

        return savedBoardData;
    }

    @Test
    @DisplayName("지연로딩 테스트")
    public void lazyLoadingTest() {
        BoardData boardData = createBoardData2();
        Long fileInFoId = boardData.getFileInfos().get(0).getId();
        em.flush();
        em.clear();

        FileInfo fileInfo = fileInfoRepository.findById(fileInFoId).orElseThrow(EntityNotFoundException::new);
        System.out.println("BoardData class : " + fileInfo.getBoardData().getClass());
        // fileInfo.getBoardData().getClass() = fileInfo 클래스 안에 멤버 BoardData 클래스의 정보.
        System.out.println("===================================");
        fileInfo.getBoardData().getRegDt();
        // fileInfo 엔티티 클래스의 BoardData 멤버와 매핑된 BoardData의 등록시간
        System.out.println("========================");
    }
}
