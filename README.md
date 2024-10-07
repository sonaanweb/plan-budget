# 💰 플랜비 (Plan-Budget)
예산 및 지출 관리 어플리케이션 플랜비

## 목차
- [1. 프로젝트 개요](#1-프로젝트-개요)
- [2. 프로젝트 관리](#2-프로젝트-관리)
- [3. 기술 문서](#3-기술-문서)
  - [3-1. API reference](#3-1-api-reference)
- [4. 트러블 슈팅](#4-트러블-슈팅)
- [5. 회고](#5-회고)

---

### 1. 프로젝트 개요
` 예산 지출 계획 플랜비와 함께 설계해봐요 ! 🔥 ` <br>
본 서비스는 개인 재무를 관리하고, 지출 추적에 도움을 주는 애플리케이션입니다. <br>
예산을 설정하고 설정한 예산 범위 내에서 지출을 모니터링하며 목표를 달성할 수 있도록 합니다.

#### 기술 스택
<div align=left> 
  <img src="https://img.shields.io/badge/java 17-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
</div>

<div align=left> 
  <img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/dbeaver-372923?style=for-the-badge&logo=dbeaver&logoColor=white">
</div>

<div align=left> 
  <img src="https://img.shields.io/badge/intellij IDEA-000085?style=for-the-badge&logo=intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
</div>

### 2. 프로젝트 관리
🗓️ 일정: 2024.09.12 ~ 2024.09.26
| 날짜 | 활동 |
| --- | --- |
| 2024.09.12 | 프로젝트 생성 |
| 2024.09.13 ~ 09.14 | 프로젝트 초기 세팅 |
| 2024.09.18 ~ 09.26 | 주요 기능 개발 |
| 2024.09.27 ~ 10.02 | 리팩토링 및 추가 기능 구현 |

</details>

<details>
<summary><strong>작업 사이클</strong></summary>

```
1. 이슈 생성
2. 브랜치 생성
3. 코드 작성
4. PR 생성
5. 기능 브랜치 PR push
6. main 브랜치로 Merge
```

</details>

<details>
<summary><strong>이슈 관리</strong></summary>
<img src=https://github.com/user-attachments/assets/7a7194f5-17f1-4632-9be1-c2efa7fa47a5>
</details>

<details>
<summary><strong>컨벤션</strong></summary>

- **Branch**
    - **전략**

      | Branch Type | Description |
      | --- | --- |
      | `main` | 개인 프로젝트이므로 개발 브랜치를 나누지 않고 진행. 기능 개발후 바로 merge |
      | `feature` | 개발할 branch, 기능 단위로 생성하기, 할 일 issue 등록 후 branch 생성 및 작업 |

    - **네이밍**
        - `{header}/#{issue number}`
        - 예) `feat/#1`

- **커밋 메시지 규칙**
    ```bash
    > [HEADER] : 기능 요약
    
    - [CHORE]: 내부 파일 수정
    - [FEAT] : 새로운 기능 구현
    - [ADD] : FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시
    - [FIX] : 코드 수정, 버그, 오류 해결
    - [DEL] : 쓸모없는 코드 삭제
    - [DOCS] : README나 WIKI 등의 문서 개정
    - [MOVE] : 프로젝트 내 파일이나 코드의 이동
    - [RENAME] : 파일 이름의 변경
    - [MERGE]: 다른 브렌치를 merge하는 경우
    - [STYLE] : 코드가 아닌 스타일 변경을 하는 경우
    - [INIT] : Initial commit을 하는 경우
    - [REFACTOR] : 로직은 변경 없는 클린 코드를 위한 코드 수정
    
    ex) [FEAT] 게시글 목록 조회 API 구현
    ex) [FIX] 내가 작성하지 않은 리뷰 볼 수 있는 버그 해결
    ```
</details>

---

### 3. 기술 문서

<strong>API 명세서</strong>

| API Type         | Http Method | URL                         | Description |
|------------------|-------------|-----------------------------|---------------- |
| **Members API**  | POST        | `/api/members/reissue`      | 엑세스토큰 재발급| 
| **Members API**  | POST        | `/api/members/join`         | 회원가입        |
| **Members API**  | POST        | `/api/members/login`        | 로그인          |
| **Members API**  | POST        | `/api/members/logout`       | 로그아웃       |
| **Category API** | GET         | `/api/categories`           | 등록 카테고리 목록|
| **Budgets API**  | POST        | `/api/budgets`              | 예산 등록      |
| **Budgets API**  | GET         | `/api/budgets`              | 예산 조회      |
| **Spends API**   | POST        | `/api/spends`            | 지출 등록      |
| **Spends API**   | PATCH       | `/api/spends/{spendId}`  | 지출 수정      |
| **Spends API**   | DELETE      | `/api/spends/{spendId}`  | 지출 삭제      |
| **Spends API**   | GET         | `/api/spends`            | 지출 리스트 조회  |
| **Spends API**   | GET         | `/api/spends/{spendId}`  | 지출 상세 조회   |
| **Spends API**   | GET         | `/api/spends/today`       | 오늘의 지출 상황 안내  |
| **Statistics API** | GET       | `/api/statistics/weekly`  | 주간 지출 비교 통계 |
| **Statistics API** | GET       | `/api/statistics/monthly`  | 월간 지출 비교 통계 |
| **Statistics API** | GET       | `/api/statistics/budgets`  | 월간 사용량 통계 |

<details>
<summary><strong>ERD</strong></summary>
<img src=https://github.com/user-attachments/assets/199c7559-8887-4611-85c5-96bbd160f665>
</details>

<details>
<summary><strong>Swagger</strong></summary>
<img src=https://github.com/user-attachments/assets/7a1f90c7-1455-476c-9edf-813fdf5aaf11>
</details>

<details>
<summary><strong>디렉토리 구조</strong></summary>
  
```plaintext
├─main
│  ├─generated
│  │  └─com
│  │      └─project
│  │          └─planb
│  │              └─domain
│  │                  ├─budget
│  │                  │  └─entity
│  │                  │          QBudget.java
│  │                  │          
│  │                  ├─category
│  │                  │  └─entity
│  │                  │          QCategory.java
│  │                  │          
│  │                  ├─member
│  │                  │  └─entity
│  │                  │          QMember.java
│  │                  │          
│  │                  └─spend
│  │                      └─entity
│  │                              QSpend.java
│  │                              
│  ├─java
│  │  └─com
│  │      └─project
│  │          └─planb
│  │              │  PlanbApplication.java
│  │              │  
│  │              ├─common
│  │              │  ├─config
│  │              │  │      QueryDslConfig.java
│  │              │  │      RedisConfig.java
│  │              │  │      SecurityConfig.java
│  │              │  │      SwaggerConfig.java
│  │              │  │      
│  │              │  ├─exception
│  │              │  │      CustomException.java
│  │              │  │      ErrorCode.java
│  │              │  │      ErrorResponse.java
│  │              │  │      GlobalExceptionHandler.java
│  │              │  │      
│  │              │  ├─security
│  │              │  │  ├─details
│  │              │  │  │      PrincipalDetails.java
│  │              │  │  │      PrincipalDetailsService.java
│  │              │  │  │      
│  │              │  │  ├─dto
│  │              │  │  │      LogoutRequestDto.java
│  │              │  │  │      RefreshToken.java
│  │              │  │  │      TokenRequestDto.java
│  │              │  │  │      TokenResDto.java
│  │              │  │  │      
│  │              │  │  ├─jwt
│  │              │  │  │  │  JwtTokenProvider.java
│  │              │  │  │  │  
│  │              │  │  │  └─filter
│  │              │  │  │          JwtAuthenticationFilter.java
│  │              │  │  │          
│  │              │  │  └─repository
│  │              │  │          RefreshTokenRepository.java
│  │              │  │          
│  │              │  └─utils
│  │              │          NotificationUtils.java
│  │              │          
│  │              ├─domain
│  │              │  ├─budget
│  │              │  │  ├─controller
│  │              │  │  │      BudgetController.java
│  │              │  │  │      
│  │              │  │  ├─dto
│  │              │  │  │  ├─req
│  │              │  │  │  │      BudgetCreateReqDto.java
│  │              │  │  │  │      BudgetPeriodReqDto.java
│  │              │  │  │  │      
│  │              │  │  │  └─res
│  │              │  │  │          BudgetCreateResDto.java
│  │              │  │  │          BudgetResDto.java
│  │              │  │  │          
│  │              │  │  ├─entity
│  │              │  │  │      Budget.java
│  │              │  │  │      
│  │              │  │  ├─repository
│  │              │  │  │      BudgetRepository.java
│  │              │  │  │      
│  │              │  │  └─service
│  │              │  │          BudgetService.java
│  │              │  │          
│  │              │  ├─category
│  │              │  │  ├─controller
│  │              │  │  │      CategoryController.java
│  │              │  │  │      
│  │              │  │  ├─dto
│  │              │  │  │      CategoryResDto.java
│  │              │  │  │      
│  │              │  │  ├─entity
│  │              │  │  │      Category.java
│  │              │  │  │      
│  │              │  │  ├─enums
│  │              │  │  │      CategoryType.java
│  │              │  │  │      
│  │              │  │  ├─init
│  │              │  │  │      CategoryInit.java
│  │              │  │  │      
│  │              │  │  ├─repository
│  │              │  │  │      CategoryRepository.java
│  │              │  │  │      
│  │              │  │  └─service
│  │              │  │          CategoryService.java
│  │              │  │          
│  │              │  ├─member
│  │              │  │  ├─controller
│  │              │  │  │      MemberController.java
│  │              │  │  │      
│  │              │  │  ├─dto
│  │              │  │  │      MemberJoinReqDto.java
│  │              │  │  │      MemberLoginReqDto.java
│  │              │  │  │      
│  │              │  │  ├─entity
│  │              │  │  │      Member.java
│  │              │  │  │      
│  │              │  │  ├─repository
│  │              │  │  │      MemberRepository.java
│  │              │  │  │      
│  │              │  │  └─service
│  │              │  │          MemberService.java
│  │              │  │          
│  │              │  └─spend
│  │              │      ├─controller
│  │              │      │      SpendController.java
│  │              │      │      
│  │              │      ├─dto
│  │              │      │  ├─req
│  │              │      │  │      SpendReqDto.java
│  │              │      │  │      
│  │              │      │  └─res
│  │              │      │          SpendDetailDto.java
│  │              │      │          SpendResDto.java
│  │              │      │          TodaySpendDto.java
│  │              │      │          
│  │              │      ├─entity
│  │              │      │      Spend.java
│  │              │      │      
│  │              │      ├─repository
│  │              │      │  │  SpendQRepository.java
│  │              │      │  │  SpendRepository.java
│  │              │      │  │  
│  │              │      │  └─impl
│  │              │      │          SpendQRepositoryImpl.java
│  │              │      │          
│  │              │      └─service
│  │              │              SpendService.java
│  │              │              
│  │              └─feature
│  │                  ├─controller
│  │                  │      ConsultingController.java
│  │                  │      StatisticsController.java
│  │                  │      
│  │                  ├─dto
│  │                  │  ├─req
│  │                  │  │      StatisticsPeriodReqDto.java
│  │                  │  │      
│  │                  │  └─res
│  │                  │          BudgetStatisticsDto.java
│  │                  │          StatisticsDto.java
│  │                  │          
│  │                  └─service
│  │                          ConsultingService.java
│  │                          StatisticsService.java
│  │                          
│  └─resources
│      │  application.properties
│      │  application.yml
│      │  
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─project
                └─planb
                    │  PlanbApplicationTests.java
                    │  
                    └─service
                            BudgetServiceTest.java
                            CategoryServiceTest.java
                            StatisticsServiceTest.java

```

</details>


### 📃3-1. API REFERENCE
#### Members 👤
<details>
  <summary>회원가입</summary>
  
  아이디와 비밀번호를 입력한 회원가입<br>
  알림설정 컬럼은 지출 알림 기능에서 사용됩니다.<br>
  (* 기본 값 false, true == 알림 전송)
  
<strong>Request</strong>

| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| `account`      | `String`  | (Required) 계정   |
| `password`     | `String`  | (Required) 비밀번호 |
| `notificationEnabled`     | `Boolean`  | 알림 설정 |

`POST /api/members/join`
```json
{
  "account": "account",
  "password": "1234",
}
```
<strong>Response</strong>
```text
200 OK
회원가입이 성공적으로 완료되었습니다.
```

```text
공백 입력 400 Bad Request 
중복 아이디 입력 409 Conflict
```
</details>
<details>
  <summary>로그인</summary>

  아이디와 비밀번호를 입력해 로그인합니다.<br>
  로그인 성공 시 accessToken, refreshToken 동시 발급됩니다.

<strong>Request</strong>

| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| `account`      | `String`  | (Required) 계정   |
| `password`     | `String`  | (Required) 비밀번호 |

`POST /api/members/login`
```json
{
  "account": "account",
  "password": "1234",
}
```
<strong>Response</strong>
```text
200 OK
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."
}
```

```text
존재하지 않는 계정 404 NOT FOUND
로그인 실패 401 Unauthorized
```
</details>

<details>
  <summary>토큰 재발급</summary>

  유효한 refreshToken이 레디스 서버 내에 존재하면 accessToken을 재발급 받을 수 있습니다. 
  
<strong>Request</strong>  

| Field | Type | Description |  
|:---------------|:----------|:------------------------|  
| `RefreshToken` | `String` | (Required) refreshToken |  

`POST /api/members/reissue`  
```json  
{  
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."  
}  
```  
<strong>Response</strong>  
refreshToken은 유지, accessToken은 재발급  
```json  
{  
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50...",  
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."  
}  
```  
</details>

<details>
  <summary>로그아웃</summary>

  사용자 로그아웃 시 엑세스 토큰(redis 블랙리스트에 추가), 리프레시 토큰이 무효화됩니다.
  
<strong>Request</strong>  

| Field | Type | Description |  
|:---------------|:----------|:------------------------|
| `AccessToken` | `String` | (Required) accessToken | 
| `RefreshToken` | `String` | (Required) refreshToken |  

`POST /api/members/logout`  
```json  
{
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50...",
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."  
}  
```  
<strong>Response</strong>  

```text
로그아웃이 성공적으로 완료되었습니다.
```
</details>


#### Category 📂
<details>
  <summary>카테고리 목록 조회</summary>

  사용자는 init data로 들어간 열가지의 기본 카테고리 목록을 조회할 수 있습니다.
  
<strong>Response</strong>
`GET /api/categories`
```json
[
    {
        "id": 1,
        "categoryName": "식비"
    },
    {
        "id": 2,
        "categoryName": "교통비"
    },
    {
        "id": 3,
        "categoryName": "간식"
    },...
```
</details>

#### Budgets 💰

<details>
  <summary>예산 생성</summary>

  사용자는 유효한 카테고리 내에서 년/월별 예산 생성을 할 수 있습니다.
  
<strong>Request</strong>

| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| `categoryId`   | `Long`  | (Required) 카테고리 id값  |
| `amount`     | `Integer`  | (Required) 예산 총액 0이상의 값 |
| `year`     | `Integer`  | (Required) 년도 |
| `month`     | `int`  | (Required) 1~12월 내 범위 |

`POST /api/budgets`
```json
{
  "categoryId": "1",
  "amount": 200000,
  "year": 2024,
  "month": 11
}
```
<strong>Response</strong>
```json
{
    "id": 34,
    "categoryName": "식비",
    "year": 2024,
    "month": 11,
    "amount": 200000
}
```
```text
년/월 중복 카테고리 등록 404 NOT FOUND
카테고리 미지정, 필드 값 예외 400 BAD Request
```
</details>

<details>
  <summary>예산 조회</summary>
  
  사용자는 예산 총액과 카테고리별 예산을 조회할 수 있습니다.<br>
  Query Params 값이 없을 때는 현재 년, 월을 기준으로 조회됩니다. 
  <br><br>
  
| Query Params Field  | Type      | Description     |
|:---------------|:----------|:----------------|
| `year`   | `Integer`  | 조회 할 년도  |
| `month`     | `Integer`  | 조회 할 월 |

<strong>Response</strong>
`GET /api/budgets` ( 2024-10 Data.now )
```json
{
    "totalAmount": 200000, --- 등록 예산 총액
    "budgets": [ --- 카테고리 별 예산 목록
        {
            "id": 33,
            "categoryName": "식비",
            "year": 2024,
            "month": 10,
            "amount": 200000
        }
    ]
}
```

`GET /api/budgets?year=2024&month=8`
```json
{
    "totalAmount": 1160000,
    "budgets": [
        {
            "id": 15,
            "categoryName": "간식",
            "year": 2024,
            "month": 8,
            "amount": 50000
        },
        {
            "id": 16,
            "categoryName": "주거비",
            "year": 2024,
            "month": 8,
            "amount": 600000
        },....
```
</details>

#### Spends 💸

<details>
  <summary>지출 생성</summary>

  사용자는 카테고리별 지출을 생성할 수 있습니다.<br>
  지출 생성 시 `지출 합계에 포함` 여부를 선택할 수 있습니다. (기본 값 false = 포함)
  
<strong>Request</strong>

| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| `categoryId`      | `Long`  | (Required) 카테고리 id   |
| `amount`     | `Integer`  | 지출액 - 0이상 |
| `memo`     | `string`  | 메모 |
| `spendAt`     | `LocalDate`  | (Required) 날짜 |
| `isExcludedSum`    | `Boolean`  | 지출 합 유무|

`POST /api/spends`

```json
{
    "categoryId": 2,
    "amount": 7000,
    "memo": "택시비",
    "spendAt": "2024-10-02"
    "isExcludedSum" : false & true
}
```

<strong>Response</strong>
```json
{
    "categoryId": 2,
    "amount": 7000,
    "memo": "택시비",
    "spendAt": "2024-10-02",
    "isExcludedSum": false
}
```

```text
필드 값 예외 400 BAD Request
```
</details>

<details>
  <summary>지출 수정</summary>

  사용자는 등록한 지출 정보를 모두 수정할 수 있습니다.
  
<strong>Request</strong>
`PATCH /api/spends/{spendId}`
```json
{
    "categoryId": 2,
    "amount": 8000, --- 변경
    "memo": "택시비",
    "spendAt": "2024-10-02"
    "isExcludedSum" : true --- 변경
}

```
<strong>Response</strong>
```json
200 OK
```

```text
존재하지 않는 지출 정보 404 NOT FOUND
```
</details>

<details>
  <summary>지출 삭제</summary>

  사용자는 등록한 지출 정보를 삭제할 수 있습니다.
  
`DELETE /api/spends/{spendId}`

<strong>Response</strong>
```json
204 No Content
```
```text
존재하지 않는 지출 정보 404 NOT FOUND
```
</details>

<details>
  <summary>지출 목록 조회</summary>

  사용자는 등록한 지출 정보 동적 조회가 가능합니다.<br>
  필수적으로 ` 기간 `으로 조회하며 (`기간 미입력 시 현재 년/월의 1일부터 계산`), <br>
  조회된 내용의 모든 지출 합계와 카테고리별 지출 합계, 카테고리 별 지출 현황을 확인할 수 있습니다.<br>
  특정 카테고리 조회, 기간 내 최소(min) 최대(max) 금액 범위의 조회 또한 가능합니다. <br><br>
  
| Query Params Field  | Type      | Description     |
|:---------------|:----------|:----------------|
| `startDate`   | `LocalDate`  | 조회 시작 기간  |
| `endDate`     | `LocalDate`  | 조회 끝 기간 |
| `categoryId`     | `Long`  | 카테고리 ID |
| `minAmount`     | `Integer`  | 최소 금액 |
| `maxAmount`     | `Integer`  | 최대 금액 |

<strong>Response</strong>
`GET /api/spends?startDate=2024-08-01&endDate=2024-08-20`
```json
{
    "totalAmount": 347000, --- 지출 총합
    "categoryAmounts": { --- 카테고리 별 사용 금액
        "1": 15000,
        "2": 7000,
        "3": 5000,
        "4": 300000,
        "5": 20000
    },
    "spendList": [ --- 지출 목록
        {
            "id": 23,
            "spendAt": "2024-08-20",
            "categoryId": 5,
            "amount": 20000,
            "memo": "책 구입",
            "isExcludedSum": false
        },....
}

```

`GET /api/spends?startDate=2024-08-01&endDate=2024-08-20&categoryId=1&minAmount=10000&maxAmount=20000`
```json
{
    "totalAmount": 15000,
    "categoryAmounts": {
        "1": 15000
    },
    "spendList": [
        {
            "id": 19,
            "spendAt": "2024-08-01",
            "categoryId": 1,
            "amount": 15000,
            "memo": "점심 식사",
            "isExcludedSum": false
        }
    ]
}
```
</details>

<details>
  <summary>지출 상세 조회</summary>

  사용자는 지출 상세 조회가 가능합니다

<strong>Response</strong>
`GET /api/spends/21`
```json
{
    "id": 21,
    "spendAt": "2024-08-10",
    "categoryId": 3,
    "categoryName": "간식",
    "amount": 5000,
    "memo": "커피",
    "isExcludedSum": false
}
```

```text
지출 정보 존재하지 않을 시 404 NOT Found
```
</details>

<details>
  <summary>오늘의 지출</summary>

  사용자는 오늘의 지출 내역을 알림으로 받을 수 있습니다. (Scheduled: 매일 오후 8시 실행 - 알림설정 허용한 사용자에게만 발송) <br>
  오늘 사용한 총 지출액, 등록한 예산 범위 내 하루 추천 사용액, 총 위험도, 카테고리 별 추천 사용액과 위험도 등을 알려줍니다.<br>
  등록 된 지출 중 `비교 데이터` ( 지난 달, 지난 주 예산으로 등록된 카테고리 별 지출 )가 없을 시 신규데이터로 판단하여 증가율 100%로 설정<br>
  예산에 등록되지 않은 카테고리 지출은 별도로 위험도를 알려주지 않고, 등록되지 않은 카테고리임을 알려줍니다.
  <br>
  
<strong>Response</strong>
`GET /api/spends/today`

```json
{
    "totalSpentAmount": 7000, --- 오늘 사용한 지출
    "recommendedAmount": 1613, --- (등록 예산 / 일수) 하루 지출 추천 사용액
    "totalRisk": 434.0, --- 위험도
    "categories": [ --- 카테고리 별
        {
            "categoryName": "교통비",
            "todayRecommendedAmount": 1613, --- 카테고리 별 추천 사용액
            "spentAmount": 7000,
            "risk": 434.0
        }
    ],...
--- 설정하지 않은 카테고리가 있으면
        {
            "categoryName": "취미/여가",
            "todayRecommendedAmount": 0,
            "spentAmount": 15000,
            "risk": 0.0
        }
    ],
    "message": null,
    "unBudgetCategories": [
        "취미/여가"
    ]
}
```
```
안녕하세요, wanted1님! 오늘의 지출 정보입니다:
총 지출: 7000원
추천 지출: 1613원
위험도: 434.00%
카테고리별 지출: 교통비: 7000원 (위험도: 434.00%)
```

```text
지출 정보 없을 시 : 지출 데이터가 없습니다.
```
</details>

#### Statistics 📊

<details>
  <summary>월간 예산 통계</summary>

  사용자는 월간 예산 지출 사용량 통계를 확인할 수 있습니다.<br>
  조회 할 년/월을 입력하지 않을 시 현재 년/월이 조회됩니다. <br><br>

| Query Params Field  | Type      | Description     |
|:---------------|:----------|:----------------|
| `year`   | `Integer`  | 조회 할 년도  |
| `month`     | `Integer`  | 조회 할 월 |
  
<strong>Response</strong>
`GET /api/statistics/budgets?year=2024&month=8`
```json
{
    "totalBudget": 1160000, --- `8월`에 설정한 예산
    "remainingBudget": 698000, --- 남은 예산
    "usagePercentage": 39.83, --- 예산 사용 비율
    "categoryUsages": [ --- 카테고리 별 계
        {
            "categoryName": "간식",
            "spentAmount": 5000,
            "budgetAmount": 50000,
            "usagePercentage": 10.0
        },
        {
            "categoryName": "주거비",
            "spentAmount": 300000,
            "budgetAmount": 600000,
            "usagePercentage": 50.0
        },
        {
            "categoryName": "교육/학습",
            "spentAmount": 20000,
            "budgetAmount": 200000,
            "usagePercentage": 10.0
        },
        {
            "categoryName": "쇼핑",
            "spentAmount": 30000,
            "budgetAmount": 100000,
            "usagePercentage": 30.0
        },
        {
            "categoryName": "의료/건강",
            "spentAmount": 15000,
            "budgetAmount": 70000,
            "usagePercentage": 21.43
        },
        {
            "categoryName": "취미/여가",
            "spentAmount": 20000,
            "budgetAmount": 60000,
            "usagePercentage": 33.33
        },
        {
            "categoryName": "공과금",
            "spentAmount": 50000,
            "budgetAmount": 50000,
            "usagePercentage": 100.0
        },
        {
            "categoryName": "기타",
            "spentAmount": 0,
            "budgetAmount": 30000,
            "usagePercentage": 0.0
        }
    ]
}
```
</details>

<details>
  <summary>주간 지출 통계</summary>

  사용자는 `지난 주`와 `이번 주`의 비교 지출 통계를 확인할 수 있습니다.
  지난주 총 사용금액, 이번주 총 사용금액, 증감 비율을 포함해 카테고리별 통계도 확인 가능합니다.
  
<strong>Response</strong>
`GET /api/statistics/weekly
```json
{
    "lastAmount": 62000, --- 지난 주 지출
    "currentAmount": 67000, --- 이번 주 지출
    "increaseRate": 8.0, --- 지난 주 지출에 비한 증가율
    "categories": [ --- 카테고리 별
        {
            "categoryName": "간식",
            "lastAmount": 0,
            "currentAmount": 0,
            "increaseRate": 0.0
        },
        {
            "categoryName": "공과금",
            "lastAmount": 40000, --- 지난 주 지출
            "currentAmount": 60000, --- 이번 주 지출
            "increaseRate": 50.0 --- 지난 주 지출에 비한 증가율
        },
```
</details>

<details>
  <summary>월간 지출 통계</summary>
  
  사용자는 주간 지출통계와 같이 `지난 달`과 `이번 달`의 비교 지출 통계를 확인할 수 있습니다.<br>
  (* 이번 달 `오늘` + 지난 달 `오늘`까지의 통계)
  
<strong>Response</strong>
`GET /api/statistics/monthly`
```json
{
    "lastAmount": 26000, --- 지난 달 사용 금액
    "currentAmount": 7000, --- 이번 달 사용 금액
    "increaseRate": -73.0, --- 증감 비율
    "categories": [ --- 카테고리 별
        {
            "categoryName": "교통비",
            "lastAmount": 8000,
            "currentAmount": 7000,
            "increaseRate": -12.0
        },
        {
            "categoryName": "식비",
            "lastAmount": 18000,
            "currentAmount": 0,
            "increaseRate": -100.0
        },...
```
</details>

---
### 4. 트러블 슈팅
<details>
  <summary> int와 Integer</summary>
  테스트 중 날짜 값에 ""와 같은 공백이 들어가는 걸 확인했습니다. <br>
  현재 프로젝트에서는 날짜 포맷 클래스를 사용하지 않고 Year / Month를 따로 받아오고 있는데, <br> 데이터 타입에 대해 놓친 부분이 있어서 생긴 결점이었습니다.<br>
  int는 기본타입으로 빈문자열을 0으로 치환하여 허용된 것이기 때문에 참조타입 Integer로 바꾸어주어 해결했습니다.(Notnull 검증)
</details>
<details>
  <summary> 토큰 유효시간 에러</summary>
  JWT 토큰 생성 시 만료 시간을 설정하는 부분에서 오류를 수정했습니다. <br>
기존 코드에서는 expiration(new Date(System.currentTimeMillis() + expirationTime))를 사용하여 만료 시간을 설정했는데 이 부분이 `밀리초단위`로 계산되어 예상시간과 다른 걸 확인했습니다.<br>
Instant 클래스를 사용하여 현재 시각을 가져온 뒤, expirationTime을 초 단위로 더하여 만료 시각을 계산하는 방법으로 수정하였습니다.
</details>

### 5. 회고
이번 개인 프로젝트를 진행하며 사용해보지 않았던 기술에 대한 두려움을 덜어낼 수 있었습니다.
스프링 시큐리티나 QueryDsl과 같은 기술을 사용해보며 개인적으로 부족한 점도 많았고, 어려운 점 또한 많았지만 발전 가능성을 향해 한발자국 더 나아간 것 같습니다.
<details>
  <summary> 사용자에게 유용할 통계는 무엇이 있을까?</summary>
  🪄`N요일` 지출 비교 통계를 주간통계로 변경하였습니다.<br>
  데이터가 없을 시 N요일만을 비교해서 통계내주는 것 보단 주간 통계로 변경하는 것이
  유용성을 더 높일 수 있지 않을까하여 초기 요구사항에서 수정을 거쳤습니다.<br><br>
  🪄월별 예산 지출 통계 API를 새로 추가하였습니다. <br>
  실제 서비스되고 있는 애플리케이션들을 참고하여, 월별로 설정한 예산, 남은 예산, 사용 비율을 직관적으로 확인할 수 있도록 구현하였습니다.<br>
  이러한 고민들을 거치며 사용자 친화적인 어플리케이션에 대해 더 깊게 생각해볼 수 있었습니다.
</details>
<details>
  <summary> 기본 카테고리 구현 방식</summary>
  고정된 카테고리를 추가하기 위해 초기화 데이터를 Java 코드로 작성하기로 결정했습니다. <br>
  파일 업로드 시 내부파일 오류로 인해 프로젝트가 실행되지 않은 경험이 있어 이를 방지하기 위해 코드 구현 방식을 선택했습니다.<br>
  또한 초기화 시 두 가지 방법을 비교하였습니다. <br>
  첫 번째는 List를 사용하여 존재하지 않는 카테고리를 모아 saveAll로 저장하는 방식이고, <br>
  두 번째는 Set을 활용하여 이미 존재하는 카테고리 이름을 데이터베이스에서 가져와 중복 확인을 최소화하는 방식입니다.<br>
  이 프로젝트에서는 두 번째 방식을 선택하였는데, List 방식은 각 카테고리의 존재 여부를 체크하기 위해 여러 번의 데이터베이스 호출을 발생시키기 때문입니다.<br>
  이를 줄이기 위해 Set을 활용하여 데이터베이스 호출을 최소화하였습니다.
</details>
<details>
  <summary> 날짜 포맷에 대한 고민</summary>
  Year / Month를 따로 받아오는 현재 방식에 대해 고민하였습니다.<br>
  프론트에서 날짜 UI를 사용할 때 데이터를 일관되게 처리할 방법을 찾아보았습니다.<br>
  이 과정에서 조회 시 사용했던 JsonFormat 방식과 YearMonth를 활용해 입력 받는 방법을 알게 되었습니다.<br>
  이번 프로젝트에서 이 부분은 리팩토링을 진행하지 않았지만, 협업에 대한 사고가 확장되는 시간이었습니다.
</details>
<details>
  <summary> 스프링 시큐리티 도입 회고..</summary>
  시큐리티에 대해서는 아쉬움이 많이 남은 프로젝트였습니다.<br>
  기능 구현을 우선으로 하다보니 사용자 API와 토큰관련 클래스 분리나, 핸들러 사용 등 유용한 기능들을 많이 사용하지 못한 것이 마음에 남습니다.<br>
  시큐리티 관련 코드는 꾸준히 공부하면서 디벨롭 해나가는 게 좋을 것 같습니다.
</details>
