#JPAStudy
***

##JPA 복습을 위한 repository
* 1. Entity
* 2. Repository
* 3. 쿼리 메서드
* 4. @Query 애노테이션
* 5. Querydsl 
* 6. 영속성 전이
* 7. 지연 로딩
* 8. 엔티티 공통 속성화
***

** JPAQuery : 쿼리를 직접 쓰는 대신 자바코드로 쿼리를 날릴 수 있도록 하는 클래스

** BooleanBuilder : 쿼리에 들어갈 조건을 만들어주는 빌더

** Pageable pageable = PageRequest.of(0, 5);
첫 번째 인자 : 조회할 페이지의 번호
두 번째 인자 :  한 페이지당 조회할 데이터 개수

** @joinColumn 
   name 속성 : 외래키명을 입력한 값으로 설정할 수 있음.

** @OneToMany(mapped=)
   mapped 속성은 연관관계의 주인을 설정할 때 사용함.

** 고아 객체 : 부모 엔티티와 연관 관계가 끊어진 자식 엔티티를 고아 객체라고 합니다
   @OneToMany의 orphanRemoval 속성은 고아 객체를 제거하기 위해 사용됨.

** @ManyToOne(fetch = FetchType.LAZY)
   : 지연로딩을 선택. (<-> FetchType.EAGER은 즉시 로딩)

** N+1 문제가 발생할 수도 있음. 
   N+1 문제는 목록을 출력하는 경우 즉시로딩을 할 경우 한번에 연산가능한데,
   필요한 경우에 조회하는 지연로딩의 경우, 모든 목록을 다 조회한 후 결과값을 도출하므로 
   N(모든 목록을 다 조회) + 1 하는 문제로 성능이 떨어지는 문제가 있음
   -> 해결 방법 = 즉시로딩으로 변경

** 설정 클래스에는 @EnableJpaAuditing
   공통 속성 엔티티 클래스에는 @EntityListeners, @MappedSuperclass를 사용한다
   