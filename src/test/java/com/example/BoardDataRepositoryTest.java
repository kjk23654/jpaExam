package com.example;


import com.example.entities.BoardData;
import com.example.repositories.BoardDataRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
// 해당 테스트 클래스의 property는 application-test로 하겠다는 의미
public class BoardDataRepositoryTest {

    @Autowired
    BoardDataRepository boardDataRepository;
    // Repository는 EntityManager의 역할과 DAO역할을 같이 할 수 있다.

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
}
