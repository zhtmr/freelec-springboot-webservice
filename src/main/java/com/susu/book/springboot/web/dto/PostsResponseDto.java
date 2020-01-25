package com.susu.book.springboot.web.dto;

import com.susu.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    // 응답 Dto
    // entity는 controller로 나오면 안되기때문에 entity를 받아서 Dto로 반환
    // Entity는 Controller로 나가서는 안됩니다.
    //여러가지 이유가 있는데요
    //
    // * 일단은 실제 서비스시에는 Entity와 전혀 관계가 없는 데이터를 JOIN해서 사용해야될때가 더 많습니다.
    //지금 하는 프로젝트에서야 거의 Entity 그대로 사용해도 무방한것처럼 보이지만, 조금만 서비스 기능을 확장하면 어차피 JOIN 해서 여러 테이블의 몇몇 필드를 조립해서 반환해야 됩니다.
    //
    // * 1번에 이어서 이후에 사용하시다보면 저장소를 조합해서 가져올때도 있습니다.
    //에를 들어 조회되어야하는 필드중 2개는 Redis라는 캐시 저장소에서, 나머지 필드는 데이터베이스에서 가져와야할수 있습니다.
    //이럴 경우 어차피 Entity는 사용할 수 없습니다.
    //
    // * 직렬화 문제가 있습니다.
    //결국 Request/Response에서 데이터를 주고 받으려면 JSON 변환이 가능하도록 설정 (직렬화)을 해야하는데,
    //데이터베이스에 대한 설정 외에 직렬화 설정까지 가지게 되면 이후 계속해서 문제가 될 소지가 있습니다.
    //
    //Entity는 Controller와 완전히 무관한 영역이라고 생각해주세요.
    //
    //RequestDto도 별도로 두는 이유는 여러테이블에 동시에 나눠 저장되거나,
    //일부는 Redis에 일부는 데이터베이스에 저장해야될 수 있기 때문입니다.
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
//
// public PostsResponseDto(Long id, String title, String content,String author ) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.author = author;
//    }
// 이런식으로 만들지 않음