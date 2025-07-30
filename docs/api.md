# 📝 [ Plan-B ] API 명세서
### Members 👤
#### 1. 회원가입
아이디와 비밀번호를 입력한 회원가입 알림설정 컬럼은 지출 알림 기능에서 사용됩니다.<br>
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

#### 2. 로그인</summary>
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

#### 3. 토큰 재발급
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

#### 4. 로그아웃
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

<br>

### Category 📂

#### 1. 카테고리 목록 조회
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


<br>

### Budgets 💰
#### 1. 예산 생성
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

#### 2. 예산 조회
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

<br>

### Spends 💸

#### 1. 지출 생성

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

#### 2. 지출 수정
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

#### 3. 지출 삭제
사용자는 등록한 지출 정보를 삭제할 수 있습니다.
  
`DELETE /api/spends/{spendId}`

<strong>Response</strong>
```json
204 No Content
```
```text
존재하지 않는 지출 정보 404 NOT FOUND
```

#### 4.지출 목록 조회
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
#### 5. 지출 상세 조회

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

#### 6. 오늘의 지출
오늘의 지출 내역을 알림으로 받을 수 있습니다. (Scheduled: 매일 오후 8시 실행 - 알림설정 허용한 사용자에게만 발송) <br>
오늘 사용한 총 지출액, 등록한 예산 범위 내 하루 추천 사용액, 총 위험도, 카테고리 별 추천 사용액과 위험도 등을 알려줍니다.<br>
등록 된 지출 중 `비교 데이터` ( 지난 달, 지난 주 예산으로 등록된 카테고리 별 지출 )가 없을 시 신규데이터로 판단하여 증가율 100%로 설정<br>
예산에 등록되지 않은 카테고리 지출은 별도로 위험도를 알려주지 않고, 등록되지 않은 카테고리임을 알려줍니다.<br>
  
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

<br>

### Statistics 📊
#### 1. 월간 예산 통계
월간 예산 지출 사용량 통계를 확인할 수 있습니다.<br>
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

#### 2. 주간 지출 통계
`지난 주`와 `이번 주`의 비교 지출 통계를 확인할 수 있습니다.
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

#### 3. 월간 지출 통계
주간 지출통계와 같이 `지난 달`과 `이번 달`의 비교 지출 통계를 확인할 수 있습니다.<br>
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
