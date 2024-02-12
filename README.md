# PAYHERE
기간 : 2024.02.07 ~ 2024.02.12
<hr>


### 🏗 프로젝트 아키텍쳐
![페이히어_시스템_아키텍처](https://github.com/devMtn30/cafe_store/assets/97010280/a5558237-55fe-486d-8f31-75af13a175f1)

#### 아키텍처 링크 : https://colab.research.google.com/drive/1zJgp9MXTUnsVR6QrK5egBzrgm5gUYtaZ?usp=sharing
- 서버 : Java 17 / Spring Boot 3.2.2 
- Data : MySQL 5.7 / Redis / ElasticSearch 8.6.2
- JPA (Hibernate), Spring Security, RestAssured, junit5 등으로 개발하였습니다.
- Redission의 pub-sub 방식을 사용해 distributed lock을 구현해 회원가입시 동시성 문제를 해결했습니다.
- Login, Redis에 K,V 자료구조로 userId별 token 등록하고, api 요청시 유효성 검사를 처리하였습니다.
- Logout, Redis에 등록된 자료구조를 제거하여, filter에서 로그인을 하지 않으면 요청을 할 수 없도록 하였습니다.
- 상품 등록, 수정시 ElasticSearch에 document를 등록하는 ProductIndexingAfterCommitListener를 생성하였습니다.
- 조회 성능 최적화를 위해 루씬을 기반으로 방대한 양의 데이터를 신속하게 검색할 수 있는 ElasticSearch를 통해 상품 이름 기반 검색을 구현하였습니다.
- 최신 자바 문법을 활용해(record, var 등) 가독성을 증가시켜 읽기 쉬운 프로젝트를 만들기 위해 노력했습니다.
<hr>

### ▶ 실행 방법
- Docker 실행
- Project Root에 start_project.bat 파일 실행 
- 이후 컨테이너가 전부 실행되면 프로젝트 실행
- [POSTMAN] https://documenter.getpostman.com/view/28177093/2sA2r3aRYb

<br>
<hr>

### 🗝 API 및 테스트 케이스
- `RestAssured`를 통해서, E2E(API) 테스트를 구현하였습니다.
- H2 인메모리 DB를 활용하여, 운영 DB와 테스트 DB를 격리 하였습니다.
- 각 테스트간 격리를 편리하게 만들기 위해 AcceptanceTest Annotation을 구현하였습니다.  

<br>

<hr>

### 🧪 고민점 및 설명

- **상품 이름 기반 검색 [Spring Data Elasticsearch]**   
  Elasticsearch를 도입하였고, 기존의 Nori analyzer의 성능을 향상시킨 오픈소스 jaso-analyzer 를 적용하였습니다.  
  추가로 검색 성능을 향상시키기 위해서 초성어를 뽑아내는 유틸리티를 적용하여 Document를 등록할 때 자음을 같이 등록하도록 하였습니다.  
  하지만 문제점이 있습니다, Spring boot 3.2에서 docker에 엘라스틱 서치랑 같이 올리고 spring data elasticsearch 사용할때 connect refused 가 발생하여 Spring application을 Docker에 올리지 못하였습니다.     
- 1.로컬에서 스프링부트 앱 실행시 잘동작함 
- 2.컨테이너 간 네트워크 연결 정상
- 3.Spring application.yml을 es의 컨테이너명으로 변경해도 동일한 문제 발생
- 최신 버전에서 문제가 발생하는 듯 하여, 우선적으로 로컬에서 실행하여 테스트가 가능하도록 프로젝트를 구성하였습니다. 
  
<br>

- **상품 수정 등록 이후 [ProductIndexingAfterCommitListener]**   
  상품이 등록된 이후 상품명과 item id를 엘라스틱 서치에 등록하여야 하는데, 등록시 같은 트랜잭션 내부인 경우 아직 insert가 되지 않아 product의 id가 null인 문제가 발생하게 되는데 해당 클래스를 통해 이 문제를 해결하였습니다.  
  ProductIndexingAfterCommitListener 내부에서 TransactionSynchronization를 상속 후 afterCommit 메소드를 구현하여 위의 문제를 해결했습니다.  
  @DynamicUpdate의 경우는 Hibernate가 캐시된 SQL문을 사용하지 않는 문제가 있지만 항상 일부분만 업데이트 하는 현재 상황에 적합하다고 판단하여 추가하였습니다.

<br>

- **Redis 분산락 [LockManager]**  
  RedissonClient를 통해 분산락을 구현할 때 함수형인터페이스를 활용해 조금 더 효율적으로 처리하도록 하였습니다.   
  현재는 multi-process 환경에서 회원가입시 중복으로 가입되는 일을 막기위해 추가하였지만 application의 특성 상 추후 상품 판매 등에서 lock을 도입할 때 효과적으로 분산락을 구현 및 사용하도록 따로 class로 생성하였습니다.  

<br>

- **JPA**  
  이번 과제에서, 양방향 연관관계를 최대한 줄이려 노력하였습니다. 양방향 연관관계가 필요한 경우도 있지만 프로젝트에서 어떤 도메인들이 양방향 관계인지 모르는 개발자는 이걸 언제 쓰는지 파악하는 작업을 할 거고, 이는 버그를 발생시킬 수 있다고 생각합니다.
  즉, 양방향인지 아닌지 계속 신경쓰면서 개발을 해야한다는 의미인데 결국 개발 생산성을 떨어트릴 수 있다고 생각하여 양방향 연관관계를 최대한 줄이려 노력했습니다.

<br>

- **아쉬운 점**  
  현재 도커 컨테이너 테스트가 격리되지 않았는데, testcontainers 라이브러리를 활용해 테스트 환경을 격리한다면 더 좋을 것 같습니다. 어플리케이션의 특성상(과제) 따로 격리하지는 않았습니다.  

<hr>

### 💧 프로젝트 디렉토리
```java
├─gradle
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─payhere
    │  │          └─kimsan
    │  │              ├─common
    │  │              │    ├─config
    │  │              │    ├─constant
    │  │              │    ├─exception
    │  │              │    ├─filter
    │  │              │    ├─login
    │  │              │    └─utils
    │  │              ├─user
    │  │              │    ├─apllication
    │  │              │    ├─domain
    │  │              │    └─ui
    │  │              └─product
    │  │                   ├─apllication
    │  │                   ├─domain
    │  │                   └─ui
    │  └─resources
    └─test
        └─java
            └─com
                └─payhere
                    └─kimsan
                      ├─acceptance
                      │    ├─login
                      │    ├─logout
                      │    └─product
                      ├─user
                      │    ├─apllication
                      │    ├─domain
                      │    └─ui
                      └─product
                           ├─apllication
                           ├─domain
                           └─ui
```
<br>
