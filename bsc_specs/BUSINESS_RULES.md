# Business Rules - BSC SkillForge MVP

## 1. Quy tắc tổng quan

### 1.1. Mọi dữ liệu B1-B8 phải thuộc một BSC Strategy

Tất cả dữ liệu nghiệp vụ chính phải có `bsc_strategy_id` hoặc truy được về `bsc_strategy_id`.

Lý do:

```text
Một công ty có thể có nhiều chu kỳ BSC theo năm/giai đoạn.
```

---

### 1.2. Workflow đi tuần tự

Luồng MVP:

```text
B1 → B2 → B3 → B4 → B5 → B6 → B7 → B8
```

Không cho hoàn thành bước sau nếu bước trước chưa completed.

Ví dụ:

- Chưa completed B1 thì không cho completed B2.
- Chưa completed B5 thì không cho completed B6.
- Chưa completed B7 thì không cho tạo B8 setup completed.

---

### 1.3. Strategy phải ở trạng thái DRAFT khi chỉnh workflow B1-B7

Các bước xây dựng chiến lược B1-B7 chỉ cho chỉnh khi:

```text
bsc_strategy.status = DRAFT
```

Sau khi strategy chuyển ACTIVE/OPERATING, các bước setup chiến lược bị hạn chế chỉnh sửa.

---

### 1.4. Role MVP

MVP role chính:

| Role | Quyền chính |
|---|---|
| CEO | Thao tác B1-B4, B6-B7, xem toàn bộ |
| DEPARTMENT_HEAD | Thao tác B5, B8 cho phòng ban mình |
| EMPLOYEE | Xem/cập nhật task được giao |
| COMPANY_ADMIN | Setup dữ liệu nền |
| SYSTEM_ADMIN | Monitoring, không xem dữ liệu chiến lược chi tiết |

MVP có thể implement role đơn giản trước, permission chi tiết làm sau.

---

# 2. Rules BSC Strategy

## 2.1. Khi tạo BSC Strategy

Khi tạo strategy mới:

- Strategy status = DRAFT
- Sinh 8 step status:
  - B1_ASSESSMENT = NOT_STARTED
  - B2-B8 = LOCKED

## 2.2. Hoàn thành step

Khi một step completed:

- Update current step = COMPLETED
- Unlock next step = NOT_STARTED

Ví dụ:

```text
Complete B1
→ B1 = COMPLETED
→ B2 = NOT_STARTED
```

## 2.3. Invalidated

Nếu dữ liệu bước trước thay đổi làm bước sau không còn hợp lệ:

- Mark bước sau = INVALIDATED
- Không tự xóa dữ liệu ngay trong MVP
- User phải kiểm tra/chỉnh lại

Ví dụ:

```text
B4 final objective thay đổi
→ B5/B6/B7/B8 có thể INVALIDATED
```

---

# 3. Rules B1 - Assessment

## 3.1. Financial

- Có ít nhất 1 dòng financial để completed B1.
- Tối đa 3 dòng financial.
- `revenue >= 0`.
- `profit` có thể âm.
- Không trùng năm trong cùng strategy.

## 3.2. Market Share

- Có dữ liệu CURRENT.
- Có dữ liệu FUTURE.
- Tổng market share CURRENT = 100.
- Tổng market share FUTURE = 100.
- Mỗi period phải có ít nhất một item `is_own_company = true`.
- `market_share_percent >= 0`.
- `market_share_percent <= 100`.

## 3.3. Text Items

- `content` không được rỗng.
- Trim content trước khi lưu.
- Category phải thuộc enum hợp lệ.

## 3.4. Complete B1

Điều kiện complete:

- Strategy DRAFT.
- User là CEO hoặc quyền tương đương.
- Financial valid.
- Market share valid.
- Text item không rỗng.

---

# 4. Rules B2 - Strategy Building

## 4.1. Analysis Items

- `model_type` phải thuộc:
  - SEVEN_S
  - FIVE_FORCES
  - PESTEL
- `factor_code` phải hợp lệ theo model type.
- Content không rỗng.

## 4.2. SWOT Selection

Nguồn chọn:

| SWOT | Nguồn |
|---|---|
| S | SEVEN_S |
| W | SEVEN_S |
| O | FIVE_FORCES hoặc PESTEL |
| T | FIVE_FORCES hoặc PESTEL |

Rules:

- Một `analysis_item` chỉ được chọn vào một SWOT item.
- Item 7S không được vừa là S vừa là W.
- Item 5 Forces/PESTEL không được vừa là O vừa là T.

## 4.3. Candidate Strategy

Strategy group:

```text
SO, ST, WO, WT
```

Rules:

- Tổng candidate strategy mỗi BSC Strategy không quá 12.
- Strategy name không rỗng.
- Strategy group hợp lệ.
- SWOT item được chọn phải thuộc cùng BSC Strategy.
- Một SWOT item chỉ được dùng tối đa một lần trên toàn bộ danh sách candidate strategy.

## 4.4. Rule theo strategy group

### SO

- Có ít nhất 1 S.
- Có đúng 1 O.

### ST

- Có ít nhất 1 S.
- Có đúng 1 T.

### WO

- Có ít nhất 1 W.
- Có đúng 1 O.

### WT

- Có ít nhất 1 W.
- Có đúng 1 T.

## 4.5. Complete B2

Điều kiện:

- B1 COMPLETED.
- Strategy DRAFT.
- Có ít nhất 1 candidate strategy.
- Tổng candidate strategy <= 12.
- Không có SWOT item bị dùng nhiều hơn 1 lần.

---

# 5. Rules B3 - Strategy Selection

## 5.1. Chọn strategy

- Chọn tối thiểu 1 candidate strategy.
- Chọn tối đa 2 candidate strategies.
- Candidate strategy phải thuộc BSC Strategy hiện tại.
- Candidate strategy chưa bị xóa.
- Không chọn trùng.
- Priority order không trùng.

## 5.2. Complete B3

Điều kiện:

- B2 COMPLETED.
- Có 1-2 selected strategies.
- Strategy DRAFT.
- User là CEO hoặc quyền tương đương.

---

# 6. Rules B4 - Strategy Map

## 6.1. Strategic Objective

- Objective name không rỗng.
- Perspective code bắt buộc.
- Perspective code thuộc 4 góc độ:
  - FINANCIAL
  - CUSTOMER
  - INTERNAL_PROCESS
  - LEARNING_AND_GROWTH
- Objective phải thuộc selected strategy hiện tại.
- Mỗi selected strategy tối đa 12 objectives.
- Mỗi selected strategy phải có đủ 4 perspective để complete B4.

## 6.2. Objective Link

- Source objective không được bằng target objective.
- Không tạo duplicate link cùng source-target trong cùng map.
- Source và target phải thuộc cùng strategy map.

## 6.3. Final Objectives

- Final objective name không rỗng.
- Final objective phải thuộc BSC Strategy hiện tại.
- Final map phải có đủ 4 perspective.
- Nếu chọn 1 strategy: có thể copy objective riêng thành final objective.
- Nếu chọn 2 strategy: final objective được tạo từ keep/merge/edit.
- Merge MVP chỉ nên cho merge objectives cùng perspective.

## 6.4. Final Objective Link

- Source final objective không được bằng target final objective.
- Không tạo duplicate link.
- Source và target phải thuộc BSC Strategy hiện tại.

## 6.5. Complete B4

Điều kiện:

- B3 COMPLETED.
- Có final objectives.
- Final objectives có đủ 4 perspective.
- Không có objective/link invalid.

---

# 7. Rules B5 - Fishbone / Department KPI

## 7.1. Department Participation

Quan hệ:

```text
Department N-N Final Strategic Objective
```

Rules:

- B4 phải COMPLETED.
- Department phải tồn tại.
- Final objective phải thuộc BSC Strategy hiện tại.
- Không join trùng department + final objective trong cùng BSC Strategy.
- User thao tác nên là trưởng phòng của department đó.

## 7.2. Department KPI

Rules:

- KPI name không rỗng.
- KPI phải thuộc một department participation hợp lệ.
- KPI phải thuộc department của trưởng phòng đang thao tác.
- KPI phải gắn với final objective mà department đã join.
- Không tạo KPI tự do ngoài final objective.
- Không tạo KPI cho department khác nếu không có quyền.

## 7.3. Complete B5

Điều kiện đề xuất MVP:

- B4 COMPLETED.
- Mỗi final objective có ít nhất 1 department participation.
- Mỗi final objective có ít nhất 1 department KPI.
- Không có department participation mà chưa có KPI.
- Strategy DRAFT.

Nếu deadline gấp, có thể nới rule:

- Chỉ cần có ít nhất 1 KPI để đi tiếp B6.

Nhưng khuyến nghị vẫn dùng rule strict để dashboard không thiếu dữ liệu.

---

# 8. Rules B6 - Weight Allocation

## 8.1. Nguyên tắc cốt lõi

Tất cả weight là **tỉ trọng tuyệt đối trên tổng 100%**.

Không chia lại 100% ở tầng con.

Ví dụ đúng:

```text
Financial = 40%
Objective A trong Financial = 20%
Objective B trong Financial = 12%
Objective C trong Financial = 8%
Tổng objective Financial = 40%
```

## 8.2. Perspective Weight

Rules:

- Có đủ 4 perspective.
- Mỗi perspective có weight > 0.
- Tổng 4 perspective = 100.

## 8.3. Objective Weight

Rules:

- Mỗi final objective có đúng 1 weight.
- Weight > 0.
- Objective weight không vượt perspective weight cha.
- Tổng objective weight theo từng perspective = perspective weight.

## 8.4. KPI Weight

Rules:

- Mỗi department KPI có đúng 1 weight.
- Weight > 0.
- KPI weight không vượt objective weight cha.
- Tổng KPI weight trong từng final objective = objective weight.

## 8.5. Complete B6

Điều kiện:

- B5 COMPLETED.
- Perspective weight valid.
- Objective weight valid.
- KPI weight valid.
- Dùng BigDecimal để validate tổng.

---

# 9. Rules B7 - KPI Measurement & Target

## 9.1. Measurement

Rules:

- B6 phải COMPLETED.
- Mỗi department KPI đã có weight phải có measurement.
- Một KPI chỉ có một active measurement trong MVP.
- Unit bắt buộc.
- Target value bắt buộc.
- Direction bắt buộc.
- Reporting frequency bắt buộc.
- Threshold hợp lệ.

## 9.2. Baseline / Target MVP

MVP hiểu:

```text
Baseline = giá trị hiện tại
Target = giá trị cần đạt trong chu kỳ BSC
```

Ví dụ:

```text
KPI: Tăng tỷ lệ chốt đơn
Baseline = 20%
Target = 35%
```

Khi actual >= target:

```text
achievement_status = ACHIEVED
```

Khi actual > target:

```text
achievement_status = EXCEEDED
```

Vẫn cho phép tiếp tục báo cáo sau khi đạt target.

## 9.3. Direction

### HIGHER_IS_BETTER

```text
completion_rate = actual_value / target_value * 100
```

### LOWER_IS_BETTER

```text
completion_rate = target_value / actual_value * 100
```

Cần xử lý case actual hoặc target = 0 để tránh chia cho 0.

## 9.4. Threshold

MVP rule:

```text
GREEN nếu completion_rate >= green_threshold
YELLOW nếu completion_rate >= yellow_threshold và < green_threshold
RED nếu completion_rate < yellow_threshold
```

Default:

```text
green_threshold = 90
yellow_threshold = 70
red_threshold = 0
```

## 9.5. Complete B7

Điều kiện:

- B6 COMPLETED.
- Mọi KPI có measurement.
- Measurement valid.

---

# 10. Rules B8 - Action Plan / Task / KPI Report

## 10.1. Action Plan

Rules:

- B7 phải COMPLETED.
- Action plan phải thuộc department KPI.
- Department KPI phải thuộc department của trưởng phòng đang thao tác.
- Name không rỗng.
- Start date <= end date.
- Owner hợp lệ.

## 10.2. Task

Rules:

- Task phải thuộc action plan.
- Không tạo task tự do.
- Task tự suy ra KPI/objective/department từ action plan.
- Name không rỗng.
- Assignee hợp lệ.
- Assignee thuộc cùng department trong MVP.
- Start date <= due date.
- Status hợp lệ.
- Nếu status = BLOCKED thì block_reason bắt buộc.

## 10.3. Task Status Transition

Flow MVP:

```text
TODO → IN_PROGRESS
IN_PROGRESS → REVIEW
REVIEW → DONE
REVIEW → IN_PROGRESS
IN_PROGRESS → BLOCKED
BLOCKED → IN_PROGRESS
TODO/IN_PROGRESS/REVIEW → CANCELLED
```

Rules quyền đơn giản:

- Employee cập nhật task của mình.
- Department head review task của phòng mình.
- Department head chuyển REVIEW → DONE.

## 10.4. KPI Report

Rules:

- KPI phải tồn tại.
- KPI phải có measurement.
- Actual value bắt buộc.
- Actual value là số.
- Reporting period không rỗng.
- Không tạo trùng report cùng KPI + period trong MVP.
- Reporter có quyền.

Khi tạo report, server tính:

```text
completion_rate
status_color
achievement_status
```

## 10.5. Completion Rate

### HIGHER_IS_BETTER

```text
completion_rate = actual_value / target_value * 100
```

### LOWER_IS_BETTER

```text
completion_rate = target_value / actual_value * 100
```

## 10.6. Achievement Status

MVP:

```text
if completion_rate > 100:
    EXCEEDED
elif completion_rate >= 100:
    ACHIEVED
else:
    IN_PROGRESS
```

## 10.7. Weighted Score

Dashboard tính:

```text
score_completion = min(completion_rate, 100)
weighted_score = weight_percent * score_completion / 100
```

Ví dụ:

```text
weight = 10
completion_rate = 80
weighted_score = 8
```

Nếu completion_rate = 132:

```text
score_completion = 100
weighted_score = 10
raw completion vẫn hiển thị 132%
```

---

# 11. Dashboard Rules

## 11.1. KPI Score

Dữ liệu cần:

- kpi_weights.weight_percent
- kpi_measurements.target_value
- kpi_measurements.direction
- latest/approved kpi_report.actual_value

Công thức:

```text
completion_rate = theo direction
score_completion = min(completion_rate, 100)
weighted_score = weight_percent * score_completion / 100
```

## 11.2. Objective Score

Group KPI weighted score theo:

```text
final_strategic_objective_id
```

## 11.3. Perspective Score

Group KPI weighted score theo:

```text
perspective_code
```

## 11.4. Company Score

Tổng toàn bộ KPI weighted score trong BSC Strategy.

## 11.5. Task Progress

Action plan progress MVP:

```text
Done tasks / total valid tasks * 100
```

KPI work progress MVP:

```text
Done tasks under KPI / total valid tasks under KPI * 100
```

Lưu ý:

```text
KPI Performance khác Task Progress.
```

- KPI Performance = actual/target.
- Task Progress = mức độ hoàn thành công việc.

---

# 12. Invalidated Rules MVP

## 12.1. B2 thay đổi sau B3

Nếu candidate strategy đã selected bị xóa:

- Mark B3 INVALIDATED.
- Nếu B4 đã có dữ liệu, mark B4 INVALIDATED.

## 12.2. B4 thay đổi sau B5

Nếu final objective bị xóa:

- Mark B5 INVALIDATED.
- Mark B6/B7/B8 INVALIDATED nếu đã completed.

## 12.3. B5 thay đổi sau B6

Nếu KPI bị thêm/xóa:

- Mark B6 INVALIDATED.
- Mark B7/B8 INVALIDATED nếu cần.

## 12.4. B6 thay đổi sau B7

Nếu chỉ thay đổi weight, KPI ID không đổi:

- Không cần invalid B7.
- Dashboard tính lại theo weight mới.

Nếu KPI mất weight:

- Mark B7 INVALIDATED.

## 12.5. B7 thay đổi sau B8

Nếu target thay đổi:

- KPI report cũ giữ lịch sử.
- Dashboard có thể tính theo target mới cho report mới.
- MVP có thể chưa xử lý lịch sử phức tạp.

---

# 13. MVP Simplification Notes

Các điểm cố ý đơn giản trong MVP:

- Chưa có target_type.
- Chưa có forecast on-track/behind-track.
- Chưa có formula engine.
- Evidence chỉ lưu URL text.
- Permission dùng role đơn giản.
- Dashboard tính từ latest report hoặc approved report gần nhất.
- Không hard delete dữ liệu quan trọng nếu có liên kết, ưu tiên status DELETED/REMOVED.
