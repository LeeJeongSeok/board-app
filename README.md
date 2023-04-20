# 게시판 - 프로젝트 명세서

### 목차

- 프로젝트 주제
- 프로젝트 개요
- 필요 기술 분석
- 요구사항 분석
- API 설계
- DB 설계
- 패키지 구조
- 개발

### 프로젝트 주제

- 웹의 기본이 될 수 있는 Create,Read,Update,Delete 기능을 포함한 게시판을 만들고, 추가적인 기능은 하나씩 개발하는 방향으로 진행할 예정

### 프로젝트 개요

- 프로젝트명 : Toy-Project-Board
- 개발 기간 : 2021.02.01 ~ 2021.02.28
- 주요 기능
    - 게시판 - Create, Read, Update, Delete 기능, 페이징 처리, 검색 기능
    - 유저 - Security 회원가입 및 로그인, OAuth2.0 구글 및 네이버 로그인, 회원 정보 수정, 로그인 및 회원가입시 입력값 유효성 검사
    - 댓글 - Create, Read, Update, Delete 기능
- 개발 환경 소개
    - OS : Mac
    - IDE : IntelliJ Ultimate
    - Language : Java 11
    - DB : MySQL
    - 형상관리 : github
    - Project : Gradle Project
    - Spring Boot Version : 2.7.8
    - Group : com.jeongseok
    - Artifact : board-app

### 필요 기술 분석

- Spring Web
- Lombok
- Spring Data JPA
- Spring Security
- QueryDSL
    - 동적 쿼리를 활용하기 위한 의존성
- MySQL
- Validation
    - 데이터 검증을 위한 의존성
- Thymleaf

### 요구사항 정의

- 로그인하지 않은 상태에서 접근할 수 있는 페이지
    - 로그인 페이지 (/login)
    - 회원가입 페이지 (/join)
    - 게시판 목록 조회 페이지 (/, /?page={번호}, /posts/search?keyword)
    - 게시판 상세보기 페이지 (/post/read/{id})
    - 이 외 다른 페이지 이동 시 로그인 페이지로 이동

- 회원가입
    - 입력값 : ID, PW, 이름, 이메일, 전화번호, 가입 일시, 수정일시
    - 정책 (유효성 검사)
        - 아이디는 유효성 검사를 하지 않고, 회원가입시 중복 체크를 진행
            - DB에 해당 아이디가 존재한 상태에서 회원 가입시 ‘이미 존재하는 회원’의 메시지 출력
        - 비밀번호는 최소 8 ~ 16자 이상이며, 영문 대/소문자, 숫자, 특수문자로 구성
        - 이름은 공백 체크만 한 후 길이만 체크 (최소 2자, 최대 6자)
        - 이메일은 이메일 형식의 패턴으로 구성
        - 전화번호는 010-1111-2222 형태의 입력값으로 유도
        - 최초 회원가입이 완료되면 가입 일시와 수정 일시 업데이트
        - 회원 수정시 수정 일시만 업데이트
        - 위 정책을 통과하면 회원가입이 완료되고, 로그인 페이지로 이동한다.

- 회원정보 수정
    - 회원정보 수정 가능 항목은 비밀번호, 이름, 이메일, 전화번호 가능
    - ID 값을 제외한 나머지 항목 회원가입과 마찬가지로 정책 및 유효성 검사 동일하게 진행
    - 수정이 성공적으로 이뤄지면 해당 유저 데이터의 수정 일시 컬럼을 업데이트

- 로그인
    - SpringSecurity를 활용한 로그인
    - 로그인 실패시 “아이디 또는 비밀번호가 일치하지 않는다”의 메시지 출력
    - 로그인 성공시 index 페이지로 이동

- 소셜 로그인 기능
    - 구글, 네이버 로그인 가능
    - 만약 DB에 존재하는 회원인 경우 기존 정보 유지
    - 정책 검토 필요

- 게시글 작성
    - 게시글 작성, 수정 시 제목과 내용은 공백 혹은 빈칸으로 작성될 수 없음
    - 해당 유저가 작성한 게시글만 수정, 삭제 가능
    - 로그인을 하지 않고 글 작성 버튼을 누르는 경우 로그인 페이지로 이동

- 댓글 작성
    - 댓글 작성, 수정 시 내용은 공백 혹은 빈칸으로 작성될 수 없음
    - 해당 유저만 작성한 댓글에 대해 수정, 삭제 가능
    - 로그인을 하지 안하고 댓글 작성 버튼을 누르는 경우 로그인 페이지로 이동
    - 댓글을 작성했던 게시글이 삭제될 경우 해당 댓글 데이터도 같이 삭제

### 각 기능 별 구체적인 API 명세서

[게시글 관련 API](https://www.notion.so/f39967ae935747a9a467b810f5d984a8)

[회원 관련 API](https://www.notion.so/15afc87f64944c0993b4a599122973a0)

[댓글 관련 API](https://www.notion.so/58de5d965f7943b4a832d11fe2378cad)

### 패키지 구조

```java
- config : Spring Security 관련 설정 및 JPA 관련 설정 등록
- controller : API의 endPoint 등록 및 요청/응답의 형식을 갖는 클래스 패키지
- dto : DTO(Data Transfer Object)를 위치시키는 패키지 
- repository : Repository(DB 연결시 사용하는 인터페이스)를 위치시키는 패키지
- service : 비지니스 로직을 담는 서비스 클래스 패키지
- type : 회원 탈퇴 여부, 게시물 삭제 여부 등의 다양한 enum class를 위치시키는 패키지
```

### DB 설계

[User](https://www.notion.so/44c021b8b3194916afdaa03f16893345)

[Posts](https://www.notion.so/f2fb5d7e1f2b4445a624f7bd41d56101)

[Comments](https://www.notion.so/c255dc38dcd1453580d107f6aabf08ff)

![스크린샷 2023-02-04 오전 1.10.25.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9ab8621a-26e3-43f1-b08e-c069bccfe510/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-02-04_%E1%84%8B%E1%85%A9%E1%84%8C%E1%85%A5%E1%86%AB_1.10.25.png)

## **주차별 개발 계획**

[WBS](https://www.notion.so/af898fae78ef447693b99933b1ecbe01)
