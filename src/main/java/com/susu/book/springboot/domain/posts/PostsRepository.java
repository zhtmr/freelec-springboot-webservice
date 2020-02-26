package com.susu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
*   Dao같은 역할. Posts 클래스로 DB를 접근하게 해줌.
*
*   1. JpaRepository<Entity클래스, PK타입> 상속
*   2. CRUD 메소드 자동 생성
* */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // SpringDataJpa에서 지원하지 않는 메소드는 쿼리로 작성해도됨(아래 코드는 기본 메소드로 가능하지만 한번 해본다)
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC") // JPQL에선 테이블이 아니라 엔티티 기반 네이티브 쿼리에서 p.*와 같음
    List<Posts> findAllDesc();
}
