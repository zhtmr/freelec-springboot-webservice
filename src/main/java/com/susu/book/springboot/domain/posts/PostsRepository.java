package com.susu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/*
*   Dao같은 역할. Posts 클래스로 DB를 접근하게 해줌.
*
*   1. JpaRepository<Entity클래스, PK타입> 상속
*   2. CRUD 메소드 자동 생성
* */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
