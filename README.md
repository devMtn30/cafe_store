# PAYHERE
기간 : 2024.02.07 ~ 2024.02.12
<hr>


### 🏗 프로젝트 아키텍쳐
![페이히어_시스템_아키텍처](https://github.com/devMtn30/cafe_store/assets/97010280/a5558237-55fe-486d-8f31-75af13a175f1)

#### 아키텍처 링크 : https://colab.research.google.com/drive/1zJgp9MXTUnsVR6QrK5egBzrgm5gUYtaZ?usp=sharing
- 서버 : Java 17 / Spring Boot 3.2.2 
- Data : MySQL 5.7 / Redis / ElasticSearch
- JPA (Hibernate), Spring Security, RestAssured, junit5 등으로 개발하였습니다.
- Redission의 pub-sub 방식을 사용해 distributed lock을 구현했습니다.
- 조회 성능 최적화를 위해 루씬을 기반으로 방대한 양의 데이터를 신속하게 검색할 수 있는 ElasticSearch를 통해 자,모음 검색을 구현하였습니다.
- 최신 자바 문법을 활용해(record, var 등) 가독성을 증가시켜 읽기 쉬운 프로젝트를 만들기 위해 노력했습니다.
<hr>

### ▶ 실행 방법
- Project Root에 start_project.bat 파일 실행

<br>
<hr>

### 🗝 API 및 테스트 케이스
- `RestAssured`를 통해서, E2E(API) 테스트를 구현하였습니다.
- H2 인메모리 DB를 활용하여, 운영 DB와 테스트 DB를 격리 하였습니다.
- 각 테스트간 격리를 편리하게 만들기 위해 AcceptanceTest Annotation을 구현하였습니다.  

<br>

<hr>

### 🧪 고민점 및 설명

- **상품 이름 기반 검색**   
  이름에 대해서 like 검색에 대한 부분을 어떻게 구현하는게 좋을지 고민입니다. 우선 ElasticSearch를 도입하여, 이 부분을 해소할 예정입니다.
  <br>


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
    │  │              │    ├─error
    │  │              │    └─jwt
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
                        └─domain
```
<br>
