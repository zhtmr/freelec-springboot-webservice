# freelec-springboot-webservice
* 스프링 부트
* JPA
* JUnit 테스트
* gradle
* 소셜 로그인
* AWS 무중단 배포


## ~~Intellij + SpringBoot + Template engine 에서 livereload 하기~~ : 인텔리제이 커뮤니티에서 안되는듯.. 
> 이것때문에 travis 빌드 안돼서 주석처리 했음.

![주석 2020-02-27 195456](https://user-images.githubusercontent.com/48509269/75438505-6951e880-599b-11ea-89be-fd4212658e08.jpg)

## .gitignore 작동 안할때 -> 캐시삭제
> git rm -r --cached .
  git add .
  git commit -m "fixed untracked files"

## MockMvc 
![image](https://user-images.githubusercontent.com/48509269/78100506-ed2a4500-741f-11ea-83a5-d71b7542347d.png)

![image](https://user-images.githubusercontent.com/48509269/78100536-fc10f780-741f-11ea-8649-51e472f6deef.png)

## @WebMvcTest 와 @SpringBootTest 차이
`@SpringBootTest`의 경우 일반적인 테스트로 slicing을 전혀 사용하지 않기 때문에 전체 응용 프로그램 컨텍스트를 시작한다. 그렇기 때문에 전체 응용 프로그램을 로드하여 모든 bean을 주입하기 때문에 속도가 느리다.

`@WebMvcTest`의 경우는 Controller layer를 테스트하고 모의 객체를 사용하기 때문에 나머지 필요한 bean을 직접 세팅해줘야 한다. @WebMvcTest는 `WebSecurityConfigurerAdapter, WebMvcConfigurer, @ControllerAdvice, @Controller`를 읽음. 즉, `@Repository, @Service, @Component`는 스캔 대상이 아님. 

따라서 `@WebMvcTest` 는 SecurityConfig를 생성하기 위해 필요한 `@Service` 인 CustomOAuth2UserService를 읽지 못함. 

>1. (`secure = false` 옵션은 2.1부터 지원하지 않음. )
>2. **필터** 
`controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})`  

## Travis CI 가 권한부족으로 gradlew을 실행하지 못할때
### gradlew 자체에 권한주기
![image](https://user-images.githubusercontent.com/48509269/79227703-42903880-7e9b-11ea-82cb-574e9b4d7a6f.png)
### .travis.yml 에 실행권한 추가
![image](https://user-images.githubusercontent.com/48509269/79239580-b1758d80-7eab-11ea-8595-bda44bf5015a.png)

## gradle > test 시에 "Test event were not received" 라고 뜰때
![wn](https://user-images.githubusercontent.com/48509269/79236227-a6206300-7ea7-11ea-98d8-69ef2cfc0a65.jpg)
>File -> Settings ->Build,Execution, Deployment -> Build Tools -> Gradle

![image](https://user-images.githubusercontent.com/48509269/79236273-b6384280-7ea7-11ea-8b02-82222295b7f1.png)
