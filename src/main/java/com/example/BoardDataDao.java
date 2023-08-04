package com.example;

import com.example.entities.BoardData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController // JSON 형태의 데이터를 반환할 때 사용하는 애노테이션(@Controller + @ResponseBody)
public class BoardDataDao {

    @Autowired // 의존 자동 주입
    EntityManagerFactory emf; // EntityManager 인스턴스를 만들어줌.

    @GetMapping("/board_test")
    public BoardData test() {

        BoardData board = new BoardData(); // 객체 생성

        EntityManager em = emf.createEntityManager(); // EntityManagerFactory에서 엔티티매니저 인스턴스를 생성

        EntityTransaction tx = em.getTransaction(); // Entity에 Transaction을 부여함

        BoardData findBoardData = null;

        try {
                tx.begin();

                board.setSubject("게시글 제목");
                board.setContents("게시글 내용");
                board.setRegDt(LocalDateTime.now());

                em.persist(board); // 객체에 영속성을 추가 (insert 쿼리가 날아갈 것)

                board.setSubject("(수정)게시글 제목"); // 수정 (update 쿼리가 날아감)

                findBoardData = em.find(BoardData.class, board.getId()); // find는 조회할 때 사용하는 메서드

            List<BoardData> boardDatas = em.createQuery("select b from BoardData b", BoardData.class)
                                            .getResultList();

            System.out.println(boardDatas);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

        return findBoardData;
    }


}
