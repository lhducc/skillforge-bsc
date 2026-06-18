# Development Plan - BSC SkillForge Backend MVP

## 1. Mục tiêu development

Xây dựng backend Java Spring Boot cho BSC SkillForge MVP theo hướng:

```text
Code được end-to-end workflow trước
→ validate rule quan trọng
→ demo được dữ liệu B1-B8
→ dashboard basic chạy được
→ sau đó mới scale feature nâng cao
```

Stack đề xuất:

```text
Java 21
Spring Boot 3.5.x
PostgreSQL
Spring Data JPA
Spring Validation
Spring Security/JWT basic
Flyway
Lombok
MapStruct
Swagger/OpenAPI
```

---

# 2. Nguyên tắc code MVP

## 2.1. Code theo module nghiệp vụ

Không gom toàn bộ vào một package lớn.

Package đề xuất:

```text
src/main/java/.../bsc
├── common
├── company
├── strategycycle
├── assessment
├── strategybuilding
├── strategyselection
├── strategymap
├── fishbone
├── weight
├── measurement
├── execution
├── report
└── dashboard
```

## 2.2. Ưu tiên service rule rõ ràng

Các rule validate nghiệp vụ nên nằm ở service/domain validator, không nhét hết vào controller.

Ví dụ:

```text
WeightAllocationValidator
BscStepGuard
KpiReportCalculator
TaskStatusTransitionValidator
```

## 2.3. Response format thống nhất

Tất cả API trả về một response format chung:

```json
{
  "success": true,
  "code": "SUCCESS",
  "message": "Success",
  "data": {}
}
```

## 2.4. Không over-engineering

MVP chưa cần:

- Event-driven architecture
- Microservice
- CQRS
- Complex permission matrix
- Full audit log
- Formula engine

---

# 3. Development Phases

## Phase 0 - Project Setup

### Mục tiêu

Tạo codebase Spring Boot sạch, chạy được, kết nối DB, có Swagger.

### Tasks

- Tạo project Spring Boot 3.5.x, Java 21.
- Add dependencies:
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-validation
  - spring-boot-starter-security
  - postgresql
  - flyway-core
  - lombok
  - mapstruct
  - springdoc-openapi
- Config PostgreSQL.
- Config Flyway.
- Config Swagger.
- Tạo common response.
- Tạo global exception handler.
- Tạo error code enum.

### Done khi

- App start thành công.
- Swagger mở được.
- DB migrate thành công.
- Có health/test endpoint.

---

## Phase 1 - Company Setup + BSC Strategy Cycle

### Module

```text
company
strategycycle
```

### Tables

```text
companies
departments
employees
user_accounts
bsc_strategies
bsc_step_statuses
```

### APIs

- Create company
- Create department
- Create employee
- Create user account
- Create BSC Strategy
- Get BSC Strategy
- Get step statuses

### Business Logic

- Khi tạo BSC Strategy, sinh 8 step statuses.
- B1 NOT_STARTED, B2-B8 LOCKED.

### Done khi

Demo được:

```text
Tạo company
→ tạo phòng ban
→ tạo nhân viên
→ tạo BSC Strategy Draft
→ thấy 8 step statuses
```

---

## Phase 2 - B1 Assessment

### Module

```text
assessment
```

### Tables

```text
assessment_financials
assessment_market_shares
assessment_text_items
```

### APIs

- Upsert financials
- Upsert market shares
- Upsert text items
- Get assessment
- Complete B1

### Business Logic

- Financial max 3 years.
- Revenue >= 0.
- Current market share total = 100.
- Future market share total = 100.
- Must have own company in each period.
- Complete B1 unlocks B2.

### Done khi

Demo được:

```text
CEO nhập B1
→ complete B1
→ B1 COMPLETED
→ B2 NOT_STARTED
```

---

## Phase 3 - B2 Strategy Building

### Module

```text
strategybuilding
```

### Tables

```text
analysis_items
swot_items
candidate_strategies
strategy_swot_items
```

### APIs

- Upsert analysis items
- Create/delete SWOT item
- Create/update/delete candidate strategy
- List candidate strategies
- Complete B2

### Business Logic

- 7S item chỉ chọn S/W.
- 5 Forces/PESTEL item chỉ chọn O/T.
- Một analysis item chỉ chọn vào một SWOT item.
- Tổng candidate strategy <= 12.
- Một SWOT item chỉ dùng tối đa một strategy.
- Validate SO/ST/WO/WT rule.
- Complete B2 unlocks B3.

### Done khi

Demo được:

```text
Nhập 7S/5 Forces/PESTEL
→ chọn SWOT
→ tạo SO/ST/WO/WT strategy
→ complete B2
```

---

## Phase 4 - B3 Strategy Selection

### Module

```text
strategyselection
```

### Tables

```text
selected_strategies
```

### APIs

- Select strategies
- Get selected strategies
- Complete B3

### Business Logic

- Chọn 1-2 strategies.
- Không chọn trùng.
- Candidate strategy phải hợp lệ.
- Complete B3 unlocks B4.

### Done khi

Demo được:

```text
CEO chọn 1 hoặc 2 strategy
→ complete B3
→ B4 unlocked
```

---

## Phase 5 - B4 Strategy Map

### Module

```text
strategymap
```

### Tables

```text
strategy_maps
strategic_objectives
objective_links
final_strategic_objectives
final_objective_sources
final_objective_links
```

### APIs

- Create strategy map
- Create/update/delete strategic objective
- Create/delete objective link
- Build final objectives
- Create/delete final objective link
- Get final strategy map
- Complete B4

### Business Logic

- Mỗi selected strategy có đủ 4 perspective.
- Mỗi strategy tối đa 12 objectives.
- Link không tự trỏ.
- Link không trùng.
- Final objective phải có đủ 4 perspective.
- Complete B4 unlocks B5.

### MVP simplification

- Nếu chọn 1 strategy: copy objectives thành final objectives.
- Nếu chọn 2 strategy: dùng API build final objectives từ sourceObjectiveIds.

### Done khi

Demo được:

```text
Tạo objectives theo 4 perspective
→ tạo link nhân quả
→ build final objectives
→ complete B4
```

---

## Phase 6 - B5 Fishbone / Department KPI

### Module

```text
fishbone
```

### Tables

```text
department_participations
department_kpis
```

### APIs

- Department join objective
- Remove participation
- Create/update/delete department KPI
- Get company fishbone
- Get department fishbone
- Complete B5

### Business Logic

- Department N-N final objective qua department_participations.
- Không join trùng department + objective.
- KPI phải thuộc participation hợp lệ.
- Trưởng phòng chỉ tạo KPI cho department mình.
- Complete B5 unlocks B6.

### Done khi

Demo được:

```text
HR join 16/24 objectives
→ HR tạo KPI trong các objectives đã join
→ CEO xem fishbone tổng
→ HR xem fishbone phòng ban
```

---

## Phase 7 - B6 Weight Allocation

### Module

```text
weight
```

### Tables

```text
perspective_weights
objective_weights
kpi_weights
```

### APIs

- Upsert perspective weights
- Upsert objective weights
- Upsert KPI weights
- Get weight tree
- Complete B6

### Business Logic

- Tổng 4 perspective = 100.
- Tổng objective trong perspective = perspective weight.
- Tổng KPI trong objective = objective weight.
- Weight > 0.
- Dùng BigDecimal.
- Complete B6 unlocks B7.

### Done khi

Demo được:

```text
CEO phân bổ 100%
→ validate đúng tổng
→ complete B6
```

---

## Phase 8 - B7 KPI Measurement

### Module

```text
measurement
```

### Tables

```text
kpi_measurements
```

### APIs

- Upsert KPI measurement
- Get KPI measurements
- Complete B7

### Business Logic

- Mỗi department KPI có weight phải có measurement.
- Unit required.
- Target required.
- Direction required.
- Reporting frequency required.
- Threshold valid.
- Complete B7 unlocks B8.

### MVP simplification

- Chưa làm target_type.
- Target hiểu là mục tiêu cần đạt trong chu kỳ BSC.

### Done khi

Demo được:

```text
KPI Sales weight 10%
Baseline 20%
Target 35%
Direction HIGHER_IS_BETTER
→ complete B7
```

---

## Phase 9 - B8 Execution / Task / KPI Report

### Modules

```text
execution
report
```

### Tables

```text
action_plans
tasks
task_dependencies
task_comments
kpi_reports
```

### APIs

- Create/update/list action plan
- Create task
- Update task status
- Get Kanban
- Get Gantt data
- Create KPI report
- List KPI reports
- Review KPI report
- Complete B8 setup

### Business Logic

- Action plan phải thuộc KPI.
- Task phải thuộc action plan.
- Task tự suy ra KPI/objective/department từ action plan.
- Status transition hợp lệ.
- BLOCKED cần block reason.
- KPI report actual required.
- Không trùng report cùng KPI + period.
- Server tính completion/status.

### Done khi

Demo được:

```text
Trưởng phòng tạo action plan
→ tạo task
→ nhân viên cập nhật task
→ trưởng phòng nhập actual KPI
→ report tính completion/status
```

---

## Phase 10 - Dashboard Basic

### Module

```text
dashboard
```

### APIs

- Get company dashboard
- Get objective dashboard
- Get department dashboard
- Get KPI detail dashboard

### Business Logic

Dashboard đọc từ:

```text
kpi_weights
kpi_measurements
kpi_reports
tasks
action_plans
```

Công thức:

```text
completion_rate = actual/target hoặc target/actual
score_completion = min(completion_rate, 100)
weighted_score = weight_percent * score_completion / 100
```

### Done khi

Demo được:

```text
Company score
Perspective score
Objective score
KPI status
Task summary
```

---

# 4. Suggested Package Structure

```text
com.skillforge.bsc
├── BscApplication.java
├── common
│   ├── response
│   │   ├── ApiResponse.java
│   │   └── PageResponse.java
│   ├── exception
│   │   ├── BusinessException.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── ErrorCode.java
│   ├── enums
│   └── util
├── company
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   └── mapper
├── strategycycle
├── assessment
├── strategybuilding
├── strategyselection
├── strategymap
├── fishbone
├── weight
├── measurement
├── execution
├── report
└── dashboard
```

---

# 5. Migration Plan

Flyway files:

```text
V1__create_company_tables.sql
V2__create_bsc_workflow_tables.sql
V3__create_assessment_tables.sql
V4__create_strategy_building_tables.sql
V5__create_strategy_selection_tables.sql
V6__create_strategy_map_tables.sql
V7__create_fishbone_tables.sql
V8__create_weight_tables.sql
V9__create_measurement_tables.sql
V10__create_execution_report_tables.sql
V11__seed_demo_data.sql
```

---

# 6. Demo Data Plan

Seed data nên có:

## Company

```text
SkillForge SME
```

## Departments

```text
Sales
Marketing
Product
HR
CSKH
```

## Users

```text
CEO
Sales Manager
Marketing Manager
Product Manager
HR Manager
CSKH Manager
Sales Employee 1
CSKH Employee 1
```

## Example KPI

```text
Perspective: FINANCIAL
Objective: Tăng doanh thu từ khách hàng SME
Department: Sales
KPI: Tăng tỷ lệ chốt đơn
Weight: 10%
Baseline: 20%
Target: 35%
Actual: 28%
Completion: 80%
Weighted score: 8/10
```

---

# 7. Suggested Implementation Order for Coding

Thứ tự code thực tế:

```text
1. Project setup + common layer
2. Flyway migrations V1-V2
3. Company setup APIs
4. BSC Strategy + Step Status APIs
5. B1 APIs
6. B2 APIs
7. B3 APIs
8. B4 APIs
9. B5 APIs
10. B6 APIs
11. B7 APIs
12. B8 APIs
13. Dashboard APIs
14. Seed data
15. Swagger polish
16. Basic integration test
```

---

# 8. Testing Plan MVP

## 8.1. Unit Test ưu tiên

- Weight validation
- KPI report completion calculator
- Task status transition
- Step guard

## 8.2. Integration Test ưu tiên

- Create BSC Strategy sinh 8 step statuses
- Complete B1 unlock B2
- B6 validate weight total
- B8 create KPI report tính completion/status

## 8.3. Manual Demo Test

Checklist:

```text
[ ] Create company
[ ] Create departments/employees
[ ] Create BSC Strategy
[ ] Complete B1
[ ] Complete B2
[ ] Complete B3
[ ] Complete B4
[ ] Department creates KPI in B5
[ ] CEO allocates weight in B6
[ ] CEO configures measurement in B7
[ ] Department creates action plan/task in B8
[ ] Department submits KPI report
[ ] Dashboard shows score
```

---

# 9. Risk & Mitigation

## Risk 1: Scope quá lớn

Mitigation:

- Code từng phase.
- Mỗi phase phải chạy được API demo.
- Không tối ưu UI/dashboard trước khi xong flow.

## Risk 2: B4 merge phức tạp

Mitigation:

- MVP build final objectives bằng API đơn giản.
- UI có thể hỗ trợ keep/copy trước.
- Merge nâng cao để phase sau.

## Risk 3: B6 validate decimal sai

Mitigation:

- Dùng BigDecimal.
- Scale thống nhất 3 chữ số thập phân.
- Không dùng double/float.

## Risk 4: Dashboard thiếu report

Mitigation:

- Nếu KPI chưa có report, completion = 0 hoặc status = NO_REPORT.
- MVP có thể hiển thị KPI chưa báo cáo.

---

# 10. Final Coding Entry Point

Sau khi 5 spec file này chốt, bước code đầu tiên nên là:

```text
Phase 0: Project Setup
→ common response/exception
→ Flyway V1-V2
→ Company + BSC Strategy + Step Status
```

Không nên code B1-B8 ngay khi chưa có foundation.
