# MVP Scope - BSC SkillForge Backend

## 1. Mục tiêu MVP

MVP của BSC SkillForge tập trung xây dựng được luồng nghiệp vụ đầy đủ từ **thiết lập công ty → xây dựng 8 bước BSC → vận hành KPI bằng action plan/task/report/dashboard cơ bản**.

Mục tiêu không phải làm một hệ thống hoàn hảo ngay từ đầu, mà là tạo được một phiên bản có thể demo end-to-end, có dữ liệu thật, có workflow rõ ràng và có thể mở rộng sau.

Luồng MVP:

```text
Company Setup
→ BSC Strategy Cycle
→ B1 Assessment
→ B2 Strategy Building
→ B3 Strategy Selection
→ B4 Strategy Map
→ B5 Department KPI / Fishbone
→ B6 Weight Allocation
→ B7 KPI Measurement & Target
→ B8 Action Plan / Task / KPI Report
→ Dashboard Basic
```

---

## 2. Nguyên tắc MVP

### 2.1. Ưu tiên end-to-end workflow

MVP phải chạy được toàn bộ flow B1 đến B8 ở mức cơ bản.

Không ưu tiên các tính năng nâng cao như forecast, notification, audit log phức tạp, formula engine hay file storage thật.

### 2.2. Mỗi dữ liệu phải trace được về chiến lược

Mọi dữ liệu chính phải gắn với `bsc_strategy_id` để hệ thống truy ngược được:

```text
Task
→ Action Plan
→ Department KPI
→ Final Strategic Objective
→ Perspective
→ BSC Strategy
→ Company
```

### 2.3. Task không được tạo tự do

Task trong MVP bắt buộc đi theo chain:

```text
Department KPI
→ Action Plan
→ Task
```

Không cho tạo task không liên kết KPI.

### 2.4. KPI target đơn giản trước

MVP chưa cần `target_type` phức tạp.

Mặc định hiểu:

```text
Baseline = giá trị hiện tại trước khi triển khai
Target = giá trị cần đạt của KPI trong chu kỳ BSC hiện tại
```

Khi báo cáo actual:

```text
actual >= target → KPI đạt target
actual > target  → KPI vượt target
```

Dù KPI đã đạt hoặc vượt target, hệ thống vẫn cho phép tiếp tục báo cáo các kỳ sau.

---

## 3. Scope bắt buộc làm trong MVP

## 3.1. Company Setup

Bắt buộc có:

- Tạo công ty
- Tạo phòng ban
- Tạo nhân viên
- Gán nhân viên vào phòng ban
- Tạo tài khoản đăng nhập cơ bản
- Role cơ bản:
  - CEO
  - DEPARTMENT_HEAD
  - EMPLOYEE
  - COMPANY_ADMIN
  - SYSTEM_ADMIN

MVP có thể đơn giản:

- Chưa cần phân quyền chi tiết theo permission matrix.
- Có thể dùng role enum trước.
- Có thể dùng login basic/JWT đơn giản.

---

## 3.2. BSC Strategy Cycle

Bắt buộc có:

- Tạo BSC Strategy mới cho công ty
- Trạng thái strategy:
  - DRAFT
  - ACTIVE
  - OPERATING
  - ARCHIVED
- Khi tạo strategy, hệ thống sinh 8 step status:
  - B1 NOT_STARTED
  - B2-B8 LOCKED
- Hoàn thành step trước thì mở step sau.

---

## 3.3. B1 - Assessment

Bắt buộc có:

- Nhập tài chính tối đa 3 năm
- Nhập thị phần hiện tại/tương lai
- Nhập dynamic text list:
  - Phân khúc hiện tại
  - Phân khúc tương lai
  - Sản phẩm chủ lực hiện tại
  - Sản phẩm chủ lực tương lai
  - Điểm mạnh công ty
  - Yếu tố thành công trong ngành
  - Điểm mạnh đối thủ
  - Điểm yếu đối thủ
  - Lợi thế cạnh tranh

Validate khi complete B1:

- Có ít nhất 1 dòng tài chính
- Tài chính không vượt quá 3 năm
- Revenue không âm
- Current market share tổng 100%
- Future market share tổng 100%
- Mỗi market share period phải có công ty của mình
- Không có text item rỗng

---

## 3.4. B2 - Strategy Building

Bắt buộc có:

- Nhập analysis item cho:
  - 7S
  - 5 Forces
  - PESTEL
- Chọn SWOT item từ analysis item
- Tạo strategy SO/ST/WO/WT
- Mapping strategy với SWOT item đã dùng

Validate MVP:

- Có ít nhất 1 item S/W/O/T nếu muốn strict
- Có ít nhất 1 candidate strategy
- Tổng candidate strategy không quá 12
- Một SWOT item chỉ được dùng tối đa một lần trên toàn bộ strategy

---

## 3.5. B3 - Strategy Selection

Bắt buộc có:

- CEO chọn strategy ứng viên từ B2
- Chọn tối thiểu 1 strategy
- Chọn tối đa 2 strategy
- Lưu thứ tự ưu tiên
- Lưu lý do chọn nếu có

---

## 3.6. B4 - Strategy Map

Bắt buộc có:

- Tạo strategy map cho strategy đã chọn
- Tạo strategic objective theo 4 góc độ BSC:
  - FINANCIAL
  - CUSTOMER
  - INTERNAL_PROCESS
  - LEARNING_AND_GROWTH
- Tạo link nhân quả giữa objectives
- Chốt final strategic objectives
- Tạo final objective links

MVP xử lý đơn giản:

- Nếu chọn 1 strategy: copy objectives từ strategy map sang final objectives.
- Nếu chọn 2 strategy: hỗ trợ manual keep/merge/edit ở mức API/data, UI có thể làm đơn giản.

Validate:

- Mỗi selected strategy có đủ 4 perspective
- Mỗi strategy tối đa 12 objectives
- Final map có đủ 4 perspective
- Không tạo link tự trỏ
- Không tạo link trùng

---

## 3.7. B5 - Fishbone / Department KPI

Bắt buộc có:

- Trưởng phòng xem final strategic objectives từ B4
- Trưởng phòng chọn objectives mà phòng ban mình tham gia
- Hệ thống lưu department participation
- Trưởng phòng tạo KPI nhỏ cho phòng ban trong objective đã join
- CEO xem fishbone tổng công ty
- Trưởng phòng xem fishbone phòng ban

Quan hệ quan trọng:

```text
Department N-N Final Strategic Objective
qua department_participations
```

Validate:

- Department chỉ join objective thuộc BSC Strategy hiện tại
- Không join trùng department + objective
- KPI phải thuộc objective mà department đã join
- KPI phải thuộc department của trưởng phòng

---

## 3.8. B6 - Weight Allocation

Bắt buộc có:

- CEO phân bổ trọng số cho 4 perspective
- CEO phân bổ trọng số cho final objectives
- CEO phân bổ trọng số cho department KPIs

Nguyên tắc:

```text
Tỉ trọng là tỉ trọng tuyệt đối trên tổng 100%.
Không chia lại 100% ở tầng con.
```

Validate:

- Tổng 4 perspective = 100
- Tổng objective theo từng perspective = perspective weight
- Tổng KPI theo từng final objective = objective weight
- Mỗi weight > 0
- Dùng BigDecimal/DECIMAL, không dùng float/double

---

## 3.9. B7 - KPI Measurement & Target

Bắt buộc có:

- CEO nhập measurement cho từng department KPI đã có weight
- Field tối thiểu:
  - unit
  - baseline_value
  - target_value
  - direction
  - reporting_frequency
  - formula_description
  - green_threshold
  - yellow_threshold
  - red_threshold

MVP mặc định:

```text
Target là mục tiêu cần đạt của KPI trong chu kỳ BSC hiện tại.
```

Khi actual đạt target:

```text
KPI status = ACHIEVED
```

Nếu actual vượt target:

```text
KPI status = EXCEEDED
```

Nhưng vẫn cho tiếp tục báo cáo.

---

## 3.10. B8 - Action Plan / Task / KPI Report

Bắt buộc có:

- Trưởng phòng tạo action plan cho KPI của phòng ban mình
- Tạo task trong action plan
- Giao task cho nhân viên
- Cập nhật trạng thái task
- Ghi log task comment/progress
- Nhập KPI report actual value theo kỳ
- Tính completion rate
- Tính status color

Task status MVP:

- TODO
- IN_PROGRESS
- REVIEW
- DONE
- BLOCKED
- CANCELLED

Action Plan status MVP:

- DRAFT
- ACTIVE
- COMPLETED
- CANCELLED
- ON_HOLD

KPI Report status MVP:

- DRAFT
- SUBMITTED
- APPROVED
- REJECTED

---

## 3.11. Dashboard Basic

Bắt buộc có:

- Company score
- Score theo 4 perspective
- Score theo final objective
- KPI completion rate
- KPI weighted score
- KPI xanh/vàng/đỏ
- KPI IN_PROGRESS/ACHIEVED/EXCEEDED
- Task count theo status
- Task overdue
- Action plan progress cơ bản

Công thức MVP:

```text
HIGHER_IS_BETTER:
completion_rate = actual_value / target_value * 100

LOWER_IS_BETTER:
completion_rate = target_value / actual_value * 100

score_completion = min(completion_rate, 100)
weighted_score = weight_percent * score_completion / 100
```

Dashboard vẫn hiển thị raw completion rate vượt 100%, nhưng điểm đóng góp vào BSC nên cap ở 100%.

---

## 4. Out of Scope cho MVP

Các phần chưa làm ở MVP:

- Target type phức tạp: PER_PERIOD, FINAL_BY_DEADLINE, CUMULATIVE
- Forecast on-track/behind-track
- Notification/reminder báo cáo KPI
- Audit log đầy đủ toàn hệ thống
- Formula engine tự động tính KPI từ nguồn dữ liệu khác
- File upload evidence thật qua MinIO/S3
- Realtime collaboration
- Permission matrix chi tiết
- Multi-tenant phức tạp
- Soft delete/archive nâng cao cho toàn bộ bảng
- Dashboard trend nâng cao
- Gantt UI nâng cao
- Task dependency cycle detection nâng cao nếu không kịp

---

## 5. Definition of Done MVP

MVP được xem là đạt khi demo được flow:

```text
1. Tạo công ty, phòng ban, nhân viên
2. Tạo BSC Strategy Draft
3. CEO hoàn thành B1
4. CEO hoàn thành B2
5. CEO chọn strategy ở B3
6. CEO tạo final strategic objectives ở B4
7. Trưởng phòng join objectives và tạo KPI ở B5
8. CEO phân bổ weight ở B6
9. CEO nhập measurement/target ở B7
10. Trưởng phòng tạo action plan/task ở B8
11. Trưởng phòng nhập actual KPI report
12. Dashboard hiển thị score và trạng thái KPI/task
```
