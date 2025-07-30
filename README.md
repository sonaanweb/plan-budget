# 💰 플랜비 (Plan-Budget)
예산 및 지출 관리 어플리케이션 플랜비

## 목차
- [1. 프로젝트 개요](#1-프로젝트-개요)
- [2. 프로젝트 관리](#2-프로젝트-관리)
- [3. 기능 구현](#3-기능-구현)
- [4. 기술 문서](#4-기술-문서)
- [5. 트러블 슈팅](#5-트러블-슈팅)
- [6. 회고](#6-회고)

<br>

## 1. 프로젝트 개요
` 예산 지출 계획 플랜비와 함께 설계해봐요 ! 🔥 ` <br>
본 서비스는 개인 재무를 관리하고, 지출 추적에 도움을 주는 애플리케이션입니다. <br>
예산을 설정하고 설정한 예산 범위 내에서 지출을 모니터링하며 목표를 달성할 수 있도록 합니다.

### 기술 스택
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

<br>

## 2. 프로젝트 관리
🗓️ 일정: 2024.09.12 ~ 2024.10.07
| 날짜 | 활동 |
| --- | --- |
| 2024.09.12 | 프로젝트 생성 |
| 2024.09.13 ~ 09.14 | 프로젝트 초기 세팅 |
| 2024.09.18 ~ 09.26 | 주요 기능 개발 |
| 2024.09.27 ~ 10.07 | 리팩토링 및 추가 기능 구현 |

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

<br>

## 3. 기능 구현
### 회원 가입 및 로그인/로그아웃
- 아이디와 비밀번호를 입력한 회원가입. 회원가입 시 지출 알림 기능에서 사용 가능한 알림설정 가능 (추후 마케팅, 이벤트 알림 등에 대한 확장 용이성)
- 아이디, 비밀번호 입력 로그인 -> 엑세스/리프레시 토큰 발급
- 사용자 로그아웃 시 토큰 무효화

### 카테고리
- 10가지의 기본 카테고리 제공(`식비, 교통비, 간식, 주거비, 교육/학습, 쇼핑, 의료/건강, 취미/여가, 공과금, 기타`)

### 예산
- 사용자는 유효한 카테고리 내에서 `년/월별` 예산 생성 가능(같은 년/월 중복 카테고리 등록 불가, 카테고리 미지정 시 필드값 예외 오류 띄움)
- 설정한 예산 총액과 카테고리별 예산 조회 -> `년/월 미지정 시 현재 날짜 기준 조회`

### 지출
- 지출 생성, 수정, 삭제, 조회, 상세 조회, 오늘의 지출 확인 가능
- `지출 생성`: 카테고리별 지출 생성. 생성 시 메모 + 지출 합계에 포함 여부 선택 가능
- `지출 목록 조회`: 등록한 지출 정보 동적 조회(`모든 지출 합계, 카테고리별 지출 합계, 지출 현황, 특정 카테고리 조회, 기간 내 최소 최대 금액 범위 조회`) 가능. <br>
기간으로 조회하며 미입력 시 현재 년/월의 1일부터 자동 계산.
- `오늘의 지출` : 알림을 허가한 사용자에게 오늘의 지출 알림을 보내어(매일 오후 8시)<br>
총 지출액, 등록한 예산 범위 or 카테고리 별 추천 사용액, 위험도를 알려줍니다. <br>
혼란 최소화를 위해 `등록 된 지출 중 비교데이터(지난 달, 지난 주 예산 별 지출)가 없을 시 신규 데이터로 판단하여 증가율 100% 설정`. <br>
예산에 등록되지 않은 카테고리 지출은 별도로 위험도를 알려주지 않고 등록되지 않은 카테고리임을 알려주도록 개발

### 통계
- 사용자는 지정 년/월 월간 예산 지출 사용량 통계를 확인할 수 있음. (`n`월에 설정한 예산, 남은 예산, 사용 비율, 카테고리 별 계산)
- `주간 지출 통계`: 사용자의 지난 주와 이번 주 지출 비교 통계를 통해 소비 변화 확인(`지난 주 총 사용금액, 이번 주 총 사용금액, 증감 비율, 카테고리별 통계`)
- `월간 지출 통계`: 주간과 같이 지난 달과 이번 달의 비교 지출 통계 확인이 가능함 (* 7/18일 조회일 시 6/1 ~ 6/18일과 7/1 ~ 7/18일 비교)

<br>

## 4. 기술 문서

### 📝 API 명세서

[➡️➡️➡️ API 명세 상세보기]()

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

### 📝ERD
<img src=https://github.com/user-attachments/assets/199c7559-8887-4611-85c5-96bbd160f665>

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

<br>

## 5. 트러블 슈팅
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

<br>

## 6. 회고
이번 개인 프로젝트를 진행하며 스프링 시큐리티와 QueryDsl와 같은 직접 사용해보지 않았던 기술에 대한 두려움을 덜어낼 수 있었습니다.
개인적으로 부족한 점도 많았고, 어려운 점 또한 많았지만 개발자로서의 한층 더 성장했다 느낀 프로젝트였습니다.

### (+) 프로젝트 주요 개선점 회고
<strong> ** 사용자 경험 중심 리팩토링 ** </strong>
<strong>사용자에게 유용할 통계는 무엇이 있을까?</strong>

- **🪄 `N요일` 지출 비교 통계를 `주간통계`로 변경** <br>
  데이터가 없을 시 N요일만을 비교해서 통계내주는 것 보단 주간 통계로 변경하는 것이
  유용성을 더 높일 수 있지 않을까하여 초기 요구사항에서 수정을 거쳤습니다.<br>
- **🪄 월별 예산 지출 통계 API 추가.** <br>
  실제 서비스되고 있는 애플리케이션들을 참고하여, 월별로 설정한 예산, 남은 예산, 사용 비율을 직관적으로 확인할 수 있도록 구현하였습니다.<br>
  이러한 고민들을 거치며 사용자 친화적인 어플리케이션에 대해 더 깊게 생각해볼 수 있었습니다. <br>
  [코드 바로가기](https://github.com/sonaanweb/plan-budget/pull/29)
<br>

<strong> 그 외 기타 구현 내용 </strong>

- **🗂️ 기본 카테고리 구현 방식** <br>
  고정된 카테고리를 추가하기 위해 초기화 데이터를 Java 코드로 작성하기로 결정했습니다. <br>
  파일 업로드 시 내부파일 오류로 인해 프로젝트가 실행되지 않은 경험이 있어 이를 방지하기 위해 코드 구현 방식을 선택했습니다.<br>
  또한 초기화 시 두 가지 방법을 비교하였습니다. <br>
  **1. List 사용 - 존재하지 않는 카테고리를 모아 saveAll로 저장**<br>
  **2. Set 사용 - 이미 존재하는 카테고리 이름을 DB에서 가져와 중복 확인 최소화**<br>
  이 프로젝트에서는 두 번째 방식을 선택하였는데, List 방식은 각 카테고리의 존재 여부를 체크하기 위해 여러 번의 데이터베이스 호출을 발생시키기 때문입니다.<br>
  **이를 줄이기 위해 Set을 활용하여 데이터베이스 호출을 최소화하였습니다.**

- **📅 날짜 포맷 고민** <br>
  해당 프로젝트는 Year - Month의 입력을 따로 받는 구조입니다.<br>
  만약 프론트에서 날짜 UI를 사용할 시 yyyy-MM 혹은 yyyy-MM-dd 같은 문자열 포맷을 사용해 년월을 한 번에 받을 수 있기 때문에 데이터를 일관되게 처리할 방법을 고민했습니다.<br>
  이 과정에서 조회 시 사용했던 JsonFormat 방식과 YearMonth를 활용해 일관되게 입력 받는 방법을 알게 되었습니다(ex. `@JsonFormat(pattern = "yyyy-MM")`와 같이 포맷을 맞춤).<br>
  리팩토링은 진행하지 않았지만, 협업 관점에서 좋은 인사이트를 얻게 됐습니다.

<details>
  <summary> 스프링 시큐리티 도입 회고..</summary>
  시큐리티에 대해서는 아쉬움이 많이 남은 프로젝트였습니다.<br>
  기능 구현을 우선으로 하다보니 사용자 API와 토큰관련 클래스 분리나, 핸들러 사용 등 유용한 기능들을 많이 사용하지 못한 것이 마음에 남습니다.<br>
  시큐리티 관련 코드는 꾸준히 공부하면서 디벨롭 해나가는 게 좋을 것 같습니다.
</details>
