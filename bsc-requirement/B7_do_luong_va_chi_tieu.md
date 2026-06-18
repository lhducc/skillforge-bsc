# B7. Đo lường và Chỉ tiêu

## 1. Mục tiêu của bước

Bước **Đo lường và Chỉ tiêu** là bước CEO định nghĩa cách đo và mức cần đạt cho từng KPI nhỏ đã được tạo ở B5 và đã được phân bổ tỉ trọng ở B6.

Sau B6, hệ thống đã biết mỗi KPI nhỏ quan trọng bao nhiêu phần trăm trong toàn bộ chiến lược BSC. Tuy nhiên, hệ thống vẫn chưa biết KPI đó sẽ được đo bằng gì, cần đạt bao nhiêu, đo theo kỳ nào và khi nào được xem là đạt hoặc không đạt.

B7 dùng để trả lời các câu hỏi:

```text
KPI này đo bằng đơn vị gì?
KPI này cần đạt bao nhiêu?
KPI này hiện tại đang ở mức nào?
KPI này càng cao càng tốt hay càng thấp càng tốt?
KPI này báo cáo theo ngày, tuần, tháng, quý hay năm?
KPI này đạt bao nhiêu thì xanh, vàng hoặc đỏ?
KPI này do ai/phòng ban nào chịu trách nhiệm báo cáo?
```

B7 là bước biến KPI từ một mô tả chiến lược thành một chỉ tiêu có thể đo lường được.

---

## 2. Vai trò của B7 trong toàn workflow BSC

Luồng dữ liệu:

```text
B4. Bản đồ chiến lược tổng
→ B5. Mô hình xương cá
→ B6. Phân bổ tỉ trọng
→ B7. Đo lường & Chỉ tiêu
→ B8. Action Plan
```

Ở B7:

- KPI nhỏ đã có từ B5
- KPI nhỏ đã có tỉ trọng từ B6
- CEO nhập cách đo và chỉ tiêu cho từng KPI
- Dữ liệu B7 sẽ được dùng để đánh giá KPI trong quá trình vận hành
- Dữ liệu B7 là nền tảng để dashboard tính trạng thái xanh/vàng/đỏ và mức độ hoàn thành KPI

B7 là cầu nối giữa **KPI chiến lược** và **hệ thống báo cáo hiệu suất**.

---

## 3. Role được phép thao tác

## 3.1. CEO

CEO là người thao tác chính trong B7.

CEO được phép:

- Xem danh sách KPI nhỏ từ B5
- Xem tỉ trọng KPI từ B6
- Nhập đơn vị đo cho KPI
- Nhập chỉ tiêu mục tiêu cho KPI
- Nhập giá trị hiện tại hoặc baseline nếu có
- Nhập công thức đo nếu cần
- Chọn kỳ báo cáo
- Chọn chiều đánh giá KPI
- Thiết lập ngưỡng xanh/vàng/đỏ
- Chỉ định người hoặc phòng ban chịu trách nhiệm báo cáo nếu hệ thống cần
- Chỉnh sửa dữ liệu đo lường khi BSC Strategy còn Draft
- Hoàn thành B7 để chuyển sang B8

CEO là người nhập B7 vì chỉ tiêu và cách đo KPI là quyết định quản trị cấp chiến lược.

---

## 3.2. Trưởng phòng

Trưởng phòng được phép:

- Xem KPI của phòng ban mình
- Xem tỉ trọng KPI của phòng ban mình
- Xem chỉ tiêu đo lường do CEO đã thiết lập
- Xem kỳ báo cáo và cách đo KPI
- Chuẩn bị action plan ở B8 dựa trên chỉ tiêu đã được CEO nhập

Trưởng phòng không được phép:

- Thay đổi chỉ tiêu KPI ở B7
- Thay đổi đơn vị đo
- Thay đổi tỉ trọng
- Thay đổi ngưỡng đánh giá
- Thay đổi công thức đo nếu không được CEO cấp quyền

---

## 3.3. Nhân viên

Nhân viên được phép:

- Xem KPI liên quan đến phòng ban hoặc task của mình
- Xem mục tiêu cần đạt nếu KPI đó liên quan đến công việc được giao
- Xem kỳ báo cáo nếu nhân viên được yêu cầu cập nhật số liệu ở B8 hoặc giai đoạn vận hành

Nhân viên không được phép thao tác B7.

---

## 3.4. Company Admin

Company Admin không được thao tác B7.

Company Admin chủ yếu setup dữ liệu nền như công ty, phòng ban, chức vụ, nhân viên và phân quyền.

---

## 3.5. System Admin

System Admin thuộc bên cung cấp nền tảng.

System Admin chỉ monitoring hệ thống, không xem hoặc chỉnh sửa nội dung đo lường chiến lược của doanh nghiệp.

---

## 4. Điều kiện truy cập bước

B7 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B3 - Kết quả chiến lược đã hoàn thành
- B4 - Bản đồ chiến lược đã hoàn thành
- B5 - Mô hình xương cá đã hoàn thành
- B6 - Phân bổ tỉ trọng đã hoàn thành
- B5 đã có danh sách KPI nhỏ hợp lệ
- B6 đã có tỉ trọng hợp lệ cho từng KPI nhỏ
- Người thao tác có role CEO hoặc quyền tương đương

Nếu B6 chưa hoàn thành, hệ thống không cho CEO hoàn thành B7.

---

## 5. Khái niệm chính trong B7

## 5.1. KPI nhỏ đã có tỉ trọng

B7 không tạo KPI mới.

Tất cả KPI ở B7 phải lấy từ danh sách KPI nhỏ đã được tạo ở B5 và đã được phân bổ tỉ trọng ở B6.

```text
B5 tạo KPI nhỏ
→ B6 phân bổ tỉ trọng
→ B7 định nghĩa đo lường và chỉ tiêu
```

Nếu KPI không tồn tại ở B5 hoặc chưa có tỉ trọng ở B6, KPI đó không được xử lý ở B7.

---

## 5.2. Đơn vị đo

Đơn vị đo là cách biểu diễn giá trị KPI.

Ví dụ:

| Loại KPI | Đơn vị đo |
|---|---|
| Doanh thu | VNĐ |
| Tăng trưởng doanh thu | % |
| Số lượng khách hàng mới | khách hàng |
| Thời gian phản hồi ticket | giờ |
| Tỷ lệ hoàn thành task | % |
| Số lỗi nghiêm trọng | lỗi |
| Tỷ lệ nghỉ việc | % |
| Số buổi đào tạo | buổi |

Đơn vị đo có thể là:

- `%`
- `VNĐ`
- `USD`
- `người`
- `khách hàng`
- `đơn hàng`
- `giờ`
- `ngày`
- `ticket`
- `task`
- `lỗi`
- `điểm`
- hoặc đơn vị custom do CEO nhập

---

## 5.3. Giá trị hiện tại / Baseline

Giá trị hiện tại hoặc baseline là mức hiện tại của KPI trước khi triển khai action plan.

Ví dụ:

```text
KPI:
Tăng tỷ lệ khách hàng quay lại

Baseline:
25%

Target:
40%
```

Baseline giúp hệ thống biết công ty đang bắt đầu từ đâu.

Baseline có thể không bắt buộc trong MVP, nhưng rất nên có vì giúp dashboard thể hiện mức cải thiện theo thời gian.

---

## 5.4. Chỉ tiêu mục tiêu / Target

Target là mức cần đạt của KPI.

Ví dụ:

| KPI | Target |
|---|---:|
| Tăng tỷ lệ khách hàng quay lại | 40% |
| Giảm thời gian phản hồi ticket | 2 giờ |
| Tăng doanh thu từ khách hàng SME | 10 tỷ |
| Giảm số lỗi nghiêm trọng | 5 lỗi/tháng |
| Đào tạo kỹ năng giao tiếp khách hàng | 4 buổi/quý |

Target là dữ liệu bắt buộc để sau này hệ thống đánh giá KPI đạt hay chưa đạt.

---

## 5.5. Chiều đánh giá KPI

Không phải KPI nào cũng càng cao càng tốt.

Hệ thống cần có field xác định chiều đánh giá KPI.

Có 2 loại cơ bản:

| Mã | Ý nghĩa | Ví dụ |
|---|---|---|
| HIGHER_IS_BETTER | Càng cao càng tốt | Doanh thu, tỷ lệ hài lòng, số khách hàng mới |
| LOWER_IS_BETTER | Càng thấp càng tốt | Chi phí, số lỗi, thời gian xử lý, tỷ lệ nghỉ việc |

Ví dụ:

```text
Doanh thu:
HIGHER_IS_BETTER

Thời gian phản hồi ticket:
LOWER_IS_BETTER
```

Field này rất quan trọng để dashboard không tính sai hiệu suất KPI.

---

## 5.6. Kỳ báo cáo

Kỳ báo cáo xác định tần suất cập nhật số liệu KPI.

Ví dụ:

| Mã | Ý nghĩa |
|---|---|
| DAILY | Hằng ngày |
| WEEKLY | Hằng tuần |
| MONTHLY | Hằng tháng |
| QUARTERLY | Hằng quý |
| YEARLY | Hằng năm |
| CUSTOM | Tùy chỉnh |

Ví dụ:

```text
KPI:
Giảm thời gian phản hồi ticket

Kỳ báo cáo:
Weekly
```

Kỳ báo cáo sẽ ảnh hưởng đến cách hệ thống nhắc báo cáo, thu thập dữ liệu và hiển thị dashboard.

---

## 5.7. Công thức đo

Công thức đo là mô tả cách tính KPI.

Ví dụ:

```text
Tỷ lệ khách hàng quay lại = Số khách hàng quay lại / Tổng số khách hàng * 100
```

```text
Thời gian phản hồi trung bình = Tổng thời gian phản hồi ticket / Tổng số ticket
```

Trong MVP, công thức đo có thể là text mô tả, chưa cần hệ thống tự tính tự động.

Sau này nếu nâng cấp, công thức đo có thể được chuẩn hóa thành các trường dữ liệu để hệ thống tự tính.

---

## 5.8. Ngưỡng đánh giá

Ngưỡng đánh giá dùng để xác định trạng thái KPI.

Ví dụ trạng thái:

- Xanh: đạt tốt
- Vàng: cần chú ý
- Đỏ: nguy hiểm hoặc không đạt

Hệ thống có thể lưu các ngưỡng theo phần trăm hoàn thành.

Ví dụ:

```text
Xanh: >= 90%
Vàng: >= 70% và < 90%
Đỏ: < 70%
```

Ngưỡng có thể dùng mặc định toàn hệ thống hoặc cho phép CEO chỉnh theo từng KPI.

---

## 6. Dữ liệu cần nhập cho mỗi KPI ở B7

Với mỗi KPI nhỏ, CEO cần nhập các thông tin sau:

| Trường dữ liệu | Bắt buộc | Ghi chú |
|---|---|---|
| Đơn vị đo | Có | %, VNĐ, giờ, người, lỗi, ticket... |
| Giá trị hiện tại / Baseline | Không bắt buộc nhưng nên có | Giúp so sánh trước và sau |
| Target | Có | Mức cần đạt |
| Chiều đánh giá | Có | HIGHER_IS_BETTER hoặc LOWER_IS_BETTER |
| Kỳ báo cáo | Có | Daily, Weekly, Monthly, Quarterly, Yearly, Custom |
| Công thức đo | Nên có | Có thể là text mô tả |
| Ngưỡng xanh | Có | Ví dụ >= 90% |
| Ngưỡng vàng | Có | Ví dụ >= 70% |
| Ngưỡng đỏ | Có | Ví dụ < 70% |
| Người/phòng ban báo cáo | Có thể tùy chọn | Mặc định là phòng ban sở hữu KPI |
| Ghi chú | Không | Bổ sung nghiệp vụ nếu cần |

---

## 7. User flow của B7

## 7.1. CEO vào màn hình B7

Luồng:

```text
CEO hoàn thành B6
→ Hệ thống mở B7
→ CEO vào màn hình Đo lường & Chỉ tiêu
→ Hệ thống hiển thị danh sách KPI nhỏ đã có tỉ trọng
→ CEO nhập thông tin đo lường cho từng KPI
```

Hệ thống nên hiển thị KPI theo cấu trúc:

```text
Góc độ BSC
→ Mục tiêu chiến lược
→ KPI nhỏ
```

Ví dụ:

```text
Tài chính
└── Tăng doanh thu
    ├── KPI Sales: Tăng tỷ lệ chốt đơn
    ├── KPI Marketing: Tăng số lượng lead chất lượng
    └── KPI Product: Tối ưu gói sản phẩm bán chạy
```

---

## 7.2. CEO nhập đo lường cho KPI

Ví dụ KPI:

```text
KPI:
Tăng tỷ lệ chốt đơn

Phòng ban:
Sales

Tỉ trọng:
10%
```

CEO nhập:

```text
Đơn vị đo: %
Baseline: 20%
Target: 35%
Chiều đánh giá: HIGHER_IS_BETTER
Kỳ báo cáo: Monthly
Công thức đo: Số deal thành công / Tổng số lead đủ điều kiện * 100
Ngưỡng xanh: >= 90% target
Ngưỡng vàng: >= 70% target
Ngưỡng đỏ: < 70% target
```

---

## 7.3. CEO kiểm tra danh sách KPI còn thiếu thông tin

Hệ thống nên hiển thị trạng thái từng KPI:

- Chưa cấu hình đo lường
- Đang thiếu target
- Đang thiếu đơn vị đo
- Đang thiếu chiều đánh giá
- Đã hoàn tất

Ví dụ:

| KPI | Trạng thái |
|---|---|
| Tăng tỷ lệ chốt đơn | Đã hoàn tất |
| Tăng số lượng lead chất lượng | Thiếu target |
| Tối ưu gói sản phẩm bán chạy | Thiếu kỳ báo cáo |

---

## 7.4. CEO xác nhận hoàn thành B7

Khi tất cả KPI đã có thông tin đo lường hợp lệ, CEO bấm hoàn thành B7.

Hệ thống validate toàn bộ. Nếu hợp lệ, B7 chuyển sang trạng thái Completed và mở khóa B8.

---

## 8. Rule nghiệp vụ chính

## 8.1. B7 không tạo KPI mới

B7 chỉ cấu hình đo lường cho KPI đã có từ B5 và B6.

Không cho tạo KPI tự do ở B7.

Nếu cần thêm KPI, trưởng phòng phải quay lại B5 để tạo KPI, sau đó CEO phải quay lại B6 để phân bổ tỉ trọng cho KPI mới, rồi mới tiếp tục B7.

---

## 8.2. Mỗi KPI phải có đúng một cấu hình đo lường

Mỗi KPI nhỏ nên có một bản cấu hình đo lường chính thức trong cùng một BSC Strategy.

Quan hệ:

```text
Department KPI
→ KPI Measurement
```

Một KPI không nên có nhiều cấu hình đo lường active cùng lúc trong MVP.

---

## 8.3. Target là bắt buộc

Nếu không có target, hệ thống không thể đánh giá KPI đạt hay chưa đạt.

Do đó target là field bắt buộc.

---

## 8.4. Chiều đánh giá là bắt buộc

Chiều đánh giá là field bắt buộc vì ảnh hưởng trực tiếp đến cách tính mức độ hoàn thành.

Ví dụ:

```text
KPI doanh thu:
Actual càng cao càng tốt

KPI chi phí:
Actual càng thấp càng tốt
```

Nếu không có chiều đánh giá, dashboard có thể tính sai.

---

## 8.5. Kỳ báo cáo là bắt buộc

Kỳ báo cáo là field bắt buộc vì sau này hệ thống cần biết KPI được cập nhật theo chu kỳ nào.

Ví dụ:

- KPI doanh thu: monthly
- KPI đào tạo: quarterly
- KPI thời gian phản hồi ticket: weekly

---

## 8.6. Công thức đo có thể là text trong MVP

Trong MVP, hệ thống không cần tự động tính toàn bộ KPI theo công thức.

Công thức đo có thể lưu dạng text để người dùng hiểu cách báo cáo.

Ví dụ:

```text
Số khách hàng quay lại / Tổng số khách hàng * 100
```

Sau này có thể nâng cấp thành công thức có cấu trúc để hệ thống tự động tính từ dữ liệu vận hành.

---

## 9. Công thức tính mức độ hoàn thành KPI

B7 nên định nghĩa logic cơ bản để dashboard tính mức độ hoàn thành KPI.

## 9.1. KPI càng cao càng tốt

Áp dụng cho KPI có `direction = HIGHER_IS_BETTER`.

Công thức gợi ý:

```text
Completion Rate = Actual Value / Target Value * 100
```

Ví dụ:

```text
Target doanh thu = 10 tỷ
Actual doanh thu = 8 tỷ

Completion Rate = 8 / 10 * 100 = 80%
```

---

## 9.2. KPI càng thấp càng tốt

Áp dụng cho KPI có `direction = LOWER_IS_BETTER`.

Công thức gợi ý:

```text
Completion Rate = Target Value / Actual Value * 100
```

Ví dụ:

```text
Target thời gian phản hồi = 2 giờ
Actual thời gian phản hồi = 4 giờ

Completion Rate = 2 / 4 * 100 = 50%
```

Nếu actual thấp hơn hoặc bằng target, KPI được xem là đạt tốt.

Ví dụ:

```text
Target thời gian phản hồi = 2 giờ
Actual thời gian phản hồi = 1.5 giờ

Completion Rate = 2 / 1.5 * 100 = 133.33%
```

Hệ thống có thể cho phép completion rate vượt 100%, hoặc cap ở 100% khi tính dashboard tổng. Cần chốt ở giai đoạn dashboard.

Khuyến nghị MVP:

```text
Lưu completion rate thực tế
Khi tính điểm tổng hợp dashboard, có thể cap tối đa 100% để tránh một KPI vượt mức kéo lệch toàn bộ điểm công ty
```

---

## 9.3. Trạng thái xanh/vàng/đỏ

Dựa trên completion rate, hệ thống xác định trạng thái KPI.

Ví dụ ngưỡng mặc định:

| Completion Rate | Trạng thái |
|---:|---|
| >= 90% | Xanh |
| >= 70% và < 90% | Vàng |
| < 70% | Đỏ |

Ví dụ:

```text
Completion Rate = 80%
→ Trạng thái: Vàng
```

Ngưỡng này có thể cho phép CEO chỉnh theo từng KPI nếu cần.

---

## 10. Dữ liệu cần lưu

## 10.1. KPI Measurement

Dùng để lưu cấu hình đo lường của KPI.

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Department KPI ID
- Final Strategic Objective ID
- Department ID
- Perspective code
- Unit
- Baseline value
- Target value
- Direction
- Reporting frequency
- Formula description
- Green threshold
- Yellow threshold
- Red threshold
- Report owner ID nếu có
- Status
- Created by
- Updated by
- Created at
- Updated at

Ví dụ:

```text
Department KPI:
Tăng tỷ lệ chốt đơn

Unit:
%

Baseline:
20

Target:
35

Direction:
HIGHER_IS_BETTER

Reporting Frequency:
MONTHLY

Formula:
Số deal thành công / Tổng số lead đủ điều kiện * 100
```

---

## 10.2. KPI Report Owner

Nếu hệ thống muốn chỉ định người báo cáo KPI ngay từ B7, có thể lưu thêm người chịu trách nhiệm báo cáo.

Có 2 hướng:

### Hướng MVP đơn giản

Mặc định trưởng phòng là người chịu trách nhiệm báo cáo KPI.

```text
KPI report owner = Department Head
```

### Hướng mở rộng

Cho CEO chọn cụ thể người phụ trách báo cáo.

```text
KPI report owner = Employee ID
```

Khuyến nghị MVP:

```text
Mặc định trưởng phòng là người chịu trách nhiệm chính.
Sang B8 hoặc giai đoạn vận hành có thể giao task/báo cáo cho nhân viên cụ thể.
```

---

## 11. Validate của B7

## 11.1. Validate khi lưu cấu hình đo lường cho KPI

Hệ thống cần kiểm tra:

- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương
- KPI nhỏ tồn tại trong B5
- KPI nhỏ đã có tỉ trọng ở B6
- KPI thuộc BSC Strategy hiện tại
- Đơn vị đo không được rỗng
- Target không được rỗng
- Target phải là số
- Target không được âm
- Direction phải thuộc enum hợp lệ: HIGHER_IS_BETTER hoặc LOWER_IS_BETTER
- Reporting frequency phải thuộc enum hợp lệ
- Threshold phải hợp lệ
- Không tạo nhiều measurement active cho cùng một KPI trong cùng BSC Strategy

---

## 11.2. Validate threshold

Nếu hệ thống dùng threshold theo completion rate, rule gợi ý:

```text
Green threshold > Yellow threshold
Yellow threshold > Red threshold hoặc Red là phần còn lại dưới Yellow
```

Ví dụ hợp lệ:

```text
Green: >= 90%
Yellow: >= 70%
Red: < 70%
```

Ví dụ không hợp lệ:

```text
Green: >= 60%
Yellow: >= 80%
```

Vì ngưỡng vàng không thể cao hơn ngưỡng xanh.

---

## 11.3. Validate để hoàn thành B7

Để hoàn thành B7 và chuyển sang B8, hệ thống cần kiểm tra:

- B1 đã hoàn thành
- B2 đã hoàn thành
- B3 đã hoàn thành
- B4 đã hoàn thành
- B5 đã hoàn thành
- B6 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương
- Mỗi KPI nhỏ từ B5/B6 đều có cấu hình đo lường
- Mỗi KPI có đơn vị đo
- Mỗi KPI có target
- Mỗi KPI có chiều đánh giá
- Mỗi KPI có kỳ báo cáo
- Threshold hợp lệ
- Không có KPI bị thiếu measurement
- Không có KPI thuộc BSC Strategy hiện tại bị bỏ sót

---

## 12. Output của B7

Sau khi hoàn thành B7, hệ thống lưu lại:

- Cấu hình đo lường của từng KPI
- Đơn vị đo
- Baseline nếu có
- Target
- Chiều đánh giá
- Kỳ báo cáo
- Công thức đo
- Ngưỡng xanh/vàng/đỏ
- Người/phòng ban chịu trách nhiệm báo cáo nếu có
- Người hoàn thành B7
- Thời điểm hoàn thành B7
- Trạng thái hoàn thành B7

Dữ liệu quan trọng nhất được chuyển sang B8 là:

```text
Danh sách KPI nhỏ đã có:
- Tỉ trọng
- Đơn vị đo
- Target
- Kỳ báo cáo
- Cách đánh giá
```

---

## 13. Liên kết với B8 - Action Plan

Sau khi B7 hoàn thành, B8 sẽ dùng KPI đã có chỉ tiêu để tạo action plan và task.

Luồng:

```text
KPI nhỏ đã có tỉ trọng và target
→ Action Plan
→ Task
→ Nhân viên phụ trách
→ Báo cáo tiến độ
→ Dashboard
```

Rule quan trọng:

```text
Task ở B8 phải liên kết với KPI.
Không tạo task tự do.
```

Ví dụ:

```text
KPI:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Action Plan:
Chuẩn hóa quy trình xử lý ticket

Task:
- Rà soát quy trình xử lý ticket hiện tại
- Tạo template phản hồi khách hàng
- Training nhân viên CSKH
- Thiết lập SLA theo loại ticket
```

---

## 14. Liên kết với dashboard

B7 cung cấp dữ liệu để dashboard tính hiệu suất KPI.

Dashboard cần dữ liệu từ:

```text
B6:
Tỉ trọng KPI

B7:
Target, direction, threshold

B8 / vận hành:
Actual value, báo cáo tiến độ, minh chứng
```

Cách tính cơ bản:

```text
KPI Completion Rate
→ KPI Weighted Score
→ Objective Score
→ Perspective Score
→ Company Score
```

Ví dụ:

```text
KPI Sales:
Tỉ trọng = 10%
Target = 35%
Actual = 28%
Direction = HIGHER_IS_BETTER

Completion Rate = 28 / 35 * 100 = 80%

Weighted Score = 10% * 80% = 8%
```

Dashboard có thể dùng B7 để hiển thị:

- KPI nào đang xanh
- KPI nào đang vàng
- KPI nào đang đỏ
- KPI nào chưa được báo cáo đúng kỳ
- KPI nào có actual thấp hơn target
- KPI nào kéo điểm mục tiêu chiến lược xuống

---

## 15. Ảnh hưởng khi chỉnh sửa B6 sau khi đã có B7

Nếu CEO quay lại B6 và thay đổi tỉ trọng, B7 thường không cần nhập lại toàn bộ dữ liệu đo lường nếu KPI không thay đổi.

Các trường hợp:

## 15.1. Chỉ thay đổi tỉ trọng KPI

Nếu KPI vẫn giữ nguyên ID, chỉ thay đổi weight:

- B7 vẫn giữ nguyên measurement
- Dashboard cập nhật lại điểm theo tỉ trọng mới
- Không cần invalid B7

## 15.2. KPI bị mất tỉ trọng do thay đổi B6

Nếu một KPI không còn có tỉ trọng hợp lệ:

- B7 bị ảnh hưởng
- Hệ thống cần cảnh báo
- B7 có thể bị đánh dấu Invalidated cho KPI đó

## 15.3. KPI mới xuất hiện do B5/B6 thay đổi

Nếu có KPI mới từ B5 và B6:

- KPI mới chưa có measurement
- B7 cần được cập nhật
- Hệ thống đánh dấu B7 là In Progress hoặc Invalidated cho đến khi KPI mới được cấu hình

---

## 16. Ảnh hưởng khi chỉnh sửa B5 sau khi đã có B7

Nếu KPI ở B5 thay đổi sau khi B7 đã hoàn thành:

## 16.1. KPI bị xóa

- Measurement của KPI đó không còn hợp lệ
- B6 và B7 đều bị ảnh hưởng
- Hệ thống nên đánh dấu B6/B7 là Invalidated

## 16.2. KPI đổi tên/mô tả

- Nếu ID KPI không đổi, B7 có thể giữ nguyên measurement
- Hệ thống chỉ cập nhật tên/mô tả mới khi render

## 16.3. KPI mới được thêm

- KPI mới cần được phân bổ tỉ trọng ở B6
- Sau đó cần được cấu hình đo lường ở B7

---

## 17. Trạng thái của bước

B7 có thể có các trạng thái:

- Not Started: chưa nhập đo lường KPI
- In Progress: đang nhập đo lường
- Missing Information: còn KPI thiếu thông tin đo lường
- Completed: tất cả KPI đã có đo lường hợp lệ
- Invalidated: dữ liệu B5 hoặc B6 thay đổi làm B7 không còn hợp lệ
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B7.

Khi BSC Strategy đã Active, B7 bị hạn chế chỉnh sửa vì thay đổi target, công thức đo hoặc ngưỡng đánh giá sẽ ảnh hưởng trực tiếp đến dashboard và báo cáo hiệu suất.

---

## 18. Gợi ý UI/UX cho B7

## 18.1. View theo cây KPI

Gợi ý hiển thị:

```text
Góc độ BSC
├── Mục tiêu chiến lược
│   ├── KPI nhỏ
│   │   ├── Tỉ trọng
│   │   ├── Đơn vị đo
│   │   ├── Baseline
│   │   ├── Target
│   │   ├── Kỳ báo cáo
│   │   └── Trạng thái cấu hình
```

View này giúp CEO biết KPI nào còn thiếu thông tin.

---

## 18.2. Form cấu hình KPI

Gợi ý form:

```text
KPI name: Tăng tỷ lệ chốt đơn
Department: Sales
Weight: 10%
Perspective: Tài chính
Objective: Tăng doanh thu

Unit: %
Baseline: 20
Target: 35
Direction: Higher is better
Reporting frequency: Monthly
Formula: Số deal thành công / Tổng số lead đủ điều kiện * 100

Green threshold: 90
Yellow threshold: 70
Red threshold: below 70
```

---

## 18.3. Cảnh báo lỗi nhập liệu

Các lỗi thường gặp:

```text
KPI này chưa có target
KPI này chưa có đơn vị đo
KPI này chưa chọn chiều đánh giá
KPI này chưa chọn kỳ báo cáo
Target phải là số
Target không được âm
Ngưỡng xanh phải lớn hơn ngưỡng vàng
KPI này chưa có tỉ trọng ở B6
```

---

## 18.4. Default threshold

Để giảm thao tác nhập liệu, hệ thống có thể dùng threshold mặc định:

```text
Xanh: >= 90%
Vàng: >= 70%
Đỏ: < 70%
```

CEO có thể chỉnh nếu cần.

---

## 19. Ghi chú nghiệp vụ

B7 là bước biến KPI nhỏ thành KPI có thể đo lường và đánh giá được.

Kết quả quan trọng nhất của B7 là mỗi KPI có:

- Tỉ trọng từ B6
- Đơn vị đo
- Target
- Chiều đánh giá
- Kỳ báo cáo
- Ngưỡng đánh giá

Hệ thống không tự quyết định KPI nào tốt hay xấu nếu chưa có dữ liệu báo cáo thực tế. B7 chỉ thiết lập cách đo và chỉ tiêu cần đạt.

Dữ liệu thực tế để so sánh với target sẽ đến từ B8 và giai đoạn vận hành sau này, khi trưởng phòng hoặc nhân viên cập nhật báo cáo.

Từ B7 trở đi, hệ thống đã có đủ nền tảng để biến BSC thành dashboard quản trị:

```text
Tỉ trọng từ B6
+ Chỉ tiêu từ B7
+ Báo cáo thực tế từ B8
= Dashboard theo dõi sức khỏe công ty
```
