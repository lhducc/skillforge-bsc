# Database Schema - BSC SkillForge MVP

## 1. Tổng quan

Database MVP gồm 31 bảng chính, chia theo module nghiệp vụ:

| Module                       | Số bảng |
| ---------------------------- | ------: |
| Company Setup                |       4 |
| BSC Workflow                 |       2 |
| B1 Assessment                |       3 |
| B2 Strategy Building         |       4 |
| B3 Strategy Selection        |       1 |
| B4 Strategy Map              |       6 |
| B5 Fishbone / Department KPI |       2 |
| B6 Weight Allocation         |       3 |
| B7 KPI Measurement           |       1 |
| B8 Execution / Report        |       5 |
| Tổng                         |      31 |

Xương sống của hệ thống:

```text
companies
→ bsc_strategies
→ final_strategic_objectives
→ department_kpis
→ action_plans
→ tasks / kpi_reports
```

---

## 2. Quy ước chung

### 2.1. ID

Khuyến nghị dùng UUID cho tất cả bảng chính.

```sql
id UUID PRIMARY KEY
```

### 2.2. Audit field cơ bản

Các bảng chính nên có:

```sql
created_at TIMESTAMP NOT NULL
updated_at TIMESTAMP NOT NULL
created_by UUID NULL
updated_by UUID NULL
```

MVP có thể chưa cần audit phức tạp.

### 2.3. Decimal

Các field phần trăm/trọng số dùng:

```sql
DECIMAL(6,3)
```

Không dùng float/double cho weight và percentage.

---

# 3. Company Setup

## 3.1. companies

Lưu thông tin công ty.

| Field      | Type         | Ghi chú              |
| ---------- | ------------ | -------------------- |
| id         | UUID         | PK                   |
| name       | VARCHAR(255) | Tên công ty          |
| tax_code   | VARCHAR(100) | Mã số thuế, optional |
| industry   | VARCHAR(255) | Ngành nghề           |
| size       | VARCHAR(100) | Quy mô               |
| status     | VARCHAR(50)  | ACTIVE/INACTIVE      |
| created_at | TIMESTAMP    |                      |
| updated_at | TIMESTAMP    |                      |

Quan hệ:

```text
companies 1-N departments
companies 1-N employees
companies 1-N bsc_strategies
```

---

## 3.2. departments

Lưu phòng ban.

| Field       | Type         | Ghi chú             |
| ----------- | ------------ | ------------------- |
| id          | UUID         | PK                  |
| company_id  | UUID         | FK → companies.id   |
| name        | VARCHAR(255) | Tên phòng ban       |
| code        | VARCHAR(100) | Mã phòng ban        |
| color       | VARCHAR(50)  | Màu render fishbone |
| description | TEXT         | Mô tả               |
| status      | VARCHAR(50)  | ACTIVE/INACTIVE     |
| created_at  | TIMESTAMP    |                     |
| updated_at  | TIMESTAMP    |                     |

Unique gợi ý:

```sql
UNIQUE(company_id, code)
```

---

## 3.3. employees

Lưu nhân viên.

| Field          | Type         | Ghi chú             |
| -------------- | ------------ | ------------------- |
| id             | UUID         | PK                  |
| company_id     | UUID         | FK → companies.id   |
| department_id  | UUID         | FK → departments.id |
| full_name      | VARCHAR(255) | Họ tên              |
| email          | VARCHAR(255) | Email               |
| phone          | VARCHAR(50)  | SĐT                 |
| position_title | VARCHAR(255) | Chức danh           |
| status         | VARCHAR(50)  | ACTIVE/INACTIVE     |
| created_at     | TIMESTAMP    |                     |
| updated_at     | TIMESTAMP    |                     |

---

## 3.4. user_accounts

Lưu tài khoản đăng nhập.

| Field         | Type         | Ghi chú                          |
| ------------- | ------------ | -------------------------------- |
| id            | UUID         | PK                               |
| employee_id   | UUID         | FK → employees.id                |
| email         | VARCHAR(255) | Email login                      |
| password_hash | VARCHAR(255) | Mật khẩu hash                    |
| role          | VARCHAR(50)  | CEO/DEPARTMENT_HEAD/EMPLOYEE/... |
| status        | VARCHAR(50)  | ACTIVE/LOCKED/INACTIVE           |
| last_login_at | TIMESTAMP    | Optional                         |
| created_at    | TIMESTAMP    |                                  |
| updated_at    | TIMESTAMP    |                                  |

Unique:

```sql
UNIQUE(email)
```

---

# 4. BSC Workflow

## 4.1. bsc_strategies

Đại diện cho một chu kỳ BSC.

| Field        | Type         | Ghi chú                         |
| ------------ | ------------ | ------------------------------- |
| id           | UUID         | PK                              |
| company_id   | UUID         | FK → companies.id               |
| name         | VARCHAR(255) | Ví dụ: BSC 2026                 |
| description  | TEXT         | Mô tả                           |
| year         | INT          | Năm áp dụng                     |
| status       | VARCHAR(50)  | DRAFT/ACTIVE/OPERATING/ARCHIVED |
| created_by   | UUID         | User tạo                        |
| activated_at | TIMESTAMP    | Khi active                      |
| created_at   | TIMESTAMP    |                                 |
| updated_at   | TIMESTAMP    |                                 |

Quan hệ:

```text
companies 1-N bsc_strategies
bsc_strategies 1-N bsc_step_statuses
```

---

## 4.2. bsc_step_statuses

Lưu trạng thái B1-B8.

| Field              | Type         | Ghi chú                                              |
| ------------------ | ------------ | ---------------------------------------------------- |
| id                 | UUID         | PK                                                   |
| bsc_strategy_id    | UUID         | FK → bsc_strategies.id                               |
| step_code          | VARCHAR(100) | B1_ASSESSMENT...                                     |
| status             | VARCHAR(50)  | LOCKED/NOT_STARTED/IN_PROGRESS/COMPLETED/INVALIDATED |
| completed_by       | UUID         | User hoàn thành                                      |
| completed_at       | TIMESTAMP    | Thời điểm hoàn thành                                 |
| invalidated_reason | TEXT         | Lý do invalid                                        |
| created_at         | TIMESTAMP    |                                                      |
| updated_at         | TIMESTAMP    |                                                      |

Unique:

```sql
UNIQUE(bsc_strategy_id, step_code)
```

---

# 5. B1 Assessment

## 5.1. assessment_financials

| Field           | Type          | Ghi chú              |
| --------------- | ------------- | -------------------- |
| id              | UUID          | PK                   |
| bsc_strategy_id | UUID          | FK                   |
| year            | INT           | Năm                  |
| revenue         | DECIMAL(18,2) | Doanh thu >= 0       |
| profit          | DECIMAL(18,2) | Lợi nhuận, có thể âm |
| created_at      | TIMESTAMP     |                      |
| updated_at      | TIMESTAMP     |                      |

Unique:

```sql
UNIQUE(bsc_strategy_id, year)
```

Rule:

- Tối đa 3 dòng mỗi strategy.
- Revenue không âm.

---

## 5.2. assessment_market_shares

| Field                | Type         | Ghi chú                    |
| -------------------- | ------------ | -------------------------- |
| id                   | UUID         | PK                         |
| bsc_strategy_id      | UUID         | FK                         |
| period_type          | VARCHAR(50)  | CURRENT/FUTURE             |
| company_name         | VARCHAR(255) | Tên công ty                |
| market_share_percent | DECIMAL(6,3) | Tỉ lệ thị phần             |
| is_own_company       | BOOLEAN      | Có phải công ty mình không |
| display_order        | INT          | Thứ tự hiển thị            |
| created_at           | TIMESTAMP    |                            |
| updated_at           | TIMESTAMP    |                            |

Rule:

- Tổng CURRENT = 100.
- Tổng FUTURE = 100.
- Mỗi period phải có `is_own_company = true`.

---

## 5.3. assessment_text_items

| Field           | Type         | Ghi chú           |
| --------------- | ------------ | ----------------- |
| id              | UUID         | PK                |
| bsc_strategy_id | UUID         | FK                |
| category        | VARCHAR(100) | Loại dynamic list |
| content         | TEXT         | Nội dung          |
| display_order   | INT          | Thứ tự            |
| created_at      | TIMESTAMP    |                   |
| updated_at      | TIMESTAMP    |                   |

Category enum:

```text
CURRENT_SEGMENT
FUTURE_SEGMENT
CURRENT_CORE_PRODUCT
FUTURE_CORE_PRODUCT
COMPANY_STRENGTH
INDUSTRY_SUCCESS_FACTOR
COMPETITOR_STRENGTH
COMPETITOR_WEAKNESS
COMPETITIVE_ADVANTAGE
```

---

# 6. B2 Strategy Building

## 6.1. analysis_items

Lưu item của 7S, 5 Forces, PESTEL.

| Field           | Type         | Ghi chú                       |
| --------------- | ------------ | ----------------------------- |
| id              | UUID         | PK                            |
| bsc_strategy_id | UUID         | FK                            |
| model_type      | VARCHAR(50)  | SEVEN_S/FIVE_FORCES/PESTEL    |
| factor_code     | VARCHAR(100) | STRATEGY/SKILLS/POLITICAL/... |
| content         | TEXT         | Nội dung                      |
| display_order   | INT          | Thứ tự                        |
| created_at      | TIMESTAMP    |                               |
| updated_at      | TIMESTAMP    |                               |

Rule:

- Nếu model_type = SEVEN_S thì factor_code phải thuộc SevenSFactor.
- Nếu model_type = FIVE_FORCES thì factor_code phải thuộc FiveForcesFactor.
- Nếu model_type = PESTEL thì factor_code phải thuộc PestelFactor.

---

## 6.2. swot_items

Lưu SWOT item được chọn từ analysis item.

| Field                   | Type        | Ghi chú                     |
| ----------------------- | ----------- | --------------------------- |
| id                      | UUID        | PK                          |
| bsc_strategy_id         | UUID        | FK                          |
| swot_type               | VARCHAR(10) | S/W/O/T                     |
| source_analysis_item_id | UUID        | FK → analysis_items.id      |
| content_snapshot        | TEXT        | Nội dung tại thời điểm chọn |
| created_at              | TIMESTAMP   |                             |
| updated_at              | TIMESTAMP   |                             |

Unique gợi ý:

```sql
UNIQUE(bsc_strategy_id, source_analysis_item_id)
```

Rule:

- Một source analysis item chỉ được chọn vào một SWOT type.

---

## 6.3. candidate_strategies

Lưu strategy ứng viên SO/ST/WO/WT.

| Field           | Type         | Ghi chú        |
| --------------- | ------------ | -------------- |
| id              | UUID         | PK             |
| bsc_strategy_id | UUID         | FK             |
| strategy_group  | VARCHAR(10)  | SO/ST/WO/WT    |
| name            | VARCHAR(255) | Tên strategy   |
| description     | TEXT         | Mô tả          |
| display_order   | INT          | Thứ tự         |
| status          | VARCHAR(50)  | ACTIVE/DELETED |
| created_at      | TIMESTAMP    |                |
| updated_at      | TIMESTAMP    |                |

Rule:

- Tổng candidate strategy mỗi BSC Strategy không quá 12.

---

## 6.4. strategy_swot_items

Bảng phụ mapping strategy dùng SWOT item nào.

| Field                 | Type      | Ghi chú                      |
| --------------------- | --------- | ---------------------------- |
| id                    | UUID      | PK                           |
| candidate_strategy_id | UUID      | FK → candidate_strategies.id |
| swot_item_id          | UUID      | FK → swot_items.id           |
| created_at            | TIMESTAMP |                              |

Unique:

```sql
UNIQUE(swot_item_id)
```

Rule MVP:

- Một SWOT item chỉ được dùng trong tối đa một candidate strategy.

---

# 7. B3 Strategy Selection

## 7.1. selected_strategies

| Field                 | Type      | Ghi chú                      |
| --------------------- | --------- | ---------------------------- |
| id                    | UUID      | PK                           |
| bsc_strategy_id       | UUID      | FK                           |
| candidate_strategy_id | UUID      | FK → candidate_strategies.id |
| priority_order        | INT       | 1 hoặc 2                     |
| selection_reason      | TEXT      | Lý do chọn                   |
| selected_by           | UUID      | User chọn                    |
| selected_at           | TIMESTAMP | Thời điểm chọn               |
| created_at            | TIMESTAMP |                              |
| updated_at            | TIMESTAMP |                              |

Unique:

```sql
UNIQUE(bsc_strategy_id, candidate_strategy_id)
UNIQUE(bsc_strategy_id, priority_order)
```

Rule:

- Mỗi BSC Strategy chọn 1-2 strategy.

---

# 8. B4 Strategy Map

## 8.1. strategy_maps

| Field                | Type        | Ghi chú                                         |
| -------------------- | ----------- | ----------------------------------------------- |
| id                   | UUID        | PK                                              |
| bsc_strategy_id      | UUID        | FK                                              |
| selected_strategy_id | UUID        | FK → selected_strategies.id, nullable nếu FINAL |
| map_type             | VARCHAR(50) | INDIVIDUAL/FINAL                                |
| status               | VARCHAR(50) | DRAFT/COMPLETED                                 |
| created_at           | TIMESTAMP   |                                                 |
| updated_at           | TIMESTAMP   |                                                 |

---

## 8.2. strategic_objectives

Objective trong bản đồ riêng.

| Field                | Type         | Ghi chú                     |
| -------------------- | ------------ | --------------------------- |
| id                   | UUID         | PK                          |
| strategy_map_id      | UUID         | FK → strategy_maps.id       |
| selected_strategy_id | UUID         | FK → selected_strategies.id |
| name                 | VARCHAR(255) | Tên mục tiêu                |
| description          | TEXT         | Mô tả                       |
| perspective_code     | VARCHAR(100) | FINANCIAL/CUSTOMER/...      |
| display_order        | INT          | Thứ tự                      |
| status               | VARCHAR(50)  | ACTIVE/DELETED              |
| created_at           | TIMESTAMP    |                             |
| updated_at           | TIMESTAMP    |                             |

Rule:

- Mỗi selected strategy tối đa 12 objectives.
- Phải có đủ 4 perspective.

---

## 8.3. objective_links

Link nhân quả trong bản đồ riêng.

| Field               | Type      | Ghi chú                      |
| ------------------- | --------- | ---------------------------- |
| id                  | UUID      | PK                           |
| strategy_map_id     | UUID      | FK                           |
| source_objective_id | UUID      | FK → strategic_objectives.id |
| target_objective_id | UUID      | FK → strategic_objectives.id |
| note                | TEXT      | Ghi chú                      |
| display_order       | INT       | Thứ tự                       |
| created_at          | TIMESTAMP |                              |
| updated_at          | TIMESTAMP |                              |

Unique:

```sql
UNIQUE(strategy_map_id, source_objective_id, target_objective_id)
```

Rule:

- Source không được bằng target.

---

## 8.4. final_strategic_objectives

Objective tổng cuối cùng, dùng cho B5-B8.

| Field            | Type         | Ghi chú                       |
| ---------------- | ------------ | ----------------------------- |
| id               | UUID         | PK                            |
| bsc_strategy_id  | UUID         | FK                            |
| name             | VARCHAR(255) | Tên final objective           |
| description      | TEXT         | Mô tả                         |
| perspective_code | VARCHAR(100) | 4 góc độ BSC                  |
| source_type      | VARCHAR(50)  | ORIGINAL/MERGED/MANUAL_EDITED |
| display_order    | INT          | Thứ tự                        |
| status           | VARCHAR(50)  | ACTIVE/DELETED                |
| created_at       | TIMESTAMP    |                               |
| updated_at       | TIMESTAMP    |                               |

---

## 8.5. final_objective_sources

Mapping final objective được tạo từ objective nguồn nào.

| Field               | Type      | Ghi chú                            |
| ------------------- | --------- | ---------------------------------- |
| id                  | UUID      | PK                                 |
| final_objective_id  | UUID      | FK → final_strategic_objectives.id |
| source_objective_id | UUID      | FK → strategic_objectives.id       |
| created_at          | TIMESTAMP |                                    |

Unique:

```sql
UNIQUE(final_objective_id, source_objective_id)
```

---

## 8.6. final_objective_links

Link nhân quả tổng.

| Field                     | Type      | Ghi chú                            |
| ------------------------- | --------- | ---------------------------------- |
| id                        | UUID      | PK                                 |
| bsc_strategy_id           | UUID      | FK                                 |
| source_final_objective_id | UUID      | FK → final_strategic_objectives.id |
| target_final_objective_id | UUID      | FK → final_strategic_objectives.id |
| note                      | TEXT      | Ghi chú                            |
| display_order             | INT       | Thứ tự                             |
| created_at                | TIMESTAMP |                                    |
| updated_at                | TIMESTAMP |                                    |

Unique:

```sql
UNIQUE(bsc_strategy_id, source_final_objective_id, target_final_objective_id)
```

---

# 9. B5 Fishbone / Department KPI

## 9.1. department_participations

Bảng phụ N-N giữa department và final objective.

| Field                        | Type        | Ghi chú           |
| ---------------------------- | ----------- | ----------------- |
| id                           | UUID        | PK                |
| bsc_strategy_id              | UUID        | FK                |
| final_strategic_objective_id | UUID        | FK                |
| department_id                | UUID        | FK                |
| department_head_id           | UUID        | FK → employees.id |
| status                       | VARCHAR(50) | ACTIVE/REMOVED    |
| created_by                   | UUID        | User tạo          |
| created_at                   | TIMESTAMP   |                   |
| updated_at                   | TIMESTAMP   |                   |

Unique:

```sql
UNIQUE(bsc_strategy_id, final_strategic_objective_id, department_id)
```

---

## 9.2. department_kpis

KPI nhỏ của phòng ban.

| Field                        | Type         | Ghi chú        |
| ---------------------------- | ------------ | -------------- |
| id                           | UUID         | PK             |
| bsc_strategy_id              | UUID         | FK             |
| final_strategic_objective_id | UUID         | FK             |
| department_id                | UUID         | FK             |
| department_participation_id  | UUID         | FK             |
| name                         | VARCHAR(255) | Tên KPI        |
| description                  | TEXT         | Mô tả KPI      |
| display_order                | INT          | Thứ tự         |
| status                       | VARCHAR(50)  | ACTIVE/DELETED |
| created_by                   | UUID         | User tạo       |
| created_at                   | TIMESTAMP    |                |
| updated_at                   | TIMESTAMP    |                |

Rule:

- KPI phải thuộc objective mà department đã join.

---

# 10. B6 Weight Allocation

## 10.1. perspective_weights

| Field            | Type         | Ghi chú  |
| ---------------- | ------------ | -------- |
| id               | UUID         | PK       |
| bsc_strategy_id  | UUID         | FK       |
| perspective_code | VARCHAR(100) | 4 góc độ |
| weight_percent   | DECIMAL(6,3) | Tỉ trọng |
| created_by       | UUID         |          |
| updated_by       | UUID         |          |
| created_at       | TIMESTAMP    |          |
| updated_at       | TIMESTAMP    |          |

Unique:

```sql
UNIQUE(bsc_strategy_id, perspective_code)
```

---

## 10.2. objective_weights

| Field                        | Type         | Ghi chú                     |
| ---------------------------- | ------------ | --------------------------- |
| id                           | UUID         | PK                          |
| bsc_strategy_id              | UUID         | FK                          |
| final_strategic_objective_id | UUID         | FK                          |
| perspective_code             | VARCHAR(100) | Denormalized để query nhanh |
| weight_percent               | DECIMAL(6,3) | Tỉ trọng                    |
| created_by                   | UUID         |                             |
| updated_by                   | UUID         |                             |
| created_at                   | TIMESTAMP    |                             |
| updated_at                   | TIMESTAMP    |                             |

Unique:

```sql
UNIQUE(bsc_strategy_id, final_strategic_objective_id)
```

---

## 10.3. kpi_weights

| Field                        | Type         | Ghi chú      |
| ---------------------------- | ------------ | ------------ |
| id                           | UUID         | PK           |
| bsc_strategy_id              | UUID         | FK           |
| department_kpi_id            | UUID         | FK           |
| final_strategic_objective_id | UUID         | FK           |
| department_id                | UUID         | FK           |
| perspective_code             | VARCHAR(100) | Denormalized |
| weight_percent               | DECIMAL(6,3) | Tỉ trọng KPI |
| created_by                   | UUID         |              |
| updated_by                   | UUID         |              |
| created_at                   | TIMESTAMP    |              |
| updated_at                   | TIMESTAMP    |              |

Unique:

```sql
UNIQUE(bsc_strategy_id, department_kpi_id)
```

---

# 11. B7 KPI Measurement

## 11.1. kpi_measurements

| Field                        | Type          | Ghi chú                          |
| ---------------------------- | ------------- | -------------------------------- |
| id                           | UUID          | PK                               |
| bsc_strategy_id              | UUID          | FK                               |
| department_kpi_id            | UUID          | FK                               |
| final_strategic_objective_id | UUID          | FK                               |
| department_id                | UUID          | FK                               |
| perspective_code             | VARCHAR(100)  | Denormalized                     |
| unit                         | VARCHAR(100)  | %, VNĐ, giờ...                   |
| baseline_value               | DECIMAL(18,3) | Giá trị hiện tại                 |
| target_value                 | DECIMAL(18,3) | Giá trị cần đạt                  |
| direction                    | VARCHAR(50)   | HIGHER_IS_BETTER/LOWER_IS_BETTER |
| reporting_frequency          | VARCHAR(50)   | DAILY/WEEKLY/MONTHLY/...         |
| formula_description          | TEXT          | Công thức mô tả                  |
| green_threshold              | DECIMAL(6,3)  | Ví dụ 90                         |
| yellow_threshold             | DECIMAL(6,3)  | Ví dụ 70                         |
| red_threshold                | DECIMAL(6,3)  | Ví dụ 0                          |
| report_owner_id              | UUID          | FK → employees.id, nullable      |
| status                       | VARCHAR(50)   | ACTIVE/INACTIVE                  |
| created_by                   | UUID          |                                  |
| updated_by                   | UUID          |                                  |
| created_at                   | TIMESTAMP     |                                  |
| updated_at                   | TIMESTAMP     |                                  |

Unique:

```sql
UNIQUE(bsc_strategy_id, department_kpi_id)
```

MVP không có `target_type`.

---

# 12. B8 Execution / Report

## 12.1. action_plans

| Field                        | Type         | Ghi chú                    |
| ---------------------------- | ------------ | -------------------------- |
| id                           | UUID         | PK                         |
| bsc_strategy_id              | UUID         | FK                         |
| department_kpi_id            | UUID         | FK                         |
| final_strategic_objective_id | UUID         | FK                         |
| department_id                | UUID         | FK                         |
| name                         | VARCHAR(255) | Tên action plan            |
| description                  | TEXT         | Mô tả                      |
| start_date                   | DATE         | Ngày bắt đầu               |
| end_date                     | DATE         | Ngày kết thúc dự kiến      |
| owner_id                     | UUID         | FK → employees.id          |
| priority                     | VARCHAR(50)  | LOW/MEDIUM/HIGH/CRITICAL   |
| status                       | VARCHAR(50)  | DRAFT/ACTIVE/COMPLETED/... |
| created_by                   | UUID         |                            |
| updated_by                   | UUID         |                            |
| created_at                   | TIMESTAMP    |                            |
| updated_at                   | TIMESTAMP    |                            |

---

## 12.2. tasks

| Field                        | Type         | Ghi chú                  |
| ---------------------------- | ------------ | ------------------------ |
| id                           | UUID         | PK                       |
| bsc_strategy_id              | UUID         | FK                       |
| action_plan_id               | UUID         | FK                       |
| department_kpi_id            | UUID         | FK                       |
| final_strategic_objective_id | UUID         | FK                       |
| department_id                | UUID         | FK                       |
| assignee_id                  | UUID         | FK → employees.id        |
| name                         | VARCHAR(255) | Tên task                 |
| description                  | TEXT         | Mô tả                    |
| start_date                   | DATE         | Ngày bắt đầu             |
| due_date                     | DATE         | Deadline                 |
| status                       | VARCHAR(50)  | TODO/IN_PROGRESS/...     |
| progress_percent             | DECIMAL(6,3) | Tiến độ                  |
| priority                     | VARCHAR(50)  | LOW/MEDIUM/HIGH/CRITICAL |
| block_reason                 | TEXT         | Bắt buộc nếu BLOCKED     |
| evidence_url                 | TEXT         | Link minh chứng MVP      |
| created_by                   | UUID         |                          |
| updated_by                   | UUID         |                          |
| created_at                   | TIMESTAMP    |                          |
| updated_at                   | TIMESTAMP    |                          |

Rule:

- Task bắt buộc thuộc action plan.
- Không tạo task tự do.

---

## 12.3. task_dependencies

| Field           | Type        | Ghi chú         |
| --------------- | ----------- | --------------- |
| id              | UUID        | PK              |
| source_task_id  | UUID        | FK → tasks.id   |
| target_task_id  | UUID        | FK → tasks.id   |
| dependency_type | VARCHAR(50) | FINISH_TO_START |
| created_at      | TIMESTAMP   |                 |

Unique:

```sql
UNIQUE(source_task_id, target_task_id)
```

---

## 12.4. task_comments

| Field            | Type         | Ghi chú               |
| ---------------- | ------------ | --------------------- |
| id               | UUID         | PK                    |
| task_id          | UUID         | FK → tasks.id         |
| user_id          | UUID         | FK → user_accounts.id |
| content          | TEXT         | Nội dung comment/log  |
| old_status       | VARCHAR(50)  | Optional              |
| new_status       | VARCHAR(50)  | Optional              |
| progress_percent | DECIMAL(6,3) | Optional              |
| created_at       | TIMESTAMP    |                       |

---

## 12.5. kpi_reports

| Field                        | Type          | Ghi chú                           |
| ---------------------------- | ------------- | --------------------------------- |
| id                           | UUID          | PK                                |
| bsc_strategy_id              | UUID          | FK                                |
| department_kpi_id            | UUID          | FK                                |
| final_strategic_objective_id | UUID          | FK                                |
| department_id                | UUID          | FK                                |
| reporting_period             | VARCHAR(50)   | Ví dụ 2026-01                     |
| actual_value                 | DECIMAL(18,3) | Giá trị thực tế                   |
| completion_rate              | DECIMAL(8,3)  | Có thể vượt 100                   |
| status_color                 | VARCHAR(50)   | GREEN/YELLOW/RED                  |
| achievement_status           | VARCHAR(50)   | IN_PROGRESS/ACHIEVED/EXCEEDED     |
| note                         | TEXT          | Ghi chú                           |
| evidence_url                 | TEXT          | Link minh chứng MVP               |
| reporter_id                  | UUID          | FK → employees.id                 |
| review_status                | VARCHAR(50)   | DRAFT/SUBMITTED/APPROVED/REJECTED |
| reviewed_by                  | UUID          | FK → employees.id, nullable       |
| reviewed_at                  | TIMESTAMP     | Nullable                          |
| submitted_at                 | TIMESTAMP     | Nullable                          |
| created_at                   | TIMESTAMP     |                                   |
| updated_at                   | TIMESTAMP     |                                   |

Unique gợi ý:

```sql
UNIQUE(bsc_strategy_id, department_kpi_id, reporting_period)
```

---

# 13. Enum chính

## UserRole

```text
CEO
DEPARTMENT_HEAD
EMPLOYEE
COMPANY_ADMIN
SYSTEM_ADMIN
```

## BscStrategyStatus

```text
DRAFT
ACTIVE
OPERATING
ARCHIVED
CANCELLED
```

## BscStepCode

```text
B1_ASSESSMENT
B2_STRATEGY_BUILDING
B3_STRATEGY_RESULT
B4_STRATEGY_MAP
B5_FISHBONE
B6_WEIGHT_ALLOCATION
B7_MEASUREMENT_TARGET
B8_ACTION_PLAN
```

## BscStepStatus

```text
LOCKED
NOT_STARTED
IN_PROGRESS
COMPLETED
INVALIDATED
```

## AnalysisModelType

SEVEN_S
FIVE_FORCES
PESTEL

## SevenSFactor

STRATEGY
STRUCTURE
SYSTEMS
SHARED_VALUES
SKILLS
STYLE
STAFF

## FiveForcesFactor

COMPETITIVE_RIVALRY
SUPPLIER_POWER
BUYER_POWER
THREAT_OF_SUBSTITUTES
THREAT_OF_NEW_ENTRANTS

## PestelFactor

POLITICAL
ECONOMIC
SOCIAL
TECHNOLOGICAL
ENVIRONMENTAL
LEGAL

## PerspectiveCode

```text
FINANCIAL
CUSTOMER
INTERNAL_PROCESS
LEARNING_AND_GROWTH
```

## SwotType

```text
S
W
O
T
```

## StrategyGroup

```text
SO
ST
WO
WT
```

## KpiDirection

```text
HIGHER_IS_BETTER
LOWER_IS_BETTER
```

## ReportingFrequency

```text
DAILY
WEEKLY
MONTHLY
QUARTERLY
YEARLY
CUSTOM
```

## StatusColor

```text
GREEN
YELLOW
RED
```

## AchievementStatus

```text
IN_PROGRESS
ACHIEVED
EXCEEDED
```

## TaskStatus

```text
TODO
IN_PROGRESS
REVIEW
DONE
BLOCKED
CANCELLED
```

## Priority

```text
LOW
MEDIUM
HIGH
CRITICAL
```
