package com.susu.book.springboot.service;

import com.susu.book.springboot.domain.posts.Posts;
import com.susu.book.springboot.domain.posts.PostsRepository;
import com.susu.book.springboot.web.dto.PostsResponseDto;
import com.susu.book.springboot.web.dto.PostsSaveRequestDto;
import com.susu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    /*
        [ @Transactional 의 의미 ]

        - 하나의 작업단위로 묶어서 트랜잭션으로 관리. 모두 성공 or 모두 실패(롤백) : 원자성

        - 트랜잭션이 진행되는 동안에 데이터베이스가 변경 되더라도 업데이트된 데이터베이스로 트랜잭션이 진행되는것이 아니라,
        처음에 트랜잭션을 진행 하기 위해 참조한 데이터베이스로 진행된다. : 일관성

        - 둘 이상의 트랜잭션이 동시에 병행 실행되고 있을 경우에 어느 하나의 트랜잭션이라도 다른 트랜잭션의 연산을 끼어들 수 없다.
        하나의 특정 트랜잭션이 완료될때까지, 다른 트랜잭션이 특정 트랜잭션의 결과를 참조할 수 없다. : 독립성

        - 트랜잭션이 성공적으로 완료됬을 경우, 결과는 영구적으로 반영되어야 한다 : 지속성


        [ 예외처리 방식과 트랜잭션 ]

        - 예외를 실행하는 쪽으로 던져서 처리하기때문에 셋 중 어느하나라도 실패하면 모두 취소 시킬수 있다. (원자성)

상품발송() {
    try {
        포장();
        영수증발행();
        발송();
    }catch(예외) {
       모두취소();
    }
}

포장() throws 예외 {
   ...
}

영수증발행() throws 예외 {
   ...
}

발송() throws 예외 {
   ...
}

===========================================================================================
        - 예외를 각 메소드에서 처리하게 되면 데이터 정합성이 흔들림 (일관성)

상품발송() {
    포장();
    영수증발행();
    발송();
}

포장(){
    try {
       ...
    }catch(예외) {
       포장취소();
    }
}

영수증발행() {
    try {
       ...
    }catch(예외) {
       영수증발행취소();
    }
}

발송() {
    try {
       ...
    }catch(예외) {
       발송취소();
    }
}
     */
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId(); // 저장하고 Id(PK) 반환

    }

    /*  [ 람다식 안쓴 코드(익명 구현객체) ]

    orElseThrow(new Supplier<IllegalArgumentException>() {
        @Override
        public IllegalArgumentException get() {
            return new IllegalArgumentException("해당 사용자가 없습니다. id="+id);
        }

        [ 람다식 사용 ]

        orElseThrow(() -> { return new IllegalArgumentException("해당 사용자가 없습니다. id="+id); });

        [ 람다식의 함수 몸체에 실행문이 한개이고,
        그 실행문이 return문일 경우 중괄호, return 생략가능 ]

        orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));

        [ 람다식의 사용 목적 ]
        람다식은 결국 로컬 익명 구현객체를 생성하게 되지만, 이 람다식의 사용목적은 인터페이스가 가지고 있는
        메소드를 간편하게 즉흥적으로 구현해서 사용하는 것이 목적이다. (함수형 프로그래밍, 병렬처리와 이벤트 지향 프로그래밍)

        [ 람다식을 사용하기 위한 인터페이스 조건 ]
        한개의 추상메소드만 가지고 있어야 한다. @FunctionalInterface 붙임
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }
}
