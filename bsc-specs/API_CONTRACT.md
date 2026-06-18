# API Contract - BSC SkillForge MVP

## 1. Quy ước chung

Base path:

```http
/api/v1
```

Response format:

```json
{
  "success": true,
  "code": "SUCCESS",
  "message": "Success",
  "data": {}
}
```

Error format:

```json
{
  "success": false,
  "code": "B6_TOTAL_WEIGHT_INVALID",
  "message": "Tổng tỉ trọng không hợp lệ",
  "data": null
}
```

Pagination response:

```json
{
  "items": [],
  "page": 0,
  "size": 20,
  "totalItems": 100,
  "totalPages": 5
}
```

---

# 2. Company Setup APIs

## 2.1. Company

### Create company

```http
POST /companies
```

Request:

```json
{
  "name": "SkillForge SME",
  "taxCode": "0312345678",
  "industry": "Software",
  "size": "50-100"
}
```

Response:

```json
{
  "id": "uuid",
  "name": "SkillForge SME",
  "status": "ACTIVE"
}
```

### Get company detail

```http
GET /companies/{companyId}
```

### Update company

```http
PUT /companies/{companyId}
```

---

## 2.2. Department

### Create department

```http
POST /companies/{companyId}/departments
```

Request:

```json
{
  "name": "Sales",
  "code": "SALES",
  "color": "#22C55E",
  "description": "Phòng kinh doanh"
}
```

### List departments by company

```http
GET /companies/{companyId}/departments
```

### Update department

```http
PUT /departments/{departmentId}
```

---

## 2.3. Employee / Account

### Create employee

```http
POST /companies/{companyId}/employees
```

Request:

```json
{
  "departmentId": "uuid",
  "fullName": "Nguyễn Văn A",
  "email": "a@company.com",
  "phone": "0900000000",
  "positionTitle": "Sales Manager"
}
```

### Create user account

```http
POST /employees/{employeeId}/account
```

Request:

```json
{
  "email": "a@company.com",
  "password": "123456",
  "role": "DEPARTMENT_HEAD"
}
```

### List employees

```http
GET /companies/{companyId}/employees?departmentId={departmentId}
```

---

# 3. BSC Strategy APIs

## 3.1. Create BSC Strategy

```http
POST /companies/{companyId}/bsc-strategies
```

Request:

```json
{
  "name": "BSC Strategy 2026",
  "description": "Chiến lược BSC năm 2026",
  "year": 2026
}
```

Response:

```json
{
  "id": "uuid",
  "companyId": "uuid",
  "name": "BSC Strategy 2026",
  "status": "DRAFT"
}
```

Side effect:

- Sinh 8 dòng `bsc_step_statuses`.
- B1 = NOT_STARTED.
- B2-B8 = LOCKED.

## 3.2. Get BSC Strategy

```http
GET /bsc-strategies/{strategyId}
```

## 3.3. Get step statuses

```http
GET /bsc-strategies/{strategyId}/steps
```

## 3.4. Activate / Operating

```http
POST /bsc-strategies/{strategyId}/activate
POST /bsc-strategies/{strategyId}/operate
```

---

# 4. B1 Assessment APIs

## 4.1. Upsert financials

```http
PUT /bsc-strategies/{strategyId}/assessment/financials
```

Request:

```json
{
  "items": [
    { "year": 2026, "revenue": 10000000000, "profit": 1000000000 },
    { "year": 2027, "revenue": 15000000000, "profit": 2000000000 }
  ]
}
```

## 4.2. Upsert market shares

```http
PUT /bsc-strategies/{strategyId}/assessment/market-shares
```

Request:

```json
{
  "items": [
    {
      "periodType": "CURRENT",
      "companyName": "Công ty mình",
      "marketSharePercent": 20,
      "ownCompany": true,
      "displayOrder": 1
    },
    {
      "periodType": "CURRENT",
      "companyName": "Đối thủ A",
      "marketSharePercent": 80,
      "ownCompany": false,
      "displayOrder": 2
    }
  ]
}
```

## 4.3. Upsert text items

```http
PUT /bsc-strategies/{strategyId}/assessment/text-items
```

Request:

```json
{
  "items": [
    {
      "category": "CURRENT_SEGMENT",
      "content": "SME ngành giáo dục",
      "displayOrder": 1
    },
    {
      "category": "COMPANY_STRENGTH",
      "content": "Đội ngũ triển khai nhanh",
      "displayOrder": 1
    }
  ]
}
```

## 4.4. Get assessment

```http
GET /bsc-strategies/{strategyId}/assessment
```

## 4.5. Complete B1

```http
POST /bsc-strategies/{strategyId}/assessment/complete
```

Side effect:

- Validate B1.
- Update B1 = COMPLETED.
- Unlock B2.

---

# 5. B2 Strategy Building APIs

## 5.1. Upsert analysis items

```http
PUT /bsc-strategies/{strategyId}/analysis-items
```

Request:

```json
{
  "items": [
    {
      "modelType": "SEVEN_S",
      "factorCode": "SKILLS",
      "content": "Đội ngũ kỹ thuật triển khai nhanh",
      "displayOrder": 1
    },
    {
      "modelType": "PESTEL",
      "factorCode": "TECHNOLOGICAL",
      "content": "SME quan tâm chuyển đổi số",
      "displayOrder": 1
    }
  ]
}
```

## 5.2. Select SWOT item

```http
POST /bsc-strategies/{strategyId}/swot-items
```

Request:

```json
{
  "swotType": "S",
  "sourceAnalysisItemId": "uuid"
}
```

## 5.3. Delete SWOT item

```http
DELETE /swot-items/{swotItemId}
```

## 5.4. Create candidate strategy

```http
POST /bsc-strategies/{strategyId}/candidate-strategies
```

Request:

```json
{
  "strategyGroup": "SO",
  "name": "Tăng trưởng thị phần SME bằng năng lực triển khai nhanh",
  "description": "Tận dụng đội ngũ kỹ thuật triển khai nhanh để mở rộng thị phần SME.",
  "swotItemIds": ["uuid-s1", "uuid-o1"],
  "displayOrder": 1
}
```

## 5.5. Update candidate strategy

```http
PUT /candidate-strategies/{candidateStrategyId}
```

## 5.6. Delete candidate strategy

```http
DELETE /candidate-strategies/{candidateStrategyId}
```

## 5.7. List candidate strategies

```http
GET /bsc-strategies/{strategyId}/candidate-strategies
```

## 5.8. Complete B2

```http
POST /bsc-strategies/{strategyId}/strategy-building/complete
```

---

# 6. B3 Strategy Selection APIs

## 6.1. Select strategies

```http
PUT /bsc-strategies/{strategyId}/selected-strategies
```

Request:

```json
{
  "items": [
    {
      "candidateStrategyId": "uuid",
      "priorityOrder": 1,
      "selectionReason": "Phù hợp năng lực hiện tại"
    },
    {
      "candidateStrategyId": "uuid",
      "priorityOrder": 2,
      "selectionReason": "Tận dụng cơ hội thị trường"
    }
  ]
}
```

Rule:

- Items size từ 1 đến 2.

## 6.2. Get selected strategies

```http
GET /bsc-strategies/{strategyId}/selected-strategies
```

## 6.3. Complete B3

```http
POST /bsc-strategies/{strategyId}/strategy-result/complete
```

---

# 7. B4 Strategy Map APIs

## 7.1. Create individual strategy map

```http
POST /bsc-strategies/{strategyId}/strategy-maps
```

Request:

```json
{
  "selectedStrategyId": "uuid",
  "mapType": "INDIVIDUAL"
}
```

## 7.2. Create strategic objective

```http
POST /strategy-maps/{strategyMapId}/objectives
```

Request:

```json
{
  "selectedStrategyId": "uuid",
  "name": "Tăng doanh thu từ khách hàng SME",
  "description": "Tập trung tăng trưởng doanh thu nhóm SME.",
  "perspectiveCode": "FINANCIAL",
  "displayOrder": 1
}
```

## 7.3. Update strategic objective

```http
PUT /strategic-objectives/{objectiveId}
```

## 7.4. Delete strategic objective

```http
DELETE /strategic-objectives/{objectiveId}
```

## 7.5. Create objective link

```http
POST /strategy-maps/{strategyMapId}/objective-links
```

Request:

```json
{
  "sourceObjectiveId": "uuid",
  "targetObjectiveId": "uuid",
  "note": "Tác động nhân quả",
  "displayOrder": 1
}
```

## 7.6. Build final objectives

MVP dùng endpoint này để chốt final objectives từ objectives nguồn.

```http
POST /bsc-strategies/{strategyId}/final-objectives/build
```

Request:

```json
{
  "items": [
    {
      "name": "Tăng trưởng doanh thu SME",
      "description": "Mục tiêu tổng sau gộp/chọn",
      "perspectiveCode": "FINANCIAL",
      "sourceType": "ORIGINAL",
      "sourceObjectiveIds": ["uuid"],
      "displayOrder": 1
    }
  ]
}
```

## 7.7. Create final objective link

```http
POST /bsc-strategies/{strategyId}/final-objective-links
```

Request:

```json
{
  "sourceFinalObjectiveId": "uuid",
  "targetFinalObjectiveId": "uuid",
  "note": "Nhân quả tổng",
  "displayOrder": 1
}
```

## 7.8. Get final strategy map

```http
GET /bsc-strategies/{strategyId}/final-strategy-map
```

## 7.9. Complete B4

```http
POST /bsc-strategies/{strategyId}/strategy-map/complete
```

---

# 8. B5 Fishbone / Department KPI APIs

## 8.1. Department join final objective

```http
POST /bsc-strategies/{strategyId}/department-participations
```

Request:

```json
{
  "finalStrategicObjectiveId": "uuid",
  "departmentId": "uuid",
  "departmentHeadId": "uuid"
}
```

## 8.2. Remove department participation

```http
DELETE /department-participations/{participationId}
```

## 8.3. Create department KPI

```http
POST /department-kpis
```

Request:

```json
{
  "bscStrategyId": "uuid",
  "finalStrategicObjectiveId": "uuid",
  "departmentId": "uuid",
  "departmentParticipationId": "uuid",
  "name": "Tăng tỷ lệ chốt đơn",
  "description": "Tăng tỷ lệ lead đủ điều kiện trở thành deal thành công.",
  "displayOrder": 1
}
```

## 8.4. Update department KPI

```http
PUT /department-kpis/{departmentKpiId}
```

## 8.5. Delete department KPI

```http
DELETE /department-kpis/{departmentKpiId}
```

## 8.6. Get company fishbone

```http
GET /bsc-strategies/{strategyId}/fishbone/company
```

## 8.7. Get department fishbone

```http
GET /bsc-strategies/{strategyId}/fishbone/departments/{departmentId}
```

## 8.8. Complete B5

```http
POST /bsc-strategies/{strategyId}/fishbone/complete
```

---

# 9. B6 Weight Allocation APIs

## 9.1. Upsert perspective weights

```http
PUT /bsc-strategies/{strategyId}/weights/perspectives
```

Request:

```json
{
  "items": [
    { "perspectiveCode": "FINANCIAL", "weightPercent": 40 },
    { "perspectiveCode": "CUSTOMER", "weightPercent": 30 },
    { "perspectiveCode": "INTERNAL_PROCESS", "weightPercent": 15 },
    { "perspectiveCode": "LEARNING_AND_GROWTH", "weightPercent": 15 }
  ]
}
```

## 9.2. Upsert objective weights

```http
PUT /bsc-strategies/{strategyId}/weights/objectives
```

Request:

```json
{
  "items": [
    {
      "finalStrategicObjectiveId": "uuid",
      "perspectiveCode": "FINANCIAL",
      "weightPercent": 20
    }
  ]
}
```

## 9.3. Upsert KPI weights

```http
PUT /bsc-strategies/{strategyId}/weights/kpis
```

Request:

```json
{
  "items": [
    {
      "departmentKpiId": "uuid",
      "finalStrategicObjectiveId": "uuid",
      "departmentId": "uuid",
      "perspectiveCode": "FINANCIAL",
      "weightPercent": 10
    }
  ]
}
```

## 9.4. Get weight tree

```http
GET /bsc-strategies/{strategyId}/weights/tree
```

## 9.5. Complete B6

```http
POST /bsc-strategies/{strategyId}/weights/complete
```

---

# 10. B7 Measurement APIs

## 10.1. Upsert KPI measurement

```http
PUT /department-kpis/{departmentKpiId}/measurement
```

Request:

```json
{
  "unit": "%",
  "baselineValue": 20,
  "targetValue": 35,
  "direction": "HIGHER_IS_BETTER",
  "reportingFrequency": "MONTHLY",
  "formulaDescription": "Số deal thành công / Tổng lead đủ điều kiện * 100",
  "greenThreshold": 90,
  "yellowThreshold": 70,
  "redThreshold": 0,
  "reportOwnerId": "uuid"
}
```

## 10.2. Get KPI measurements

```http
GET /bsc-strategies/{strategyId}/kpi-measurements
```

## 10.3. Complete B7

```http
POST /bsc-strategies/{strategyId}/measurements/complete
```

---

# 11. B8 Execution APIs

## 11.1. Create action plan

```http
POST /action-plans
```

Request:

```json
{
  "bscStrategyId": "uuid",
  "departmentKpiId": "uuid",
  "name": "Cải thiện quy trình tư vấn sales",
  "description": "Chuẩn hóa quy trình tư vấn để tăng tỷ lệ chốt đơn.",
  "startDate": "2026-01-01",
  "endDate": "2026-03-31",
  "ownerId": "uuid",
  "priority": "HIGH",
  "status": "ACTIVE"
}
```

## 11.2. Update action plan

```http
PUT /action-plans/{actionPlanId}
```

## 11.3. List action plans

```http
GET /bsc-strategies/{strategyId}/action-plans?departmentId={departmentId}&departmentKpiId={departmentKpiId}
```

## 11.4. Create task

```http
POST /tasks
```

Request:

```json
{
  "actionPlanId": "uuid",
  "assigneeId": "uuid",
  "name": "Training đội sales về demo sản phẩm",
  "description": "Tổ chức training quy trình demo mới.",
  "startDate": "2026-01-10",
  "dueDate": "2026-01-20",
  "priority": "HIGH"
}
```

Server tự suy ra:

- bscStrategyId
- departmentKpiId
- finalStrategicObjectiveId
- departmentId

Từ action plan.

## 11.5. Update task status

```http
PATCH /tasks/{taskId}/status
```

Request:

```json
{
  "newStatus": "REVIEW",
  "progressPercent": 80,
  "comment": "Đã hoàn thành training, chờ trưởng phòng review.",
  "blockReason": null
}
```

## 11.6. Get task board

```http
GET /bsc-strategies/{strategyId}/tasks/kanban?departmentId={departmentId}&assigneeId={assigneeId}
```

## 11.7. Get Gantt data

```http
GET /bsc-strategies/{strategyId}/tasks/gantt?departmentId={departmentId}
```

## 11.8. Create KPI report

```http
POST /kpi-reports
```

Request:

```json
{
  "departmentKpiId": "uuid",
  "reportingPeriod": "2026-01",
  "actualValue": 28,
  "note": "Tỷ lệ chốt đơn tăng nhẹ nhưng chưa đạt target.",
  "evidenceUrl": "https://example.com/sales-report.xlsx",
  "reviewStatus": "SUBMITTED"
}
```

Server tính:

- completionRate
- statusColor
- achievementStatus

## 11.9. List KPI reports

```http
GET /department-kpis/{departmentKpiId}/reports
```

## 11.10. Review KPI report

```http
PATCH /kpi-reports/{reportId}/review
```

Request:

```json
{
  "reviewStatus": "APPROVED",
  "note": "Số liệu hợp lệ"
}
```

## 11.11. Complete B8 setup

```http
POST /bsc-strategies/{strategyId}/action-plan/complete
```

---

# 12. Dashboard APIs

## 12.1. Get company dashboard

```http
GET /dashboard/bsc-strategies/{strategyId}
```

Response example:

```json
{
  "companyScore": 72.5,
  "perspectives": [
    {
      "perspectiveCode": "FINANCIAL",
      "weightPercent": 40,
      "score": 30.5,
      "completionRate": 76.25,
      "statusColor": "YELLOW"
    }
  ],
  "kpiSummary": {
    "total": 20,
    "green": 8,
    "yellow": 7,
    "red": 5,
    "achieved": 6,
    "exceeded": 2
  },
  "taskSummary": {
    "todo": 10,
    "inProgress": 15,
    "review": 4,
    "done": 30,
    "blocked": 2,
    "overdue": 5
  }
}
```

## 12.2. Get objective dashboard

```http
GET /dashboard/bsc-strategies/{strategyId}/objectives
```

## 12.3. Get department dashboard

```http
GET /dashboard/bsc-strategies/{strategyId}/departments/{departmentId}
```

## 12.4. Get KPI detail dashboard

```http
GET /dashboard/department-kpis/{departmentKpiId}
```

---

# 13. Error Codes MVP

## Common

```text
RESOURCE_NOT_FOUND
VALIDATION_ERROR
ACCESS_DENIED
INVALID_STATUS
DUPLICATED_RESOURCE
```

## BSC Workflow

```text
BSC_STRATEGY_NOT_FOUND
BSC_STRATEGY_NOT_DRAFT
STEP_LOCKED
STEP_NOT_COMPLETED
STEP_ALREADY_COMPLETED
```

## B1

```text
B1_FINANCIAL_REQUIRED
B1_FINANCIAL_EXCEED_LIMIT
B1_REVENUE_NEGATIVE
B1_MARKET_SHARE_TOTAL_INVALID
B1_MARKET_SHARE_OWN_COMPANY_REQUIRED
B1_TEXT_ITEM_EMPTY
```

## B2

```text
B2_ANALYSIS_ITEM_NOT_FOUND
B2_SWOT_ITEM_DUPLICATED_SOURCE
B2_SWOT_ITEM_ALREADY_USED
B2_CANDIDATE_STRATEGY_LIMIT_EXCEEDED
B2_STRATEGY_GROUP_INVALID
B2_STRATEGY_SWOT_RULE_INVALID
```

## B3

```text
B3_SELECTED_STRATEGY_COUNT_INVALID
B3_SELECTED_STRATEGY_DUPLICATED
B3_CANDIDATE_STRATEGY_INVALID
```

## B4

```text
B4_OBJECTIVE_NAME_REQUIRED
B4_OBJECTIVE_PERSPECTIVE_REQUIRED
B4_OBJECTIVE_LIMIT_EXCEEDED
B4_MISSING_PERSPECTIVE
B4_OBJECTIVE_LINK_SELF_REFERENCE
B4_OBJECTIVE_LINK_DUPLICATED
B4_FINAL_OBJECTIVE_REQUIRED
```

## B5

```text
B5_DEPARTMENT_ALREADY_JOINED_OBJECTIVE
B5_OBJECTIVE_NOT_FOUND
B5_DEPARTMENT_NOT_FOUND
B5_PARTICIPATION_NOT_FOUND
B5_KPI_NAME_REQUIRED
B5_KPI_MUST_BELONG_TO_PARTICIPATION
```

## B6

```text
B6_PERSPECTIVE_WEIGHT_MISSING
B6_TOTAL_PERSPECTIVE_WEIGHT_INVALID
B6_OBJECTIVE_WEIGHT_MISSING
B6_OBJECTIVE_WEIGHT_TOTAL_INVALID
B6_KPI_WEIGHT_MISSING
B6_KPI_WEIGHT_TOTAL_INVALID
B6_WEIGHT_MUST_BE_POSITIVE
```

## B7

```text
B7_KPI_MEASUREMENT_MISSING
B7_UNIT_REQUIRED
B7_TARGET_REQUIRED
B7_DIRECTION_REQUIRED
B7_REPORTING_FREQUENCY_REQUIRED
B7_THRESHOLD_INVALID
```

## B8

```text
B8_ACTION_PLAN_NAME_REQUIRED
B8_TASK_NAME_REQUIRED
B8_TASK_MUST_BELONG_TO_ACTION_PLAN
B8_TASK_INVALID_STATUS_TRANSITION
B8_BLOCK_REASON_REQUIRED
B8_KPI_REPORT_ACTUAL_REQUIRED
B8_KPI_REPORT_DUPLICATED_PERIOD
```
