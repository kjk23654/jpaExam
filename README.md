#JPAStudy
***

##JPA 복습을 위한 repository
* 1. Entity
* 2. Repository
* 3. 쿼리 메서드
* 4. @Query 애노테이션
* 5. Querydsl 

** JPAQuery : 쿼리를 직접 쓰는 대신 자바코드로 쿼리를 날릴 수 있도록 하는 클래스

** BooleanBuilder : 쿼리에 들어갈 조건을 만들어주는 빌더

** Pageable pageable = PageRequest.of(0, 5);
첫 번째 인자 : 조회할 페이지의 번호
두 번째 인자 :  한 페이지당 조회할 데이터 개수

** @joinColumn 
   name 속성 : 외래키명을 입력한 값으로 설정할 수 있음.

** @OneToMany(mapped=)
   mapped 속성은 연관관계의 주인을 설정할 때 사용함.