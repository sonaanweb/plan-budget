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
| **Category API** | GET         | `/api/categories`           | 등록 카테고리 목록|
| **Budgets API**  | POST        | `/api/budgets`              | 예산 등록      |
| **Budgets API**  | GET         | `/api/budgets`              | 예산 조회      |
| **Spends API**   | POST        | `/api/spends`            | 지출 등록      |
| **Spends API**   | PATCH       | `/api/spends/{spendId}`  | 지출 수정      |
| **Spends API**   | DELETE      | `/api/spends/{spendId}`  | 지출 삭제      |
| **Spends API**   | GET         | `/api/spends`            | 지출 리스트 조회  |
| **Spends API**   | GET         | `/api/spends/{spendId}`  | 지출 상세 조회   |
| **Spends API**   | GET         | `/api/spends/today`       | 오늘의 지출 상황 안내  |
| **Statistics API** | GET       | `/api/Statistics/weekly`  | 주간 지출 비교  |
| **Statistics API** | GET       | `/api/Statistics/monthly`  | 월간 지출 비교 |
| **Statistics API** | GET       | `/api/Statistics/budgets`  | 월간 사용량 통계 |

<details>
<summary><strong>ERD</strong></summary>
<img src=https://github.com/user-attachments/assets/8f8552be-2321-42c0-aefe-d9b0ffc974a9>
</details>

<details>
<summary><strong>Swagger</strong></summary>
<img src=https://github.com/user-attachments/assets/fc67b07e-a0dd-4bac-8679-2122570f2410>
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


### 3-1. API REFERENCE 📃
#### Members
<details>
  <summary>회원가입</summary>
  <br>
  아이디와 비밀번호를 입력한 회원가입
  
#### Request

| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| `account`      | `String`  | (Required) 계정   |
| `password`     | `String`  | (Required) 비밀번호 |

`POST /api/members/join`
```json
{
  "account": "account",
  "password": "1234",
}
```
#### Response
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
  <br>
  아이디와 비밀번호를 입력해 로그인합니다.<br>
  로그인 성공 시 accessToken, refreshToken 동시 발급됩니다.

#### Request

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
#### Response
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
  <br>
  유효한 refreshToken이 레디스 서버 내에 존재하면 accessToken을 재발급 받을 수 있습니다. 
  
#### Request  

| Field | Type | Description |  
|:---------------|:----------|:------------------------|  
| `RefreshToken` | `String` | (Required) refreshToken |  

`POST /api/members/reissue`  
```json  
{  
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."  
}  
```  
#### Response  
refreshToken은 유지, accessToken은 재발급  
```json  
{  
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50...",  
"refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQxIiwiaWF0IjoxNz...."  
}  
```  
</details>

#### Category
<details>
  <summary>카테고리 목록 조회</summary>
  <br>
  사용자는 init data로 들어간 열가지의 기본 카테고리 목록을 조회할 수 있습니다.
  
#### Response
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

#### Budgets

<details>
  <summary>예산 생성</summary>
  <br>
  사용자는 유효한 카테고리 내에서 년/월별 예산 생성을 할 수 있습니다.
  
#### Request

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
#### Response
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
  <br>
  사용자는 예산 총액과 카테고리별 예산을 조회할 수 있습니다.<br>
  Query Params 값이 없을 때는 현재 년, 월을 기준으로 조회됩니다. 
  <br><br>
  
| Query Params Field  | Type      | Description     |
|:---------------|:----------|:----------------|
| `year`   | `Integer`  | 조회 할 년도  |
| `month`     | `Integer`  | 조회 할 월 |

#### Response
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

#### Spends

<details>
  <summary>지출 생성</summary>
  <br>
  사용자는 카테고리별 지출을 생성할 수 있습니다.<br>
  지출 생성 시 `지출 합계에 포함` 여부를 선택할 수 있습니다. (기본 값 false = 포함)
  
#### Request

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

#### Response
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
  <br>
  사용자는 등록한 지출 정보를 모두 수정할 수 있습니다.
  
#### Request
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
#### Response
```json
200 OK
```

```text
존재하지 않는 지출 정보 404 NOT FOUND
```
</details>

<details>
  <summary>지출 삭제</summary>
  <br>
  사용자는 등록한 지출 정보를 삭제할 수 있습니다.
  
`DELETE /api/spends/{spendId}`

#### Response
```json
204 No Content
```
```text
존재하지 않는 지출 정보 404 NOT FOUND
```
</details>

<details>
  <summary>지출 목록 조회</summary>
  <br>
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

#### Response
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
  <br>
  사용자는 지출 상세 조회가 가능합니다
  <br><br>

#### Response
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
  <br>
  사용자는 오늘의 지출 내역을 알림으로 받을 수 있습니다. (Scheduled: 매일 오후 8시 실행) <br>
  오늘 사용한 총 지출액, 등록한 예산 범위 내 하루 추천 사용액, 총 위험도, 카테고리 별 추천 사용액과 위험도 등을 알려줍니다.
  <br>
  
#### Response
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
    ],
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

#### Statistics ... 작성중

<details>
  <summary>카테고리 목록</summary>
  <br>
  설명
  
#### Request
`POST /api/members/join`
```json
{
  
}
```
| Field          | Type      | Description     |
|:---------------|:----------|:----------------|
| ``      | `string`  | (Required) 계정   |
| ``     | `string`  | (Required) 비밀번호 |

#### Response
```text
OK
```

```text
BAD
```
</details>

---
### 4. 트러블 슈팅
- 발생했던 문제와 해결 방법에 대한 설명

### 5. 회고
- 프로젝트 진행 후 느낀 점이나 배운 점
