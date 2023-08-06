package com.example.repositories;


import com.example.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {

    List<BoardData> findBySubject(String subject);
    // 쿼리 메서드의 네이밍룰  = find + (엔티티명) + By + 변수이름 (엔티티명은 생략 가능)

    List<BoardData> findBySubjectOrContents(String subject, String contents);

    List<BoardData> findByViewCountsLessThan(Integer viewCounts);

    List<BoardData> findByViewCountsLessThanOrderByViewCountsDesc(Integer viewCounts);

    @Query("select b from BoardData b where b.contents like %:contents% order by b.viewCounts desc")
    List<BoardData> findByContents(@Param("contents")String contents);
    // @Param은 현재 매개변수의 값을 JPQL의 매개변수와 연결 시켜주는 역할을 수행하며, value라는 속성은 생략했음
    // :contents는 @Param으로 연결된 contents임.
    // 즉, 매개변수의 값이 포함되어있는 조회하되 조회수를 내림차순으로 조회하라는 JPQL
    // 기존의 데이터베이스에서 사용하던 쿼리를 그대로 사용하기 위해서는
    // @Query의 nativeQuery 속성을 사용하면 됨
}
