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
  - 마이페이지(보유 쿠폰 조회, 보유 포인트 조회, 개인 정보 변경, 친구 초대, 회원 탈퇴)

    <details>
    <summary><b>회원 기능 화면 펼치기</b></summary>
    <div markdown="1">
      
    -   **로그인 관련 화면** 
    ![로그인 관련](https://github.com/seungchan5/Laundry_project/assets/126455161/caffadfb-5818-422d-a906-a0664ead5932)
    
    -   **주문 관련 화면**
    ![주문1](https://github.com/seungchan5/Laundry_project/assets/126455161/eec34c43-4464-4dbf-b452-9a4d0d88e076)
    ![주문2](https://github.com/seungchan5/Laundry_project/assets/126455161/7c531450-2337-49f1-9548-36e90c5d9446)
    ![주문3](https://github.com/seungchan5/Laundry_project/assets/126455161/b30dd0a2-6d4a-4b07-a7a5-b216d827d969)
    
    -   **정기 구독권 관련 화면**
    ![세탁패스](https://github.com/seungchan5/Laundry_project/assets/126455161/ad20c380-d4cb-4193-91f9-5aa713289313)
    
    -   **마이페이지 관련 화면**
    ![마이페이지1](https://github.com/seungchan5/Laundry_project/assets/126455161/948609c1-2288-4ca5-a663-fca6c0d9024c)
    ![마이페이지2](https://github.com/seungchan5/Laundry_project/assets/126455161/d0ea591f-3b32-4167-84fd-29f890df486e)
    </div>
    </details>

  
- 라이더
  - 실시간 배달 주문 목록 조회
  - 티맵 API를 통한 위치 기반 거리 계산
  - 카카오 내비 API를 통한 경로 안내
  - 배달 현황 등록

    <details>
    <summary><b>라이더 기능 화면 펼치기</b></summary>
    <div markdown="1">
    
    -   **빠른 세탁 배달 관련 화면**
    ![빠른 세탁1](https://github.com/seungchan5/Laundry_project/assets/126455161/72724c2f-1e02-4022-b765-d6f3282332e1)
    ![빠른 세탁2](https://github.com/seungchan5/Laundry_project/assets/126455161/95b52121-792e-45ae-8b66-a412375c6fc3)
    
    -   **일반 세탁 배달 관련 화면**
    ![일반세탁1](https://github.com/seungchan5/Laundry_project/assets/126455161/2dd46eb6-06aa-45bf-9b14-a5815ea294bd)
    ![일반세탁2](https://github.com/seungchan5/Laundry_project/assets/126455161/e2b462f5-1a76-4980-8c84-57071bc889c1)
    
    </div>
    </details>
 
- 검수자
  - 검수사항 등록 및 수정
 
    <details>
    <summary><b>검수자 기능 화면 펼치기</b></summary>
    <div markdown="1">
    
    -   **검수 관련 화면**
    ![검수](https://github.com/seungchan5/Laundry_project/assets/126455161/a409b269-4901-45e6-aac6-8f890a53d085)
    
    
    </div>
    </details>

</br>

## 4. 나의 역할
- 마이페이지 기능 구현 `Controller : laundry/controller/MypageController.java` : [코드확인](https://github.com/seungchan5/Laundry_project/blob/main/src/main/java/aug/laundry/controller/MypageController_osc.java)
  - 회원 쿠폰리스트 조회
  - 회원 보유 포인트 및 포인트 사용내역 조회
  - 회원 주소, 전화번호, 비밀번호 변경
  - 회원탈퇴
  - 카카오톡 공유를 통한 친구 초대 `laundry/src/resources/js/project_invite.js` : [코드확인](https://github.com/seungchan5/Laundry_project/blob/main/src/main/resources/static/js/project_invite.js)

  </br>

## 5. ERD 설계

![ERD](https://github.com/seungchan5/Laundry_project/assets/126455161/86c2ec9d-db42-415c-a0a7-ee37e6c041d8)

