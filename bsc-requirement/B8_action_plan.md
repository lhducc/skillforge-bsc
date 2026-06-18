# B8. Action Plan

## 1. Mục tiêu của bước

Bước **Action Plan** là bước biến KPI nhỏ đã có tỉ trọng, chỉ tiêu và cách đo thành các kế hoạch hành động cụ thể để phòng ban và nhân viên thực thi.

Sau B7, hệ thống đã có đầy đủ:

- Mục tiêu chiến lược tổng từ B4
- KPI nhỏ của phòng ban từ B5
- Tỉ trọng KPI từ B6
- Đơn vị đo, target, kỳ báo cáo và cách đánh giá KPI từ B7

Sang B8, hệ thống bắt đầu chuyển từ giai đoạn **xây dựng chiến lược** sang giai đoạn **thực thi chiến lược**.

Nguyên tắc quan trọng nhất của B8:

```text
Task không được tạo tự do.
Task phải liên kết với KPI.
```

Nói cách khác, mọi công việc trong B8 đều phải trả lời được câu hỏi:

```text
Task này phục vụ KPI nào?
KPI đó thuộc mục tiêu chiến lược nào?
Mục tiêu đó thuộc góc độ BSC nào?
```

Nhờ vậy CEO có thể theo dõi ngược từ task của nhân viên lên KPI, từ KPI lên mục tiêu chiến lược, từ mục tiêu chiến lược lên góc độ BSC và cuối cùng là tình trạng toàn công ty.

---

## 2. Vai trò của B8 trong toàn workflow BSC

Luồng dữ liệu tổng thể:

```text
B4. Bản đồ chiến lược tổng
→ B5. Mô hình xương cá
→ B6. Phân bổ tỉ trọng
→ B7. Đo lường & Chỉ tiêu
→ B8. Action Plan
→ Dashboard vận hành
```

B8 là nơi hệ thống bắt đầu vận hành như một nền tảng quản lý tiến độ.

Ở B8:

- Trưởng phòng tạo action plan cho KPI của phòng ban mình
- Trưởng phòng chia action plan thành task cụ thể
- Trưởng phòng giao task cho nhân viên
- Nhân viên cập nhật tiến độ task
- Nhân viên hoặc trưởng phòng cập nhật báo cáo KPI theo kỳ
- CEO xem dashboard tổng quan và drill-down xuống từng layer khi có vấn đề

B8 giúp hệ thống đi từ:

```text
Chiến lược
→ KPI
→ Kế hoạch hành động
→ Task
→ Báo cáo thực tế
→ Dashboard
```

---

## 3. Role được phép thao tác

## 3.1. CEO

CEO được phép:

- Xem toàn bộ action plan của công ty
- Xem toàn bộ task liên quan đến KPI chiến lược
- Xem Kanban tổng công ty
- Xem Gantt tổng công ty
- Xem dashboard tiến độ
- Xem KPI nào đang xanh/vàng/đỏ
- Drill-down từ công ty xuống góc độ BSC, mục tiêu chiến lược, KPI, phòng ban, action plan và task
- Xem lý do task bị block hoặc quá hạn
- Xem minh chứng báo cáo KPI nếu có

CEO không nên thao tác trực tiếp:

- Không tạo task thay trưởng phòng
- Không sửa task thay nhân viên/trưởng phòng
- Không cập nhật tiến độ task thay nhân viên
- Không cập nhật actual KPI thay phòng ban nếu không có quyền đặc biệt

Lý do: B8 là giai đoạn vận hành của phòng ban và nhân viên. CEO chủ yếu theo dõi, phân tích và ra quyết định quản trị.

---

## 3.2. Trưởng phòng

Trưởng phòng là người thao tác chính ở cấp phòng ban trong B8.

Trưởng phòng được phép:

- Xem các KPI của phòng ban mình từ B5/B6/B7
- Tạo action plan cho từng KPI của phòng ban mình
- Chỉnh sửa action plan khi còn hợp lệ
- Tạo task trong action plan
- Giao task cho nhân viên trong phòng ban
- Thiết lập deadline, ngày bắt đầu, độ ưu tiên và phụ thuộc task
- Theo dõi Kanban của phòng ban
- Theo dõi Gantt của phòng ban
- Review tiến độ task
- Cập nhật hoặc xác nhận báo cáo KPI theo kỳ
- Gửi báo cáo KPI lên hệ thống nếu workflow yêu cầu
- Xem cảnh báo KPI/task bị trễ, bị block hoặc thiếu báo cáo

Trưởng phòng không được phép:

- Tạo action plan cho KPI của phòng ban khác
- Giao task cho nhân viên phòng ban khác nếu không được cấp quyền
- Sửa KPI gốc từ B5
- Sửa tỉ trọng từ B6
- Sửa target/cách đo từ B7
- Xem dữ liệu chiến lược bí mật B1-B4 nếu không được CEO cấp quyền

---

## 3.3. Nhân viên

Nhân viên là người thực thi task và cập nhật tiến độ.

Nhân viên được phép:

- Xem task được giao cho mình
- Xem KPI liên quan đến task của mình
- Xem action plan chứa task của mình
- Cập nhật trạng thái task
- Cập nhật phần trăm tiến độ task nếu được yêu cầu
- Ghi chú tiến độ
- Đính kèm minh chứng công việc
- Báo cáo vấn đề hoặc lý do bị block
- Xem Kanban cá nhân
- Xem Gantt cá nhân hoặc timeline công việc cá nhân

Nhân viên không được phép:

- Tạo KPI mới
- Sửa KPI
- Sửa tỉ trọng
- Sửa target KPI
- Tạo task tự do không liên kết KPI
- Xem dữ liệu chiến lược bí mật B1-B4
- Xem task/KPI của phòng ban khác nếu không được cấp quyền

---

## 3.4. Company Admin

Company Admin chủ yếu setup dữ liệu nền.

Company Admin không nên thao tác trực tiếp vào B8 nếu không được cấp quyền đặc biệt.

---

## 3.5. System Admin

System Admin thuộc bên cung cấp nền tảng.

System Admin chỉ monitoring hệ thống, không xem nội dung chiến lược, KPI, action plan hoặc task chi tiết của doanh nghiệp.

---

## 4. Điều kiện truy cập bước

B8 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- Công ty đã có phòng ban
- Công ty đã có nhân viên
- Công ty đã có trưởng phòng
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B3 - Kết quả chiến lược đã hoàn thành
- B4 - Bản đồ chiến lược đã hoàn thành
- B5 - Mô hình xương cá đã hoàn thành
- B6 - Phân bổ tỉ trọng đã hoàn thành
- B7 - Đo lường & Chỉ tiêu đã hoàn thành
- KPI nhỏ đã có tỉ trọng
- KPI nhỏ đã có target và cách đo
- Người thao tác có quyền phù hợp

Sau khi B8 được setup xong, hệ thống có thể chuyển BSC Strategy từ trạng thái Draft sang trạng thái Active/Operating để bước vào giai đoạn vận hành.

---

## 5. Khái niệm chính trong B8

## 5.1. KPI nhỏ

KPI nhỏ là KPI do trưởng phòng tạo ở B5, được CEO phân bổ tỉ trọng ở B6 và thiết lập đo lường ở B7.

Trong B8, KPI nhỏ là gốc để tạo action plan.

Không có KPI thì không có action plan.

```text
Department KPI
→ Action Plan
→ Task
```

---

## 5.2. Action Plan

Action Plan là kế hoạch hành động để đạt một KPI cụ thể.

Một KPI có thể có một hoặc nhiều action plan.

Ví dụ:

```text
KPI:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Action Plan 1:
Chuẩn hóa quy trình xử lý ticket

Action Plan 2:
Thiết lập hệ thống phân loại ticket theo mức độ ưu tiên
```

Action Plan giúp gom các task có cùng mục tiêu hành động lại với nhau.

---

## 5.3. Task

Task là công việc cụ thể được giao cho nhân viên hoặc trưởng phòng để thực hiện action plan.

Một task bắt buộc phải thuộc một action plan.

Một action plan bắt buộc phải thuộc một KPI.

Quan hệ bắt buộc:

```text
KPI
→ Action Plan
→ Task
```

Không cho tạo task đứng riêng không liên kết KPI.

---

## 5.4. Kanban

Kanban là cách hiển thị task theo trạng thái công việc.

Ví dụ các cột Kanban:

```text
Todo
→ In Progress
→ Review
→ Done
```

Có thể bổ sung:

```text
Blocked
Overdue
Cancelled
```

Kanban giúp trưởng phòng và nhân viên biết công việc đang nằm ở trạng thái nào.

---

## 5.5. Gantt

Gantt là cách hiển thị task theo timeline.

Gantt giúp xem:

- Task bắt đầu ngày nào
- Task kết thúc ngày nào
- Task nào bị trễ
- Task nào phụ thuộc task nào
- Action plan nào đang có nguy cơ trễ
- KPI nào có nhiều task trễ

Kanban và Gantt không phải hai nguồn dữ liệu khác nhau. Chúng là hai cách hiển thị khác nhau của cùng một danh sách task.

```text
Task data
→ Kanban view
→ Gantt view
```

---

## 5.6. Báo cáo KPI thực tế

Báo cáo KPI thực tế là dữ liệu actual value được cập nhật theo kỳ báo cáo đã định nghĩa ở B7.

Ví dụ:

```text
KPI:
Tăng tỷ lệ chốt đơn

Target:
35%

Kỳ báo cáo:
Monthly

Actual tháng 01:
28%
```

Báo cáo KPI thực tế dùng để dashboard tính KPI đạt bao nhiêu phần trăm so với target.

---

## 5.7. Work Progress và KPI Performance

B8 cần phân biệt hai loại tiến độ:

### Work Progress

Work Progress là tiến độ công việc/task.

Ví dụ:

```text
Action Plan có 10 task
7 task đã Done
→ Work Progress = 70%
```

### KPI Performance

KPI Performance là kết quả thực tế của KPI so với target.

Ví dụ:

```text
Target tỷ lệ chốt đơn = 35%
Actual hiện tại = 28%
→ KPI Performance = 80%
```

Hai loại tiến độ này không giống nhau.

Một team có thể hoàn thành nhiều task nhưng KPI vẫn chưa đạt. Điều này giúp CEO phát hiện vấn đề:

```text
Team làm nhiều nhưng kết quả chưa cải thiện
→ Có thể action plan chưa đúng
→ Có thể KPI target quá cao
→ Có thể vấn đề nằm ở yếu tố khác
```

---

## 6. User flow tổng quan của B8

## 6.1. Trưởng phòng tạo action plan cho KPI

Luồng:

```text
Trưởng phòng đăng nhập
→ Vào B8 - Action Plan
→ Hệ thống hiển thị KPI của phòng ban mình
→ Trưởng phòng chọn một KPI
→ Trưởng phòng tạo action plan cho KPI đó
→ Trưởng phòng chia action plan thành các task
→ Trưởng phòng giao task cho nhân viên
```

Ví dụ:

```text
KPI:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Action Plan:
Chuẩn hóa quy trình xử lý ticket

Task:
- Rà soát quy trình hiện tại
- Viết tài liệu quy trình mới
- Tạo template phản hồi khách hàng
- Training nhân viên CSKH
- Theo dõi thời gian phản hồi mỗi tuần
```

---

## 6.2. Nhân viên nhận task và cập nhật tiến độ

Luồng:

```text
Nhân viên đăng nhập
→ Vào My Tasks
→ Xem task được giao
→ Chuyển trạng thái task
→ Cập nhật tiến độ
→ Ghi chú kết quả
→ Đính kèm minh chứng nếu cần
→ Submit task để trưởng phòng review
```

Ví dụ trạng thái:

```text
Todo
→ In Progress
→ Review
→ Done
```

Nếu bị nghẽn:

```text
In Progress
→ Blocked
→ Nhập lý do bị block
```

---

## 6.3. Trưởng phòng review task

Luồng:

```text
Trưởng phòng vào Kanban phòng ban
→ Xem task đang Review
→ Kiểm tra ghi chú và minh chứng
→ Approve task thành Done
hoặc
→ Yêu cầu chỉnh sửa và trả về In Progress
```

Review task giúp đảm bảo nhân viên không tự đánh dấu hoàn thành công việc không đúng thực tế.

---

## 6.4. Trưởng phòng hoặc người phụ trách cập nhật báo cáo KPI

Theo kỳ báo cáo đã định nghĩa ở B7, trưởng phòng hoặc người phụ trách cập nhật actual value của KPI.

Ví dụ:

```text
KPI:
Tăng tỷ lệ chốt đơn

Kỳ báo cáo:
Monthly

Tháng 01:
Actual = 28%
Ghi chú:
Tỷ lệ chốt đơn tăng nhẹ nhưng chưa đạt target do chất lượng lead chưa ổn định.
Minh chứng:
File báo cáo sales tháng 01
```

Báo cáo KPI có thể gồm:

- Actual value
- Kỳ báo cáo
- Ghi chú
- Minh chứng
- Người báo cáo
- Thời điểm báo cáo
- Trạng thái review nếu cần

---

## 6.5. CEO xem dashboard

Luồng:

```text
CEO đăng nhập
→ Xem dashboard tổng công ty
→ Thấy điểm tổng BSC
→ Thấy 4 góc độ BSC xanh/vàng/đỏ
→ Drill-down vào góc độ có vấn đề
→ Xem mục tiêu chiến lược bị ảnh hưởng
→ Xem KPI kéo điểm xuống
→ Xem action plan và task liên quan
→ Xem phòng ban/nhân viên nào đang bị trễ hoặc bị block
```

Ví dụ:

```text
Toàn công ty: 72% - Vàng
Tài chính: 65% - Đỏ
Mục tiêu: Tăng doanh thu SME - 58% - Đỏ
KPI Sales: Tăng tỷ lệ chốt đơn - 50% - Đỏ
Action Plan: Cải thiện quy trình tư vấn sales
Task bị trễ: Training đội sales về demo sản phẩm
```

---

## 7. Dữ liệu cần nhập cho Action Plan

Khi trưởng phòng tạo action plan, cần nhập:

| Trường dữ liệu | Bắt buộc | Ghi chú |
|---|---|---|
| KPI liên kết | Có | Lấy từ KPI của phòng ban |
| Tên action plan | Có | Không được rỗng |
| Mô tả action plan | Nên có | Giải thích kế hoạch hành động |
| Ngày bắt đầu | Có | Phục vụ Gantt |
| Ngày kết thúc dự kiến | Có | Phục vụ Gantt |
| Người phụ trách chính | Có | Thường là trưởng phòng hoặc nhân sự được giao |
| Độ ưu tiên | Không bắt buộc | Low, Medium, High, Critical |
| Trạng thái | Có | Draft, Active, Completed, Cancelled |
| Ghi chú | Không | Bổ sung nếu cần |

Ví dụ:

```text
KPI:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Action Plan:
Chuẩn hóa quy trình xử lý ticket

Start date:
2026-01-01

End date:
2026-03-31

Owner:
Trưởng phòng CSKH
```

---

## 8. Dữ liệu cần nhập cho Task

Khi tạo task, cần nhập:

| Trường dữ liệu | Bắt buộc | Ghi chú |
|---|---|---|
| Action Plan liên kết | Có | Task thuộc action plan nào |
| KPI liên kết | Tự suy ra | Lấy từ action plan |
| Tên task | Có | Không được rỗng |
| Mô tả task | Nên có | Mô tả việc cần làm |
| Người phụ trách | Có | Nhân viên hoặc trưởng phòng |
| Ngày bắt đầu | Có | Phục vụ Gantt |
| Deadline | Có | Phục vụ Gantt và cảnh báo quá hạn |
| Trạng thái Kanban | Có | Todo, In Progress, Review, Done... |
| Tiến độ % | Không bắt buộc | Có thể tự tính hoặc nhập |
| Độ ưu tiên | Không bắt buộc | Low, Medium, High, Critical |
| Task phụ thuộc | Không bắt buộc | Task này phụ thuộc task nào |
| Minh chứng | Không bắt buộc | File, link, hình ảnh, ghi chú |
| Lý do block | Bắt buộc nếu status = Blocked | Giải thích điểm nghẽn |
| Ghi chú | Không | Cập nhật tiến độ |

Ví dụ:

```text
Task:
Training nhân viên CSKH về quy trình phản hồi ticket mới

Action Plan:
Chuẩn hóa quy trình xử lý ticket

KPI:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Assignee:
Nguyễn Văn A

Start date:
2026-01-15

Deadline:
2026-01-30

Status:
Todo
```

---

## 9. Rule nghiệp vụ chính

## 9.1. Không tạo task tự do

Task bắt buộc phải thuộc một action plan.

Action plan bắt buộc phải thuộc một KPI.

```text
Không hợp lệ:
Task không có KPI

Hợp lệ:
KPI → Action Plan → Task
```

---

## 9.2. Trưởng phòng chỉ tạo action plan cho KPI của phòng ban mình

Trưởng phòng chỉ được tạo action plan cho KPI thuộc phòng ban của mình.

Ví dụ:

```text
Trưởng phòng HR
→ Chỉ tạo action plan cho KPI của HR
→ Không tạo action plan cho KPI của Sales
```

---

## 9.3. Trưởng phòng chỉ giao task trong phạm vi phòng ban mình

Trong MVP, trưởng phòng chỉ giao task cho nhân viên thuộc phòng ban mình.

Nếu sau này có task liên phòng ban, có thể mở rộng cơ chế phối hợp giữa các trưởng phòng.

---

## 9.4. Task phải có thời gian để hiển thị Gantt

Task muốn hiển thị trên Gantt cần có:

- Start date
- Deadline

Nếu thiếu một trong hai, task không đủ dữ liệu để hiển thị timeline đầy đủ.

---

## 9.5. Task quá hạn

Task được xem là quá hạn khi:

```text
Current date > Deadline
và
Status chưa phải Done hoặc Cancelled
```

Task quá hạn có thể được hiển thị ở cột Overdue hoặc gắn badge Overdue trên Kanban/Gantt.

---

## 9.6. Task bị block

Task bị block khi người phụ trách không thể tiếp tục vì một lý do nào đó.

Khi chuyển task sang Blocked, hệ thống bắt buộc nhập:

- Lý do bị block
- Người báo cáo block
- Thời điểm bị block

Ví dụ:

```text
Task:
Tích hợp tool đo thời gian phản hồi ticket

Block reason:
Chưa có quyền truy cập hệ thống ticket hiện tại.
```

---

## 9.7. Task Done nên qua Review

Để tránh nhân viên tự hoàn thành task không kiểm soát, MVP nên dùng flow:

```text
Todo
→ In Progress
→ Review
→ Done
```

Trong đó:

- Nhân viên chuyển task sang Review
- Trưởng phòng duyệt thành Done
- Nếu chưa đạt, trưởng phòng trả về In Progress

Nếu muốn đơn giản hơn, MVP có thể cho nhân viên chuyển thẳng Done, nhưng cách Review sẽ chặt chẽ hơn.

---

## 9.8. KPI báo cáo theo kỳ

Kỳ báo cáo của KPI lấy từ B7.

Ví dụ:

```text
KPI A reporting frequency = Monthly
→ Mỗi tháng cần có báo cáo actual value
```

Nếu đến hạn mà chưa có báo cáo, hệ thống cần cảnh báo:

```text
KPI chưa được cập nhật kỳ này
```

---

## 10. Kanban trong B8

## 10.1. Mục tiêu của Kanban

Kanban giúp quản lý trạng thái task trong quá trình thực thi.

Các view Kanban gợi ý:

- Kanban cá nhân của nhân viên
- Kanban phòng ban của trưởng phòng
- Kanban tổng công ty của CEO

---

## 10.2. Cột Kanban gợi ý

MVP nên dùng các trạng thái:

| Status | Ý nghĩa |
|---|---|
| TODO | Chưa bắt đầu |
| IN_PROGRESS | Đang thực hiện |
| REVIEW | Chờ trưởng phòng review |
| DONE | Hoàn thành |
| BLOCKED | Đang bị nghẽn |
| CANCELLED | Đã hủy |

Overdue có thể là badge hoặc filter, không nhất thiết là status riêng.

---

## 10.3. Rule chuyển trạng thái

Gợi ý flow:

```text
TODO → IN_PROGRESS
IN_PROGRESS → REVIEW
REVIEW → DONE
REVIEW → IN_PROGRESS
IN_PROGRESS → BLOCKED
BLOCKED → IN_PROGRESS
TODO/IN_PROGRESS/REVIEW → CANCELLED
```

Rule quyền:

- Nhân viên có thể chuyển task của mình từ TODO sang IN_PROGRESS
- Nhân viên có thể chuyển task của mình từ IN_PROGRESS sang REVIEW
- Nhân viên có thể chuyển task của mình sang BLOCKED kèm lý do
- Trưởng phòng có thể chuyển REVIEW sang DONE
- Trưởng phòng có thể trả REVIEW về IN_PROGRESS
- Trưởng phòng có thể CANCELLED task nếu cần

---

## 10.4. Filter Kanban

Kanban nên hỗ trợ filter theo:

- Phòng ban
- Nhân viên
- KPI
- Action plan
- Mục tiêu chiến lược
- Góc độ BSC
- Trạng thái
- Deadline
- Priority
- Overdue
- Blocked

---

## 11. Gantt trong B8

## 11.1. Mục tiêu của Gantt

Gantt giúp xem kế hoạch thực thi theo thời gian.

Gantt đặc biệt hữu ích cho:

- CEO xem timeline tổng của các action plan lớn
- Trưởng phòng xem timeline công việc của phòng ban
- Nhân viên xem deadline cá nhân
- Phát hiện task nào đang làm trễ cả action plan hoặc KPI

---

## 11.2. Dữ liệu cần có để hiển thị Gantt

Mỗi task cần có:

- Start date
- Deadline
- Status
- Assignee
- Action plan
- KPI
- Department
- Progress
- Dependencies nếu có

Mỗi action plan cũng nên có:

- Start date
- End date
- Progress tổng hợp
- Trạng thái

---

## 11.3. Task dependencies

Task dependencies là quan hệ phụ thuộc giữa các task.

Ví dụ:

```text
Task B chỉ bắt đầu sau khi Task A hoàn thành.
```

Dữ liệu cần lưu:

- Task nguồn
- Task phụ thuộc
- Loại phụ thuộc nếu cần

MVP có thể chỉ hỗ trợ loại đơn giản:

```text
FINISH_TO_START
```

Nghĩa là task sau chỉ nên bắt đầu khi task trước đã hoàn thành.

---

## 11.4. Rule dependencies

Hệ thống cần kiểm tra:

- Task không được phụ thuộc chính nó
- Không tạo dependency trùng lặp
- Không nên cho tạo vòng lặp dependency
- Task phụ thuộc nên thuộc cùng action plan trong MVP để giảm độ phức tạp

Ví dụ không hợp lệ:

```text
Task A phụ thuộc Task B
Task B phụ thuộc Task A
→ Tạo vòng lặp
```

---

## 12. Báo cáo KPI thực tế trong B8

## 12.1. Mục tiêu

Báo cáo KPI thực tế dùng để cập nhật actual value của KPI theo kỳ báo cáo đã định nghĩa ở B7.

Dashboard sẽ dùng actual value này để tính:

- KPI completion rate
- KPI status xanh/vàng/đỏ
- Objective score
- Perspective score
- Company score

---

## 12.2. Dữ liệu cần nhập khi báo cáo KPI

Mỗi báo cáo KPI nên có:

| Trường dữ liệu | Bắt buộc | Ghi chú |
|---|---|---|
| KPI liên kết | Có | KPI được báo cáo |
| Reporting period | Có | Kỳ báo cáo |
| Actual value | Có | Giá trị thực tế |
| Note | Không | Ghi chú tình hình |
| Evidence | Không nhưng nên có | File, link, ảnh, báo cáo |
| Reporter | Có | Người báo cáo |
| Submitted at | Có | Thời điểm nộp |
| Status | Có | Draft, Submitted, Approved, Rejected |

Ví dụ:

```text
KPI:
Tăng tỷ lệ chốt đơn

Period:
2026-01

Actual:
28%

Note:
Tỷ lệ chốt đơn tăng nhẹ so với baseline nhưng chưa đạt target.

Evidence:
sales_report_jan.xlsx
```

---

## 12.3. Review báo cáo KPI

MVP có thể có 2 hướng:

### Hướng đơn giản

Trưởng phòng là người cập nhật và chịu trách nhiệm báo cáo KPI.

Báo cáo sau khi submit được dùng trực tiếp cho dashboard.

### Hướng chặt chẽ hơn

Nhân viên cập nhật số liệu, trưởng phòng review và approve.

Dashboard chỉ dùng số liệu đã được approve.

Khuyến nghị MVP:

```text
Trưởng phòng là người submit báo cáo KPI chính thức.
Nhân viên cập nhật task và minh chứng.
```

Cách này giảm độ phức tạp quyền hạn và review.

---

## 13. Công thức tính tiến độ trong B8

## 13.1. Task progress

Task progress có thể tính theo trạng thái:

| Status | Progress gợi ý |
|---|---:|
| TODO | 0% |
| IN_PROGRESS | 50% |
| REVIEW | 80% |
| DONE | 100% |
| BLOCKED | Giữ progress hiện tại |
| CANCELLED | Không tính hoặc loại khỏi tiến độ |

Hoặc cho phép nhập progress thủ công.

Khuyến nghị MVP:

```text
Dùng status để tính progress cơ bản.
Nếu cần chi tiết, cho phép nhập progress %.
```

---

## 13.2. Action Plan progress

Action Plan progress có thể tính từ task bên trong.

Công thức đơn giản:

```text
Action Plan Progress = Số task Done / Tổng task hợp lệ * 100
```

Ví dụ:

```text
Action Plan có 10 task
7 task Done
→ Progress = 70%
```

Nếu có task bị Cancelled, có thể loại khỏi mẫu số.

---

## 13.3. KPI Work Progress

KPI Work Progress tính từ các action plan của KPI.

Công thức đơn giản:

```text
KPI Work Progress = Trung bình progress của các action plan thuộc KPI
```

Hoặc có thể tính theo số task:

```text
KPI Work Progress = Tổng task Done của KPI / Tổng task hợp lệ của KPI * 100
```

Khuyến nghị MVP:

```text
Tính theo tổng task Done / tổng task hợp lệ của KPI.
```

---

## 13.4. KPI Performance

KPI Performance không tính từ task, mà tính từ actual value báo cáo so với target ở B7.

Ví dụ HIGHER_IS_BETTER:

```text
Completion Rate = Actual / Target * 100
```

Ví dụ LOWER_IS_BETTER:

```text
Completion Rate = Target / Actual * 100
```

KPI Performance mới là dữ liệu chính để tính dashboard hiệu suất.

Task progress chỉ giúp hiểu quá trình thực thi.

---

## 14. Dashboard liên kết từ B8

Dashboard nên lấy dữ liệu từ 3 nguồn:

```text
B6: Tỉ trọng
B7: Target, direction, threshold
B8: Task progress, actual value, evidence
```

Dashboard có thể tính:

```text
KPI Performance
→ KPI Weighted Score
→ Objective Score
→ Perspective Score
→ Company Score
```

Ví dụ:

```text
KPI:
Tăng tỷ lệ chốt đơn

Weight:
10%

Target:
35%

Actual:
28%

Completion Rate:
28 / 35 * 100 = 80%

Weighted Score:
10% * 80% = 8%
```

CEO có thể drill-down:

```text
Company Score
→ Perspective Score
→ Strategic Objective
→ KPI
→ Action Plan
→ Task
→ Employee / Department
```

---

## 15. Dữ liệu cần lưu

## 15.1. Action Plan

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Department KPI ID
- Final Strategic Objective ID
- Department ID
- Name
- Description
- Start date
- End date
- Owner ID
- Priority
- Status
- Created by
- Updated by
- Created at
- Updated at

---

## 15.2. Task

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Action Plan ID
- Department KPI ID
- Final Strategic Objective ID
- Department ID
- Assignee ID
- Name
- Description
- Start date
- Due date
- Status
- Progress percent
- Priority
- Block reason
- Evidence URL / attachment reference
- Created by
- Updated by
- Created at
- Updated at

---

## 15.3. Task Dependency

Dữ liệu gợi ý:

- ID
- Source task ID
- Target task ID
- Dependency type
- Created at

Ví dụ:

```text
source_task_id:
TASK_A

target_task_id:
TASK_B

dependency_type:
FINISH_TO_START
```

---

## 15.4. Task Comment / Progress Log

Dữ liệu gợi ý:

- ID
- Task ID
- User ID
- Content
- Old status nếu có
- New status nếu có
- Progress percent nếu có
- Created at

Dùng để lưu lịch sử cập nhật task.

---

## 15.5. KPI Report

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Department KPI ID
- Final Strategic Objective ID
- Department ID
- Reporting period
- Actual value
- Completion rate
- Status color
- Note
- Evidence URL / attachment reference
- Reporter ID
- Review status
- Reviewed by
- Reviewed at
- Submitted at
- Created at
- Updated at

---

## 16. Validate của B8

## 16.1. Validate khi tạo Action Plan

Hệ thống cần kiểm tra:

- B7 đã hoàn thành
- KPI tồn tại và thuộc BSC Strategy hiện tại
- KPI thuộc phòng ban của trưởng phòng đang thao tác
- Tên action plan không được rỗng
- Start date không được sau end date
- Owner hợp lệ
- BSC Strategy đang ở trạng thái Draft hoặc Active/Operating tùy giai đoạn vận hành
- Người thao tác là trưởng phòng hoặc quyền tương đương

---

## 16.2. Validate khi tạo Task

Hệ thống cần kiểm tra:

- Action plan tồn tại
- Action plan thuộc KPI hợp lệ
- Task phải thuộc action plan
- Task suy ra được KPI từ action plan
- Tên task không được rỗng
- Assignee phải là nhân viên hợp lệ
- Assignee thuộc phòng ban của trưởng phòng trong MVP
- Start date không được sau due date
- Status thuộc enum hợp lệ
- Nếu status = BLOCKED thì block reason không được rỗng
- Task không được tạo tự do ngoài KPI/action plan

---

## 16.3. Validate khi cập nhật trạng thái task

Hệ thống cần kiểm tra:

- Người thao tác là assignee hoặc trưởng phòng có quyền
- Trạng thái mới thuộc enum hợp lệ
- Luồng chuyển trạng thái hợp lệ
- Nếu chuyển sang BLOCKED thì bắt buộc nhập lý do
- Nếu chuyển sang DONE từ REVIEW thì người thao tác nên là trưởng phòng
- Nếu task đã CANCELLED thì không nên cho cập nhật tiếp trừ khi khôi phục

---

## 16.4. Validate khi tạo báo cáo KPI

Hệ thống cần kiểm tra:

- KPI tồn tại
- KPI thuộc BSC Strategy hiện tại
- KPI đã có measurement ở B7
- Actual value không được rỗng
- Actual value phải là số
- Reporting period hợp lệ với reporting frequency
- Không tạo trùng báo cáo cho cùng KPI và cùng kỳ nếu chỉ cho một báo cáo mỗi kỳ
- Người báo cáo có quyền
- Nếu có evidence thì file/link hợp lệ

---

## 17. Validate để hoàn thành B8 setup

Để hoàn thành B8 setup, hệ thống nên kiểm tra:

- B1 đã hoàn thành
- B2 đã hoàn thành
- B3 đã hoàn thành
- B4 đã hoàn thành
- B5 đã hoàn thành
- B6 đã hoàn thành
- B7 đã hoàn thành
- Mỗi KPI nhỏ có ít nhất 1 action plan
- Mỗi action plan có ít nhất 1 task
- Mỗi task có assignee
- Mỗi task có start date và due date
- Không có task rỗng tên
- Không có action plan rỗng tên
- Không có task tự do ngoài KPI/action plan

Sau khi B8 setup hoàn thành, BSC Strategy có thể chuyển sang trạng thái Active/Operating.

---

## 18. Trạng thái trong B8

## 18.1. Action Plan status

Gợi ý trạng thái:

| Status | Ý nghĩa |
|---|---|
| DRAFT | Đang soạn |
| ACTIVE | Đang triển khai |
| COMPLETED | Hoàn thành |
| CANCELLED | Đã hủy |
| ON_HOLD | Tạm dừng |

---

## 18.2. Task status

Gợi ý trạng thái:

| Status | Ý nghĩa |
|---|---|
| TODO | Chưa bắt đầu |
| IN_PROGRESS | Đang thực hiện |
| REVIEW | Chờ review |
| DONE | Hoàn thành |
| BLOCKED | Bị nghẽn |
| CANCELLED | Đã hủy |

---

## 18.3. KPI Report status

Gợi ý trạng thái:

| Status | Ý nghĩa |
|---|---|
| DRAFT | Đang soạn |
| SUBMITTED | Đã nộp |
| APPROVED | Đã duyệt |
| REJECTED | Bị từ chối |

---

## 18.4. B8 step status

B8 có thể có các trạng thái:

- Not Started: chưa tạo action plan
- In Progress: đang tạo action plan và task
- Ready To Operate: action plan/task đã đủ để vận hành
- Operating: đang vận hành và báo cáo định kỳ
- Completed: hoàn thành setup B8
- Invalidated: dữ liệu B5/B6/B7 thay đổi làm B8 không còn hợp lệ
- Locked: khóa phần setup chiến lược, nhưng vẫn cho vận hành task/report theo quyền

---

## 19. Ảnh hưởng khi chỉnh sửa các bước trước sau khi đã có B8

## 19.1. KPI bị xóa từ B5

Nếu KPI bị xóa:

- Action plan liên quan bị ảnh hưởng
- Task liên quan bị ảnh hưởng
- KPI report liên quan bị ảnh hưởng
- Dashboard bị ảnh hưởng

Hệ thống nên:

- Không xóa cứng dữ liệu ngay
- Đánh dấu B8 Invalidated
- Cảnh báo người dùng kiểm tra lại
- Có thể archive action plan/task liên quan

---

## 19.2. Tỉ trọng KPI thay đổi từ B6

Nếu chỉ thay đổi tỉ trọng:

- Action plan/task không cần thay đổi
- KPI report không cần thay đổi
- Dashboard cần tính lại weighted score

---

## 19.3. Target KPI thay đổi từ B7

Nếu target thay đổi:

- Action plan/task có thể vẫn giữ
- KPI performance cần tính lại
- Dashboard cần cập nhật trạng thái
- Trưởng phòng cần được thông báo vì mục tiêu cần đạt đã thay đổi

---

## 19.4. Kỳ báo cáo thay đổi từ B7

Nếu reporting frequency thay đổi:

- Lịch nhắc báo cáo KPI thay đổi
- Các báo cáo cũ cần giữ lịch sử
- Báo cáo tương lai theo kỳ mới

---

## 20. Gợi ý UI/UX cho B8

## 20.1. View trưởng phòng

Gợi ý màn hình:

```text
Phòng ban của tôi
├── KPI 1
│   ├── Target
│   ├── Weight
│   ├── Action Plan
│   └── Task Kanban / Gantt
├── KPI 2
│   ├── Target
│   ├── Weight
│   ├── Action Plan
│   └── Task Kanban / Gantt
```

Trưởng phòng có thể chuyển giữa:

- KPI view
- Kanban view
- Gantt view
- Report KPI view

---

## 20.2. View nhân viên

Gợi ý màn hình:

```text
My Tasks
├── Todo
├── In Progress
├── Review
├── Done
```

Mỗi task hiển thị:

- Tên task
- KPI liên quan
- Action plan
- Deadline
- Priority
- Status
- Progress
- Nút cập nhật tiến độ
- Nút báo block

---

## 20.3. View CEO

Gợi ý màn hình:

```text
Dashboard tổng công ty
├── Company Score
├── 4 góc độ BSC
├── Mục tiêu chiến lược
├── KPI xanh/vàng/đỏ
├── Phòng ban có nhiều KPI đỏ
├── Task quá hạn
├── Action plan bị trễ
└── Drill-down chi tiết
```

CEO có thể click xuống:

```text
Company
→ Perspective
→ Objective
→ KPI
→ Action Plan
→ Task
→ Employee
```

---

## 20.4. Kanban và Gantt dùng chung dữ liệu

Cần thiết kế UX để user hiểu:

```text
Kanban = xem task theo trạng thái
Gantt = xem cùng task đó theo thời gian
```

Không tạo 2 loại task riêng.

---

## 21. Gợi ý dashboard sau B8

Dashboard nên có các chỉ số:

## 21.1. Cấp công ty

- Tổng điểm BSC
- Tổng số KPI
- Số KPI xanh/vàng/đỏ
- Số task quá hạn
- Số task bị block
- Phòng ban có nhiều KPI đỏ nhất
- Góc độ BSC yếu nhất

## 21.2. Cấp góc độ BSC

- Điểm từng góc độ
- KPI trong góc độ đó
- Mục tiêu chiến lược bị ảnh hưởng
- Tỉ trọng của góc độ
- Mức độ đóng góp vào điểm công ty

## 21.3. Cấp mục tiêu chiến lược

- Điểm mục tiêu
- KPI nhỏ bên trong
- Phòng ban tham gia
- Action plan liên quan
- Task đang trễ/block

## 21.4. Cấp KPI

- Target
- Actual
- Completion rate
- Weight
- Weighted score
- Work progress
- KPI performance
- Báo cáo gần nhất
- Action plan liên quan

## 21.5. Cấp phòng ban

- KPI của phòng ban
- KPI xanh/vàng/đỏ
- Task của phòng ban
- Task quá hạn
- Task bị block
- Tiến độ action plan của phòng ban

---

## 22. Ghi chú nghiệp vụ

B8 là bước cuối trong 8 bước triển khai BSC, nhưng đồng thời là bước mở đầu cho giai đoạn vận hành lâu dài của nền tảng.

Từ B8 trở đi, hệ thống không còn chỉ là nơi nhập liệu chiến lược nữa, mà trở thành nền tảng quản lý tiến độ, báo cáo KPI và hỗ trợ CEO theo dõi sức khỏe công ty.

Nguyên tắc quan trọng nhất:

```text
Mọi action plan và task phải bám vào KPI.
KPI phải bám vào mục tiêu chiến lược.
Mục tiêu chiến lược phải bám vào bản đồ chiến lược tổng.
```

Nhờ vậy hệ thống giữ được mạch liên kết:

```text
Chiến lược
→ KPI
→ Task
→ Báo cáo
→ Dashboard
```

Đây là phần tạo giá trị vận hành lớn nhất cho BSC SkillForge, vì CEO không chỉ xây chiến lược một lần rồi để đó, mà có thể theo dõi việc thực thi chiến lược hằng ngày, hằng tuần, hằng tháng thông qua Kanban, Gantt và dashboard.
