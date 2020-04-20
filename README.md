# freelec-springboot-webservice [![Build Status](https://travis-ci.org/zhtmr/freelec-springboot-webservice.svg?branch=master)](https://travis-ci.org/zhtmr/freelec-springboot-webservice)
* 스프링 부트
* JPA
* JUnit 테스트
* gradle
* 소셜 로그인
* AWS 무중단 배포


## ~~Intellij + SpringBoot + Template engine 에서 livereload 하기~~ 
>인텔리제이 커뮤니티에서 안되는듯..  
   이것때문에 travis 빌드 안돼서 주석처리 했음.

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

## Travis 빌드 실패... 
> ~~2020.4.18 현재 다시 해당 오류 발생함. 도저히 원인을 모르겠음~~
> appspec.yml 에 오타가 있어서 빌드가 안됐었음.
> 로컬에서 build 후 커밋푸쉬 했을때는 travis에서 빌드가 됐다가 안됐다가 반복함.
> 로컬에서 clean > build 하니까 성공. 
> .gitignore 에 build 관련 등록

![image](https://user-images.githubusercontent.com/48509269/79460822-56b96e80-8030-11ea-8f6a-3c1d48a907ca.png)


![image](https://user-images.githubusercontent.com/48509269/79461069-a13aeb00-8030-11ea-9340-38f3a4816a77.png)


* 현재 aws IAM(Identity and Access Management) 를 통해 Travis CI는 S3 와 CodeDeploy 권한을 부여받은 상태임
* Travis CI 에서 S3로 build 파일은 전달이 잘 되었음.
> ~~2020.4.18 현재 S3에도 빌드파일이 안올라감.. travis에서 JPA 오류 뜨면서 빌드 자체가 안되는중..~~

![image](https://user-images.githubusercontent.com/48509269/79466698-e3b3f600-8037-11ea-8798-a5c30a5a6a26.png)

* 그러나 Travis CI가 codedeploy 에 배포요청 시 S3에 있는 파일을 가져오지 못함.

![image](https://user-images.githubusercontent.com/48509269/79466890-22e24700-8038-11ea-9b4f-d53e3bbe7e14.png)


* travis 웹사이트에서 `Restart build` 하게 되면 빌드 실패뜸..
![image](https://user-images.githubusercontent.com/48509269/79549608-2de9b580-80d2-11ea-830e-f6d1d0d1e0ee.png)



### - Travis 에서 멀티 language 하는 방법 예시

 ```project/ - top-level github directory
   project/backend - Python backend
   project/backend/tests - Python tests
   project/android/AppName - Android app
   project/ios/AppName - iOS app


matrix:
  include:
    - language: python
      python: 2.7
      before_script:
        - cd backend/tests
      script:
        - python -m unittest discover

    - language: android
      dist: trusty
      jdk: oraclejdk8
      android:
        components:
          - tools
          - android-25
          - build-tools-25.0.3
      before_script:
        - cd android/AppName
      script:
        - ./gradlew build connectedCheck

    - language: objective-c
      os: osx
      osx_image: xcode8.3
      before_script:
        - cd ios/AppName
      script:
        - xcodebuild -workspace AppName.xcworkspace -scheme AppName
          -destination 'platform=iOS Simulator,name=iPhone 7,OS=10.3' build test

notifications:
  email:
    - yourname@gmail.com
