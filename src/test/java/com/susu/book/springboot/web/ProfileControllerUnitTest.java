package com.susu.book.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

// 스프링 환경이 필요없는 test기 때문에 @SpringBootTest 없다.
// ProfileController 에서 @Autowired가 아닌 생성자 주입방식을 사용했기 때문임.
public class ProfileControllerUnitTest {
  @Test
  public void real_profile이_조회된다(){
    // given
    String expectedProfile = "real"; // deploy.sh에 현재 활성화 시킨 profile : -Dspring.profiles.active=real \
    MockEnvironment env = new MockEnvironment();
    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("oauth");
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    // when
    String profile = controller.profile();

    // then
    assertThat(profile).isEqualTo(expectedProfile);
  }

  @Test
  public void real_profile이_없으면_첫번째가_조회된다(){
    // given
    String expectedProfile = "oauth";
    MockEnvironment env = new MockEnvironment();

    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    // when
    String profile = controller.profile();

    // then
    assertThat(profile).isEqualTo(expectedProfile);
  }
}
