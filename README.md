# :pushpin: 세탁 및 배송 서비스 플랫폼
현대 사회에서 바쁘게 살아가는 사람들을 위해 수거, 세탁, 배송 서비스 플랫폼  
</br>

## 1. 제작 기간 & 참여 인원
-   2023년 08월 21일 ~ 2023년 09월 17일
-   팀 프로젝트(7인)
</br>

## 2. 개발도구 및 환경

-   Java 11 / Gradle
-   Oracle 11g
-   Apache Tomcat 9.0.78
-   Spring Boot 2.7.14
-   MyBatis 2.3.1
-   jQuery 3.7.1
-   Thymeleaf 3.1.2
-   IntelliJ 2023.2.1
-   Git, Github

</br>

## 3. 프로젝트 주요 기능
- 회원
  - 회원가입 및 로그인(NAVER, KAKAO 로그인 API포함)
  - 세탁신청 및 결제
  - 정기 구독권 신청 및 결제
  

- 배달원
  - 실시간 배달 주문 목록 조회
  - 티맵 API를 통한 위치 기반 거리 계산
  - 카카오 내비 API를 통한 경로 안내
  - 배달 현황 등록
  
 
- 검수자
  - 검수사항 등록 및 수정

</br>

## 4. 나의 역할
- 마이페이지 기능 구현 `Controller : laundry/controller/MypageController.java` : [코드확인](https://github.com/seungchan5/Laundry_project/blob/main/src/main/java/aug/laundry/controller/MypageController_osc.java)
  - 회원 쿠폰리스트 조회
  - 회원 보유 포인트 및 포인트 사용내역 조회
  - 회원 주소, 전화번호, 비밀번호 변경
  - 회원탈퇴
  - 카카오톡 공유를 통한 친구 초대 `laundry/src/resources/js/project_invire.js` : [코드확인](https://github.com/seungchan5/Laundry_project/blob/main/src/main/resources/static/js/project_invite.js)

  </br>

## 5. ERD 설계

![ERD](https://github.com/seungchan5/Laundry_project/assets/126455161/86c2ec9d-db42-415c-a0a7-ee37e6c041d8)

