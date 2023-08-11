package com.example;


import com.example.entities.BoardData;
import com.example.entities.QBoardData;
import com.example.repositories.BoardDataRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
// 해당 테스트 클래스의 property는 application-test로 하겠다는 의미
public class BoardDataRepositoryTest {

    @Autowired
    BoardDataRepository boardDataRepository;
    // Repository는 EntityManager의 역할과 DAO역할을 같이 할 수 있다.

    @PersistenceContext //
    EntityManager em;

    @Test
    @DisplayName("게시글 저장 테스트")
    public void createBoardDataTest() {
        BoardData boardData = new BoardData();
        boardData.setSubject("게시글 제목");
        boardData.setContents("게시글 본문");
        boardData.setRegDt(LocalDateTime.now());
        boardData.setModDt(LocalDateTime.now());

        BoardData savedBoardData = boardDataRepository.saveAndFlush(boardData);
        // 엔티티 매니저의 persist 메서드와 같이 해당 객체에 영속성을 넣어주는 역할을 수행
        System.out.println(savedBoardData);
    }

    public void createBoardDatas() {
        for(int i=1; i<=10; i++) {
            BoardData boardData = new BoardData();
            boardData.setSubject("게시글 제목" + i);
            boardData.setContents("게시글 내용" + i);
            boardData.setViewCounts(100 + i);
            boardData.setRegDt(LocalDateTime.now());
            boardData.setModDt(LocalDateTime.now());
            BoardData savedBoardData = boardDataRepository.save(boardData);
        }
    }

    @Test
    @DisplayName("게시글 제목 조회 테스트")
    public void findBySubjectTest() {
        createBoardDatas();
        List<BoardData> boardDatas = boardDataRepository.findBySubject("게시글 제목1");
        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }
    }

    @Test
    @DisplayName("게시글 제목, 게시글 본문 or 테스트")
    public void findBySubjectOrContents() {
        createBoardDatas();
        List<BoardData> boardDatas = boardDataRepository.findBySubjectOrContents("게시글 제목1", "게시글 내용1");
        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }
    }

    @Test
    @DisplayName("조회수 LessThan 테스트")
    public void findByViewCountsLessThanTest() {
        createBoardDatas();
        List<BoardData> boardDatas = boardDataRepository.findByViewCountsLessThan(105);
        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }
    }

    @Test
    @DisplayName("조회수 내림차순 조회 테스트")
    public void findByViewCountsLessThanOrderByViewCountsDesc() {
        createBoardDatas();
        List<BoardData> boardDatas = boardDataRepository.findByViewCountsLessThanOrderByViewCountsDesc(110);
        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }
    }

    @Test
    @DisplayName("@Query를 이용한 게시글 조회 테스트")
    public void findByContentsTest() {
        createBoardDatas();
        List<BoardData> boardDatas = boardDataRepository.findByContents("게시글 본문");

        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        createBoardDatas();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoardData qBoardData = QBoardData.boardData;
        JPAQuery<BoardData> query = queryFactory.selectFrom(qBoardData)
                .where(qBoardData.subject.eq("게시글 제목2"))
                .where(qBoardData.contents.like("%" + "본문" + "%"))
                .orderBy(qBoardData.viewCounts.desc());

        // select b from BoardData b where b.subject = "게시글 제목2" and b.contents like "%본문%" order by viewCounts desc;

        List<BoardData> boardDatas = query.fetch(); // fetch() : 리스트 결과 반환
        for(BoardData boardData : boardDatas) {
            System.out.println(boardData);
        }

    }

    public void createBoardDatas2() {
        for(int i=1; i<=5; i++) {
            BoardData boardData = new BoardData();
            boardData.setSubject("게시글 제목" + i);
            boardData.setContents("게시글 내용" + i);
            boardData.setViewCounts(100+i);
            boardData.setRegDt(LocalDateTime.now());
            boardData.setModDt(LocalDateTime.now());
            boardDataRepository.save(boardData);
        }

        for(int i=6; i<=10; i++) {
            BoardData boardData = new BoardData();
            boardData.setSubject("게시글 제목" + i);
            boardData.setContents("게시글 내용" + i);
            boardData.setViewCounts(100+i);
            boardData.setRegDt(LocalDateTime.now());
            boardData.setModDt(LocalDateTime.now());
            boardDataRepository.save(boardData);
        }
    }

    @Test
    @DisplayName("게시글 Querydsl 조회 테스트2")
    public void querydslTest2() {
        createBoardDatas2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoardData boardData = QBoardData.boardData;
        String contents = "게시글 본문";
        int viewCount = 105;

        booleanBuilder.and(boardData.contents.like("%"+ contents + "%"));
        booleanBuilder.and(boardData.viewCounts.gt(viewCount)); //

        Pageable pageable = PageRequest.of(0,5);
        Page<BoardData> boardDataPagingResult = boardDataRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + boardDataPagingResult.getTotalElements());

        List<BoardData> boardDatas = boardDataPagingResult.getContent();
        for(BoardData _boardData : boardDatas) {
            System.out.println(_boardData);
        }
    }
}
