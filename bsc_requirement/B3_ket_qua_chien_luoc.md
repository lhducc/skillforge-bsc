# B3. Kết quả chiến lược

## 1. Mục tiêu của bước

Bước **Kết quả chiến lược** là bước CEO xem lại toàn bộ các chiến lược ứng viên đã được tạo ở B2 và chọn ra chiến lược chính thức để tiếp tục triển khai trong B4 - Bản đồ chiến lược.

Ở B2, CEO có thể tạo tối đa 12 chiến lược thuộc các nhóm:

- SO
- ST
- WO
- WT

Tuy nhiên, doanh nghiệp không triển khai tất cả chiến lược cùng lúc. CEO cần cân nhắc và chọn ra những chiến lược quan trọng nhất, phù hợp nhất với định hướng phát triển của công ty.

Trong B3, CEO sẽ chọn:

- Tối thiểu 1 chiến lược
- Tối đa 2 chiến lược

Các chiến lược được chọn ở B3 sẽ trở thành đầu vào cho B4 để xây dựng bản đồ chiến lược.

---

## 2. Role được phép thao tác

### 2.1. CEO

CEO là người có quyền chính trong B3.

CEO được phép:

- Xem danh sách chiến lược ứng viên từ B2
- Xem chi tiết từng chiến lược
- Chọn chiến lược để triển khai
- Bỏ chọn chiến lược khi BSC Strategy còn ở trạng thái Draft
- Ghi chú lý do lựa chọn chiến lược nếu cần
- Hoàn thành B3 để chuyển sang B4

### 2.2. BSC Consultant

Nếu doanh nghiệp có BSC Consultant hỗ trợ, Consultant có thể ngồi cùng CEO để phân tích và lựa chọn chiến lược.

Về mặt hệ thống, Consultant có thể sử dụng tài khoản CEO hoặc một tài khoản được CEO cấp quyền đặc biệt nếu sau này hệ thống hỗ trợ role này.

### 2.3. Các role khác

Các role sau không được xem hoặc thao tác B3:

- Trưởng phòng
- Nhân viên
- Company Admin
- System Admin

Lý do: B3 là bước chốt định hướng chiến lược chính thức của công ty, thuộc nhóm dữ liệu bí mật kinh doanh.

System Admin chỉ có quyền monitoring hệ thống, không xem nội dung chiến lược chi tiết của doanh nghiệp.

---

## 3. Điều kiện truy cập bước

B3 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B2 có ít nhất 1 chiến lược ứng viên
- Người thao tác có role CEO hoặc quyền tương đương

Nếu B2 chưa hoàn thành hoặc chưa có chiến lược ứng viên, hệ thống không cho hoàn thành B3.

---

## 4. Nguồn dữ liệu của B3

B3 lấy dữ liệu trực tiếp từ B2.5 - Xây dựng chiến lược SO/ST/WO/WT.

Mỗi chiến lược ứng viên gồm:

- ID chiến lược
- Nhóm chiến lược: SO, ST, WO hoặc WT
- Tên chiến lược
- Mô tả chiến lược
- Danh sách SWOT item đã dùng
- Nguồn gốc SWOT item
- Thứ tự hiển thị
- Trạng thái

Ví dụ:

```text
Chiến lược SO-01:
Tên: Tăng trưởng thị phần SME bằng năng lực triển khai nhanh
Nhóm: SO

S đã dùng:
- Đội ngũ kỹ thuật triển khai nhanh

O đã dùng:
- Doanh nghiệp SME bắt đầu quan tâm đến chuyển đổi số
```

---

## 5. User flow của B3

### Bước 1: CEO vào màn hình Kết quả chiến lược

Hệ thống hiển thị toàn bộ chiến lược ứng viên đã được tạo ở B2.

Ví dụ:

| Mã | Nhóm | Tên chiến lược |
|---|---|---|
| SO-01 | SO | Tăng trưởng thị phần SME bằng năng lực triển khai nhanh |
| SO-02 | SO | Mở rộng kênh bán hàng dựa trên lợi thế quan hệ khách hàng |
| ST-01 | ST | Dùng chất lượng dịch vụ để đối phó cạnh tranh giá |
| WO-01 | WO | Cải thiện năng lực sales để tận dụng nhu cầu chuyển đổi số |
| WT-01 | WT | Giảm rủi ro vận hành khi thị trường cạnh tranh mạnh |

---

### Bước 2: CEO xem chi tiết chiến lược

CEO có thể mở chi tiết từng chiến lược để xem:

- Tên chiến lược
- Mô tả chiến lược
- Nhóm chiến lược
- Các yếu tố SWOT được dùng để tạo chiến lược
- Nguồn gốc của từng SWOT item

Mục tiêu là giúp CEO hiểu chiến lược này được hình thành từ dữ liệu nào ở các bước trước.

---

### Bước 3: CEO chọn chiến lược chính thức

CEO chọn ít nhất 1 và tối đa 2 chiến lược để triển khai.

Quy tắc:

- Phải chọn ít nhất 1 chiến lược
- Không được chọn quá 2 chiến lược
- Chỉ được chọn chiến lược thuộc BSC Strategy hiện tại
- Chỉ được chọn chiến lược đang hợp lệ từ B2
- CEO có thể bỏ chọn khi BSC Strategy còn Draft

Ví dụ hợp lệ:

```text
CEO chọn:
- SO-01
- WO-01
```

Ví dụ không hợp lệ:

```text
CEO chọn 0 chiến lược
→ Không được hoàn thành B3

CEO chọn 3 chiến lược
→ Hệ thống báo lỗi vì vượt quá giới hạn tối đa 2 chiến lược
```

---

### Bước 4: CEO có thể nhập ghi chú lựa chọn

Hệ thống có thể cho phép CEO nhập ghi chú hoặc lý do lựa chọn cho từng chiến lược.

Ví dụ:

```text
Lý do chọn SO-01:
Chiến lược này phù hợp với năng lực triển khai nhanh hiện tại của công ty và tận dụng được nhu cầu chuyển đổi số đang tăng ở nhóm khách hàng SME.
```

Ghi chú này không bắt buộc, nhưng hữu ích để sau này khi xem lại chiến lược, CEO và BSC Consultant hiểu vì sao chiến lược đó được chọn.

---

### Bước 5: CEO xác nhận hoàn thành B3

Sau khi chọn đủ chiến lược, CEO bấm hoàn thành B3.

Hệ thống kiểm tra rule validate. Nếu hợp lệ, B3 chuyển sang trạng thái Completed và mở khóa B4 - Bản đồ chiến lược.

---

## 6. Rule nghiệp vụ chính

## 6.1. Số lượng chiến lược được chọn

CEO được chọn:

| Điều kiện | Giá trị |
|---|---:|
| Số chiến lược tối thiểu | 1 |
| Số chiến lược tối đa | 2 |

Nếu CEO chọn 1 chiến lược, B4 sẽ xây bản đồ chiến lược cho 1 chiến lược đó.

Nếu CEO chọn 2 chiến lược, B4 sẽ xây bản đồ riêng cho từng chiến lược, sau đó thực hiện bước gộp bản đồ chiến lược.

---

## 6.2. B3 không tạo chiến lược mới

B3 chỉ là bước lựa chọn chiến lược.

CEO không tạo chiến lược mới ở B3.

Nếu CEO muốn bổ sung, chỉnh sửa hoặc xóa chiến lược ứng viên, CEO cần quay lại B2.5 để thao tác.

Sau khi B2.5 thay đổi, hệ thống cần kiểm tra lại các chiến lược đã chọn ở B3.

---

## 6.3. Chiến lược đã chọn phải còn tồn tại và hợp lệ

Một chiến lược chỉ có thể được chọn ở B3 nếu:

- Chiến lược vẫn tồn tại trong B2
- Chiến lược thuộc BSC Strategy hiện tại
- Chiến lược chưa bị xóa
- Chiến lược vẫn đáp ứng rule tạo chiến lược ở B2.5

Nếu một chiến lược đã được chọn ở B3 nhưng sau đó bị xóa hoặc chỉnh sửa không hợp lệ ở B2.5, hệ thống cần cảnh báo và yêu cầu CEO chọn lại.

---

## 6.4. Thứ tự chiến lược được chọn

Nếu CEO chọn 2 chiến lược, hệ thống nên lưu thứ tự ưu tiên của chiến lược.

Ví dụ:

| Thứ tự | Chiến lược |
|---:|---|
| 1 | SO-01 |
| 2 | WO-01 |

Thứ tự này có thể dùng để hiển thị ở B4, nhưng không làm thay đổi rule nghiệp vụ chính.

---

## 7. Validate của B3

Để hoàn thành B3 và chuyển sang B4, hệ thống cần kiểm tra:

- B1 đã hoàn thành
- B2 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- Người thao tác có role CEO hoặc quyền tương đương
- Có ít nhất 1 chiến lược được chọn
- Không có quá 2 chiến lược được chọn
- Các chiến lược được chọn đều thuộc BSC Strategy hiện tại
- Các chiến lược được chọn đều còn tồn tại
- Các chiến lược được chọn đều hợp lệ từ B2.5
- Không có chiến lược bị chọn trùng lặp

---

## 8. Output của B3

Sau khi hoàn thành B3, hệ thống lưu lại:

- Danh sách chiến lược được chọn
- Thứ tự ưu tiên của chiến lược được chọn nếu có
- Ghi chú/lý do lựa chọn nếu có
- Người thực hiện lựa chọn
- Thời điểm lựa chọn
- Trạng thái hoàn thành của B3

Dữ liệu B3 sẽ được dùng làm đầu vào cho B4.

---

## 9. Liên kết với B4 - Bản đồ chiến lược

B3 quyết định số lượng chiến lược được đưa sang B4.

### Trường hợp CEO chọn 1 chiến lược

B4 sẽ cho CEO xây bản đồ chiến lược cho chiến lược đó.

Rule ở B4:

- Chiến lược phải có đủ 4 góc độ BSC
- Tổng mục tiêu chiến lược của chiến lược đó không vượt quá 12
- CEO vẽ đường liên kết nhân quả
- Bản đồ này trở thành bản đồ chiến lược tổng của công ty

### Trường hợp CEO chọn 2 chiến lược

B4 sẽ cho CEO xây bản đồ riêng cho từng chiến lược.

Rule ở B4:

- Mỗi chiến lược phải có đủ 4 góc độ BSC
- Mỗi chiến lược có tối đa 12 mục tiêu chiến lược
- CEO vẽ đường nhân quả riêng cho từng chiến lược
- Hệ thống render toàn bộ mục tiêu chiến lược từ 2 chiến lược, tối đa 24 mục tiêu
- CEO có thể Keep / Remove / Merge / Edit các mục tiêu để tạo bản đồ chiến lược tổng
- CEO vẽ lại đường nhân quả tổng cho bản đồ cuối cùng

---

## 10. Ảnh hưởng khi chỉnh sửa B2 sau khi đã hoàn thành B3

Khi BSC Strategy còn Draft, CEO có thể quay lại B2 để chỉnh sửa dữ liệu.

Tuy nhiên, nếu CEO chỉnh sửa B2 sau khi B3 đã hoàn thành, hệ thống cần xử lý cẩn thận vì B3 phụ thuộc trực tiếp vào chiến lược ứng viên từ B2.

Các trường hợp cần xử lý:

### 10.1. Chiến lược được chọn ở B3 bị xóa ở B2

Hệ thống cần:

- Đánh dấu B3 không còn hợp lệ
- Yêu cầu CEO chọn lại chiến lược
- Nếu B4 đã có dữ liệu dựa trên chiến lược bị xóa, hệ thống cần cảnh báo rằng dữ liệu B4 có thể bị ảnh hưởng

### 10.2. Chiến lược được chọn ở B3 bị chỉnh sửa ở B2

Hệ thống cần:

- Cập nhật nội dung chiến lược ở B3
- Cảnh báo CEO rằng chiến lược đã chọn đã thay đổi
- Nếu thay đổi lớn, có thể yêu cầu CEO xác nhận lại lựa chọn

### 10.3. B2 tạo thêm chiến lược mới

Hệ thống không tự thay đổi B3.

CEO có thể vào B3 để chọn lại nếu muốn.

---

## 11. Trạng thái của bước

B3 có thể có các trạng thái:

- Not Started: chưa chọn chiến lược
- In Progress: đang xem và chọn chiến lược
- Completed: đã chọn hợp lệ và xác nhận hoàn thành
- Invalidated: lựa chọn không còn hợp lệ do dữ liệu B2 thay đổi
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B3.

Khi BSC Strategy đã Active, B3 bị hạn chế chỉnh sửa vì thay đổi chiến lược được chọn sẽ ảnh hưởng đến toàn bộ workflow phía sau.

---

## 12. Ghi chú nghiệp vụ

B3 là bước CEO chốt chiến lược triển khai chính thức từ danh sách chiến lược ứng viên đã tạo ở B2.

B3 không phải nơi phân tích lại chiến lược và cũng không phải nơi tạo chiến lược mới.

Vai trò của hệ thống trong B3 là:

- Hiển thị đầy đủ các chiến lược ứng viên
- Cho CEO xem nguồn gốc SWOT của từng chiến lược
- Kiểm soát số lượng chiến lược được chọn từ 1 đến 2
- Lưu lại chiến lược được chọn để chuyển sang B4
- Đảm bảo các lựa chọn ở B3 vẫn hợp lệ nếu dữ liệu B2 thay đổi

B3 là cầu nối giữa bước xây dựng chiến lược và bước xây dựng bản đồ chiến lược.
