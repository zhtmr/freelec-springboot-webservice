package com.susu.book.springboot.service;

import com.susu.book.springboot.domain.posts.Posts;
import com.susu.book.springboot.domain.posts.PostsRepository;
import com.susu.book.springboot.web.dto.PostsSaveRequestDto;
import com.susu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId(); // 저장하고 Id(PK) 반환

    }

    /*  [ 람다식 안쓴 코드 ]

    orElseThrow(new Supplier<IllegalArgumentException>() {
        @Override
        public IllegalArgumentException get() {
            return new IlleagalArgumentException("해당 사용자가 없습니다. id="+id);
        }

        [ 람다식 사용 ]

        orElseThrow(() -> { return new IllegalArgumentException("해당 사용자가 없습니다. id="+id); });

        [ 람다식의 함수 몸체에 실행문이 한개이고,
        그 실행문이 return문일 경우 중괄호, return 생략가능 ]

        orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));

        [ 람다식의 사용 목적 ]
        람다식은 결국 로컬 익명 구현객체를 생성하게 되지만, 이 람다식의 사용목적은 인터페이스가 가지고 있는
        메소드를 간편하게 즉흥적으로 구현해서 사용하는 것이 목적이다. (함수형 프로그래밍, 병렬처리와 이벤트 지향 프로그래밍)
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
}
