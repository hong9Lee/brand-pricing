# 🏷️ 최저·최고가 집계 서비스
브랜드별, 카테고리별 상품 가격 정보를 집계하여 **최저가 · 최고가** 정보를 효율적으로 제공하는 서비스입니다.

## 🧩 기술 스택
- Kotlin 1.9.25  
- Java 21  
- Spring Boot 3.4.4  
- Spring Data JPA  
- QueryDSL 5.0.0  
- Flyway 10.4.1  
- H2 In-Memory Database  
- Kotlin Logging 5.1.0  
- Gradle  
- JUnit5  


## 🚀 실행 방법
> 프로젝트 클론
```
git clone https://github.com/your-org/brand-pricing-summary.git
```

> 서버 실행
```
BrandPricingApplication 실행 (localhost:8080)
```

> 환경 정보
```
서버 실행 시, flyway를 통해 로컬DB(H2)에 데이터를 초기화 합니다.

H2 In-Memory Database
URL      : jdbc:h2:mem:bp-db
Username : sa
Password : (없음)
```

> 테스트
```
adapter/controller/http 경로의 .http 파일을 통해 API 테스트 가능
```

### 📂 프로젝트 구조

```
📦 brand-pricing
├── adapter        → api, controller
├── application    → service, domain, port, model
├── infra          → entity, repository
├── support        → 공통 유틸리티
├── test           → 테스트 패키지
└── README.md
```  

### 🛠️ 구현 범위
> API 제공  

- 브랜드/상품 등록, 수정, 삭제
  - 삭제의 경우, 데이터 정합성 및 이력 관리를 위해 상태값만 INACTIVE로 변경하는 Soft Delete 방식 적용
- 브랜드·카테고리별 집계 데이터 조회  
- 특정 카테고리의 최저·최고 가격 브랜드 조회  


> 데이터 초기화
```
서버 구동 시 Flyway로 기본 데이터 자동 세팅
```

  
> 에러 처리

| 필드명   | 타입   | 설명                                           |
|--------|--------|------------------------------------------------|
| type   | String | 요청 API의 URI           |
| title  | String | 에러 코드       |
| status | Number | HTTP 상태 코드 (예: 400, 404, 500 등)              |
| detail | String | 에러 상세 메시지                       |
| instance | String | 요청 API의 URI (문제 발생 위치, type과 동일)        |


```
잘못된 요청값에 대해 400 응답 처리
존재하지 않는 카테고리, 브랜드 요청 시 404 응답
서버 로직 에러 발생 시 500 응답
```  
  
예시) 
```
{
    "type": "/api/bp/v1/product",
    "title": "NOT_FOUND_PRODUCT",
    "status": 400,
    "detail": "존재하지 않는 상품 입니다.",
    "instance": "http://localhost:8080/api/bp/v1/product"
}
```

### 📌 집계 테이블 설계
- 상품 등록 및 수정 시, 조회 성능을 높이고 데이터 일관성을 유지하기 위해 가격 집계 데이터를 선반영하는 구조로 설계하였습니다. 
- 집계 테이터 관리
  - 데이터는 브랜드-카테고리 단위로 관리
  - 상품 등록/수정 시, 이벤트를 발행하여 최저가/최고가 집계 데이터 실시간 반영
  - 집계 과정에서 발생할 수 있는 실패 케이스를 데이터로 저장하고 향후 배치 작업으로 실패 데이터를 재처리 할 수 있도록 설계



