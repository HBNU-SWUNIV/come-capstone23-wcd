# 한밭대학교 컴퓨터공학과 WCD팀

## 👪 1. 팀 구성

- 20181613 노성준
- 20181633 전재윤
- 20207124 김강산

## 🌟 1. 서비스 소개

- ### 필요성
  - 현재 대부분의 모임 플랫폼들은 여전히 오프라인 모임을 위주로 서비스를 제공하고 있어 온라인 모임 활동에 적합하지 않은 경우가 많다.
  - 온라인 모임 활동 시 필요한 음성 채팅, 화상 채팅 등의 기능을 이용하기 위해서는 다른 플랫폼을 함께 사용해야 하는 등 불편한 점이 많다.
  - 지역별 모임 활성화도 정도의 차이가 있어 수도권 및 광역권을 제외한 지역에서는 모임을 찾는 것조차도 어려운 경어가 많아, 모임 활동을 하고 싶어도 할 수 없는 상황이다.
 
- ### 해결책
  - 기존 모임 플랫폼을 개선하고, 온라인 환경의 장점을 살려 지역과 활동시간에 제약이 없는 자유로운 모임 활동이 가능하게 한다.
  - 모임 모집, 소통, 기록, 관리 등 모임에 필요한 서비스를 통합하여 제공한다.

## 🏗️ 2. System Design

### 🔧 기술 스택

#### Frontend

- VueJS
- NuxtJS
- vuetify
- Javascript

#### Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cloud
- Java
- MariaDB
- Redis
- JUnit5

#### Infra

- RabbitMQ
- AbleCloud
- Jenkins
- Docker

### 시스템 구성도
<img width="100" src="![WCD_시스템구성도](https://github.com/HBNU-SWUNIV/come-capstone23-wcd/assets/44565811/7274eee8-e7f5-478c-86f3-8367b80af91e)"/>

- ### System Requirements
  - 온라인 모임 모집 기능
    - 모임 생성
    - 모임 검
  - 모임 소통 기능
    - 채팅
    - 음성, 화상 채팅
    - 알림
  - 모임 관리 기능
    - 일정 관리 

## Case Study
- ### Description

## Conclusion

- ### OOO
- ### OOO

## Project Outcome

- ### 20XX 년 OO학술대회

## Branch 규칙

### Git flow

- 메인 브랜치: main, develop
- 보조 브랜치: feature, release, hotfix

- 보조 브랜치는 사용을 마치면 브랜치를 삭제하도록 한다.
- merge할 때는 --no-ff를 사용하여 기록을 그룹화한다.

## Commit 규칙

### 규칙

1. 제목과 본문을 한 줄 띄어 구분
2. 제목은 50자 이내
3. 제목 끝에 마침표 X
4. 본문의 각 행은 72자 이내 (줄바꿈 사용)
5. 본문은 어떻게 보다 무엇을, 왜에 대하여 설명

### 구조

<type>: <subject>

<body>
<footer>

### Type

- feat : 새로운 기능 추가, 기존의 기능을 요구 사항에 맞추어 수정
- fix : 기능에 대한 버그 수정
- build : 빌드 관련 수정
- chore : 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore
- ci : CI 관련 설정 수정
- docs : 문서(주석) 수정
- style : 코드 스타일, 포맷팅에 대한 수정
- refactor : 기능의 변화가 아닌 코드 리팩터링 ex) 변수 이름 변경
- test : 테스트 코드 추가/수정
- release : 버전 릴리즈

#### Subject

Type 과 함께 헤더를 구성한다. 예를들어, 로그인 API 를 추가했다면 다음과 같이 구성할 수 있다.

ex) feat: 로그인 API 추가

#### Body

헤더로 표현이 가능하다면 생략이 가능하다. 아닌 경우에는 자세한 내용을 함께 적어 본문을 구성한다.

#### Footer

어떠한 이슈에 대한 commit 인지 issue number 를 포함한다.
