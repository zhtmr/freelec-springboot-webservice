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

>1. ~~**`secure = false`**~~ 옵션은 2.1부터 지원하지 않음. 
>2. **필터** 
><pre>
><code>
>controllers = HelloController.class,
>       excludeFilters = {
>        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
></code>
></pre>  

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
> - ~~2020.4.18 현재 다시 해당 오류 발생함. 도저히 원인을 모르겠음~~  
> -> appspec.yml 에 오타가 있어서 빌드가 안됐었음.  
> -> 로컬에서 build 후 커밋푸쉬 했을때는 travis에서 빌드가 됐다가 안됐다가 반복함.  
> -> 로컬에서 clean > build 하니까 성공.   
> -> .gitignore 에 build 관련 등록  

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

<pre>
<code> 
   project/ - top-level github directory
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
</code>
</pre>

## 메소드 참조, 생성자 참조
### 람다식
<pre>
<code>
String [] strings = new String [] {
    "6", "5", "4", "3", "2", "1"
};

List<String> list = Arrays.asList(strings);

for(String s : strings)
    System.out.println(s);
</code>
</pre>
위와 같은 코드를 람다식으로 표현 한다면

``list.forEach(x -> System.out.println(x));``  
> java 8 부터 stream() 을 이용해 메소드 들을 체이닝(chaining)을 통해 조합해서 사용할 수 있다.  
for, if 등을 훨씬 간략하게 사용가능함. 그러나 무분별하게 사용 시 [성능 이슈](https://homoefficio.github.io/2016/06/26/for-loop-%EB%A5%BC-Stream-forEach-%EB%A1%9C-%EB%B0%94%EA%BE%B8%EC%A7%80-%EB%A7%90%EC%95%84%EC%95%BC-%ED%95%A0-3%EA%B0%80%EC%A7%80-%EC%9D%B4%EC%9C%A0/)가 있다. 

#### 어떻게 가능한가?
먼저 forEach 의 생김세를 보면 Consumer functional interface를 매개변수로 받는 stream 인터페이스의 추상메소드다.
![image](https://user-images.githubusercontent.com/48509269/80193896-8a118400-8654-11ea-9d23-c11c60b367e5.png)
> Consumer 함수적 인터페이스는 리턴값이 없는 accept() 메소드를 가지고 있다.  
Consumer는 단지 매개값을 소비하는 역할만 하며, 소비한다는 말은 사용만하고 리턴값이 없다는 뜻이다.  
![image](https://user-images.githubusercontent.com/48509269/80194162-f096a200-8654-11ea-9bb3-8404f6692ee4.png)  
![image](https://user-images.githubusercontent.com/48509269/80194192-f9877380-8654-11ea-955b-4c8d2239e809.png)
   

사실 위에서 ``list.forEach(x -> System.out.println(x));``  이렇게 구현한 방법은  *list의 요소를 입력받아, 단순히 println메소드에 전달해주는 역할만*  한 것이다. 함수형 인터페이스인 Consumer 가 가지고 있는 accept 메소드의 구현체를 직접 전달한 것이다. 이렇게 될 경우 Consumer가 구현해야 되는 accept 메소드가 실행될때 println메소드를 한번더 실행해주는 형태가되어, 메소드의 **[call stack](https://ko.wikipedia.org/wiki/%EC%BD%9C_%EC%8A%A4%ED%83%9D)** 이 1depth 깊어진 결과가 된다. 

어차피 forEach에 전달되는 매개변수는 Consumer의 accept 메소드로 가게되어 구현을 해야만 하는데, 미리 구현을 해서 전달을 하게되면 두 번 구현을 하게됨. 아래처럼 **accept가 구현해야 할 메소드만 전달**하면 된다.

``list.forEach(System.out::println);``

실제로 Consumer 인터페이스의 accept 메소드는 아래처럼 생겼다. 리턴값이 없다.  
![image](https://user-images.githubusercontent.com/48509269/80194649-a3ff9680-8655-11ea-9fe3-db152481320b.png)

그리고 System.out.println() 의 모양은 아래처럼 생겼다. 마찬가지로 리턴값이 없다.  
![image](https://user-images.githubusercontent.com/48509269/80194906-12dcef80-8656-11ea-9f3a-0aec65a2208e.png)

따라서 accept가 구현해야할 메소드는 System.out의 println 이라고 ``메소드 참조`` 방식으로 전달하기만 하면된다.


### Stream()
![image](https://user-images.githubusercontent.com/48509269/80200804-268c5400-865e-11ea-91cf-0f0235cf663a.png)

![image](https://user-images.githubusercontent.com/48509269/80200847-360b9d00-865e-11ea-8da2-5a50eea791d2.png)

![image](https://user-images.githubusercontent.com/48509269/80200952-5d626a00-865e-11ea-892f-82c4ecaedc8a.png)

![image](https://user-images.githubusercontent.com/48509269/80201052-8125b000-865e-11ea-8924-e55f64a2588f.png)

* * *
![image](https://user-images.githubusercontent.com/48509269/80201292-de216600-865e-11ea-9367-748aa6a32121.png)
> [람다 테스트](https://github.com/zhtmr/lambdatest/tree/master/src/lamdaTest)

## Nginx
* 배포하는 동안 애플리케이션이 잠깐 종료되는 문제가 있음.
* 이때 클라이언트에서 접속 요청이 오게되면 서비스에 접속을 할 수 없음.
* 기존 Jar를 종료시키고 새로운 Jar를 실행시키기 까지의 잠깐의 틈이 있기 때문.

### Nginx proxy
1. 클라이언트와 서버 사이에서 Controller 로 요청을 분배함.
2. 리버스 프록시 서버(Nginx)는 요청을 전달하고, 실제 요청에 대한 처리는 뒷단의 웹 애플리케이션 서버가 처리함.
3. 스프링부트에서 서로 다른 profile 두개를 만들어서 각각 port를 다르게 설정해서 배포함.
4. 배포가 시작되면 둘 중 하나의 port 로 연결을 함.
5. 그리고 나중에 새로운 jar가 배포되면 연결되어 있지 않은 스프링 부트를 종료하고 그 profile 에 새 버전의 스프링부트를 시작함.
6. 새 스프링 부트가 정상적으로 실행됐는지 확인함. 

### Nginx 관련 시행착오..

![image](https://user-images.githubusercontent.com/48509269/80683156-b159cc80-8afe-11ea-8765-3ed0c28b29e7.png)

배포를 계속해봐도 8081로만 배포가 됨. 다시 배포하면 8081에서 실행중인 애플리케이션 종료하고 다시 8081로 배포됨.


![image](https://user-images.githubusercontent.com/48509269/80683480-37761300-8aff-11ea-9eaf-7c17ac8a68c0.png)
~~/profile 주소로 들어가도 계속 real 만 나옴. real1, real2 이 번갈아가면서 나와야 되는거 아닌가??~~  
-> application.properties에서 `profile = outh` 로 바꾸니까 `real1`, `real2` 번갈아가면서 나옴.


> 브라우저에서 보여지는 값은 application.properties 에서 include를 `profile = real`로 되어 있을때는 `real`만 보임. (`profiles.get(0)` : 실행중인 프로필 중에 첫번째가 리턴됨).  


실제 EC2에는 `real`, `real1` 이 배포된 상태였음.  
![image](https://user-images.githubusercontent.com/48509269/80684367-8c665900-8b00-11ea-8dc5-8e6270e314c6.png)

#### 원인
