# ☕️ 시크릿 오더(Scret Order)

## 📄 프로젝트 소개

- 주제 : 카페 오더 앱 기능 구현 개인 프로젝트
- 기간 : 2024.04.10 ~ 2024.04.18
- 프로젝트 인원 : 1명
- Language : Java
- Database : Oracle SQL

<br>

## 🛠️ 주요 기능

<details open>
<summary>1. 회원가입</summary>

- 아이디 중복 확인
- 가입 완료 하면 USERS 테이블에 DB 저장
- 비로그인 상태에서만 접근 가능

</details>
<details open>
<summary>2. 로그인/로그아웃</summary>

- 아이디, 비밀번호 유효성 검사
- 비로그인 상태에서는 로그인, 로그인 상태에서는 로그아웃 활성화

</details>
<details open>
<summary>3. 장바구니 / 주문</summary>

- 로그인 상태에서만 접근 가능
- 메뉴와 수량을 입력하면 CART 테이블에 DB 저장
- 주문 완료 하면 CART 테이블 비우고 ORDER 테이블에 DB 저장
- 주문 한 갯수 만큼 스탬프가 적립
- 결제 수단에서 스탬프를 선택하면 스탬프 10개 차감 후 결제

</details>
<details open>
<summary>4. 주문조회</summary>

- 로그인 상태에서만 접근 가능
- 회원의 주문 기록 조회 (주문날짜 순으로 정렬)

</details>

<br>

## 💡 구현방법

### 클래스 구성
- 추가중 ....

  

### DB 테이블 구성

![DBtable](https://github.com/devuoon/Secret0rder/assets/104570636/57494a25-1890-4010-93d8-97de17f9271c)

<br>

## 📽️ 데모 영상

<details>
<summary>회원가입</summary>

![회원가입](https://github.com/devuoon/Secret0rder/assets/104570636/1c764243-d125-4998-9ab4-54e18c2487c1)

</details>

<details>
<summary>로그인/로그아웃</summary>

- 로그인

  ![로그인](https://github.com/devuoon/Secret0rder/assets/104570636/be6eb879-1d6c-4b9a-b9ff-cdf39b310d55)

- 로그아웃

  ![로그아웃](https://github.com/devuoon/Secret0rder/assets/104570636/0c66b26c-388d-466e-872d-5b8711d88161)

</details>
<details>
<summary>장바구니 / 주문</summary>

- 장바구니 / 결제

  ![일반결제](https://github.com/devuoon/Secret0rder/assets/104570636/dce062a7-7167-4a33-902b-ace4ba75107b)

- 스탬프 결제
  
  ![스탬프 결제](https://github.com/devuoon/Secret0rder/assets/104570636/8a01dd69-07b0-4c87-9975-132731d4cca9)

</details>
<details>
<summary>주문조회</summary>

![주문내역](https://github.com/devuoon/Secret0rder/assets/104570636/e38f1a9f-9b3d-42a1-83c4-991d3f0a2cda)

</details>
