# 한밭대학교 컴퓨터공학과 WCD팀

**팀 구성**

- 20181613 노성준
- 20181633 전재윤
- 20207124 김강산

## <u>Teamate</u> Project Background

- ### 필요성
  - OOO
  - OOO
- ### 기존 해결책의 문제점
  - OOO
  - OOO

## System Design

- ### System Requirements
  - OOO
  - OOO

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

#### 

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
