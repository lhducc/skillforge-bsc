# B4. Bản đồ chiến lược

## 1. Mục tiêu của bước

Bước **Bản đồ chiến lược** là bước CEO chuyển các chiến lược đã chọn ở B3 thành hệ thống mục tiêu chiến lược theo 4 góc độ BSC.

Ở B3, CEO đã chọn từ 1 đến 2 chiến lược để triển khai. Sang B4, mỗi chiến lược được chọn sẽ được cụ thể hóa thành các mục tiêu chiến lược thuộc 4 góc độ:

- Tài chính
- Khách hàng
- Quy trình nội bộ
- Học hỏi & phát triển

Sau đó CEO vẽ các đường liên kết nhân quả giữa các mục tiêu để thể hiện logic chiến lược.

Nếu CEO chọn 2 chiến lược ở B3, hệ thống sẽ cho CEO xây bản đồ riêng cho từng chiến lược, sau đó thực hiện bước gộp để tạo ra **bản đồ chiến lược tổng của công ty**.

Bản đồ chiến lược tổng sau B4 sẽ là đầu vào cho B5 - Mô hình xương cá.

---

## 2. Vai trò của B4 trong toàn workflow BSC

B4 là cầu nối giữa chiến lược cấp cao và hệ thống KPI phòng ban.

Luồng dữ liệu:

```text
B3. Chiến lược được chọn
→ B4. Bản đồ chiến lược
→ B5. Mô hình xương cá
→ B6. Phân bổ tỉ trọng
→ B7. Đo lường & Chỉ tiêu
→ B8. Action Plan
```

Ở B4, CEO chưa tạo KPI. CEO chỉ tạo **mục tiêu chiến lược**.

Các mục tiêu chiến lược này sau đó sẽ trở thành các **đầu cá lớn** ở B5 để trưởng phòng tham gia và tạo KPI nhỏ cho phòng ban.

---

## 3. Role được phép thao tác

### 3.1. CEO

CEO là người có quyền chính trong B4.

CEO được phép:

- Xem các chiến lược đã chọn từ B3
- Tạo mục tiêu chiến lược cho từng chiến lược
- Chỉnh sửa mục tiêu chiến lược khi BSC Strategy còn Draft
- Xóa mục tiêu chiến lược khi BSC Strategy còn Draft
- Vẽ đường liên kết nhân quả cho từng chiến lược
- Nếu có 2 chiến lược, thực hiện Keep / Remove / Merge / Edit để tạo bản đồ chiến lược tổng
- Vẽ lại đường liên kết nhân quả tổng sau khi gộp
- Hoàn thành B4 để chuyển sang B5

### 3.2. BSC Consultant

Nếu doanh nghiệp có BSC Consultant hỗ trợ, Consultant có thể ngồi cùng CEO để phân tích và nhập liệu.

Về mặt hệ thống, Consultant có thể sử dụng tài khoản CEO hoặc một tài khoản được CEO cấp quyền đặc biệt nếu sau này hệ thống hỗ trợ role này.

### 3.3. Các role khác

Các role sau không được xem hoặc thao tác B4:

- Trưởng phòng
- Nhân viên
- Company Admin
- System Admin

Lý do: B4 vẫn thuộc nhóm dữ liệu chiến lược cấp cao và có thể chứa bí mật kinh doanh của công ty.

System Admin chỉ có quyền monitoring hệ thống, không xem nội dung chiến lược chi tiết của doanh nghiệp.

---

## 4. Điều kiện truy cập bước

B4 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B3 - Kết quả chiến lược đã hoàn thành
- B3 có ít nhất 1 và tối đa 2 chiến lược được chọn
- Người thao tác có role CEO hoặc quyền tương đương

Nếu B3 chưa hoàn thành, hệ thống không cho CEO hoàn thành B4.

---

## 5. Khái niệm chính trong B4

## 5.1. Chiến lược được chọn

Chiến lược được chọn là chiến lược CEO đã chọn ở B3.

Số lượng:

- Tối thiểu: 1 chiến lược
- Tối đa: 2 chiến lược

Mỗi chiến lược được chọn sẽ được dùng để tạo một bản đồ chiến lược riêng.

---

## 5.2. Mục tiêu chiến lược

Mục tiêu chiến lược là các đầu mục chiến lược được CEO tạo ra cho từng chiến lược.

Mỗi mục tiêu chiến lược bắt buộc thuộc một trong 4 góc độ BSC:

| Mã | Tên góc độ |
|---|---|
| FINANCIAL | Tài chính |
| CUSTOMER | Khách hàng |
| INTERNAL_PROCESS | Quy trình nội bộ |
| LEARNING_AND_GROWTH | Học hỏi & phát triển |

Ví dụ:

| Góc độ | Mục tiêu chiến lược |
|---|---|
| Tài chính | Tăng doanh thu từ nhóm khách hàng SME |
| Khách hàng | Tăng mức độ hài lòng của khách hàng |
| Quy trình nội bộ | Rút ngắn thời gian xử lý yêu cầu khách hàng |
| Học hỏi & phát triển | Nâng cao năng lực tư vấn giải pháp của đội ngũ sales |

---

## 5.3. Đường liên kết nhân quả

Đường liên kết nhân quả là đường nối giữa các mục tiêu chiến lược để thể hiện logic:

```text
Mục tiêu A ảnh hưởng / dẫn đến Mục tiêu B
```

Ví dụ:

```text
Nâng cao năng lực tư vấn giải pháp của đội ngũ sales
→ Tăng chất lượng tư vấn khách hàng
→ Tăng tỷ lệ chuyển đổi khách hàng
→ Tăng doanh thu từ nhóm khách hàng SME
```

Đường liên kết nhân quả là một phần quan trọng của BSC vì BSC không chỉ liệt kê mục tiêu, mà còn thể hiện quan hệ nguyên nhân - kết quả giữa các mục tiêu.

---

## 5.4. Bản đồ chiến lược riêng

Bản đồ chiến lược riêng là bản đồ được tạo cho từng chiến lược mà CEO đã chọn ở B3.

Nếu CEO chọn 1 chiến lược, hệ thống có 1 bản đồ chiến lược riêng.

Nếu CEO chọn 2 chiến lược, hệ thống có 2 bản đồ chiến lược riêng.

Mỗi bản đồ chiến lược riêng gồm:

- Chiến lược nguồn
- Danh sách mục tiêu chiến lược
- Các mục tiêu được chia theo 4 góc độ BSC
- Đường liên kết nhân quả giữa các mục tiêu

---

## 5.5. Bản đồ chiến lược tổng

Bản đồ chiến lược tổng là bản đồ cuối cùng của công ty sau khi CEO hoàn tất B4.

Nếu CEO chọn 1 chiến lược ở B3, bản đồ chiến lược riêng của chiến lược đó có thể trở thành bản đồ chiến lược tổng.

Nếu CEO chọn 2 chiến lược ở B3, CEO cần thực hiện bước gộp để tạo bản đồ chiến lược tổng.

Bản đồ chiến lược tổng gồm:

- Danh sách mục tiêu chiến lược cuối cùng của công ty
- Đường liên kết nhân quả tổng
- Nguồn gốc của từng mục tiêu chiến lược nếu được tạo từ Keep hoặc Merge
- Trạng thái hoàn thành của B4

Bản đồ chiến lược tổng là dữ liệu được chuyển sang B5 để tạo mô hình xương cá.

---

# 6. User flow tổng quan của B4

## 6.1. Trường hợp CEO chọn 1 chiến lược ở B3

Luồng xử lý:

```text
CEO chọn 1 chiến lược ở B3
→ B4 hiển thị chiến lược đó
→ CEO tạo mục tiêu chiến lược theo đủ 4 góc độ BSC
→ Tổng mục tiêu chiến lược của chiến lược đó không vượt quá 12
→ CEO vẽ đường liên kết nhân quả
→ Bản đồ này trở thành bản đồ chiến lược tổng
→ CEO hoàn thành B4
```

Điều kiện:

- Phải có đủ 4 góc độ BSC
- Mỗi góc độ phải có ít nhất 1 mục tiêu chiến lược
- Tổng số mục tiêu chiến lược không vượt quá 12
- Nếu có nhiều hơn 1 mục tiêu, nên có ít nhất 1 đường liên kết nhân quả

---

## 6.2. Trường hợp CEO chọn 2 chiến lược ở B3

Luồng xử lý:

```text
CEO chọn 2 chiến lược ở B3
→ B4 hiển thị chiến lược 1 và chiến lược 2
→ CEO tạo bản đồ riêng cho chiến lược 1
→ CEO tạo bản đồ riêng cho chiến lược 2
→ Mỗi chiến lược phải đủ 4 góc độ BSC
→ Mỗi chiến lược có tối đa 12 mục tiêu chiến lược
→ CEO vẽ đường nhân quả riêng cho từng chiến lược
→ Hệ thống render toàn bộ mục tiêu từ 2 chiến lược, tối đa 24 mục tiêu
→ CEO Keep / Remove / Merge / Edit để tạo danh sách mục tiêu chiến lược tổng
→ CEO vẽ lại đường nhân quả tổng
→ CEO hoàn thành B4
```

Điều kiện:

- Mỗi bản đồ riêng phải có đủ 4 góc độ BSC
- Mỗi bản đồ riêng không vượt quá 12 mục tiêu
- Tổng mục tiêu nguồn từ 2 chiến lược không vượt quá 24
- Bản đồ tổng sau khi gộp không vượt quá 24 mục tiêu
- Bản đồ tổng phải có đủ 4 góc độ BSC
- Nếu bản đồ tổng có nhiều hơn 1 mục tiêu, nên có ít nhất 1 đường liên kết nhân quả tổng

---

# 7. Tạo mục tiêu chiến lược cho từng chiến lược

## 7.1. Dữ liệu cần nhập cho một mục tiêu chiến lược

Khi CEO tạo mục tiêu chiến lược, hệ thống cần lưu:

- Tên mục tiêu chiến lược
- Mô tả mục tiêu chiến lược
- Góc độ BSC
- Chiến lược nguồn
- Thứ tự hiển thị
- Trạng thái

Ví dụ:

```text
Tên mục tiêu:
Tăng doanh thu từ nhóm khách hàng SME

Mô tả:
Tập trung mở rộng doanh thu từ nhóm khách hàng doanh nghiệp vừa và nhỏ thông qua sản phẩm cốt lõi của công ty.

Góc độ:
Tài chính

Chiến lược nguồn:
SO-01
```

---

## 7.2. Rule tạo mục tiêu chiến lược

Quy tắc:

- Tên mục tiêu không được rỗng
- Mục tiêu phải thuộc đúng một góc độ BSC
- Mục tiêu phải thuộc đúng một chiến lược được chọn ở B3
- Mỗi chiến lược phải có đủ 4 góc độ BSC
- Mỗi góc độ trong một chiến lược phải có ít nhất 1 mục tiêu
- Tổng số mục tiêu của mỗi chiến lược không vượt quá 12
- CEO có thể chỉnh sửa/xóa mục tiêu khi BSC Strategy còn Draft
- Nếu xóa mục tiêu đã có liên kết nhân quả, hệ thống cần xóa hoặc cảnh báo các liên kết liên quan

---

## 7.3. Gợi ý phân bổ mục tiêu theo 4 góc độ

Hệ thống không bắt buộc số lượng mục tiêu mỗi góc độ phải bằng nhau.

Ví dụ một chiến lược có thể có 9 mục tiêu:

| Góc độ | Số mục tiêu |
|---|---:|
| Tài chính | 3 |
| Khách hàng | 3 |
| Quy trình nội bộ | 2 |
| Học hỏi & phát triển | 1 |
| Tổng | 9 |

Chỉ cần đảm bảo:

- Đủ 4 góc độ
- Tổng mục tiêu không vượt quá 12

---

# 8. Vẽ đường liên kết nhân quả cho từng chiến lược

## 8.1. Mục tiêu

Sau khi tạo mục tiêu chiến lược cho một chiến lược, CEO vẽ đường liên kết nhân quả để thể hiện các mục tiêu ảnh hưởng lẫn nhau như thế nào.

Ví dụ:

```text
Nâng cao năng lực đội ngũ sales
→ Tăng chất lượng tư vấn khách hàng
→ Tăng tỷ lệ khách hàng hài lòng
→ Tăng doanh thu SME
```

---

## 8.2. Dữ liệu cần lưu cho một đường liên kết

Mỗi đường liên kết nhân quả cần lưu:

- ID mục tiêu nguồn
- ID mục tiêu đích
- Bản đồ chiến lược mà liên kết thuộc về
- Ghi chú nếu có
- Thứ tự hiển thị nếu cần

Ví dụ:

```text
source_objective_id: OBJ_001
target_objective_id: OBJ_002
map_type: STRATEGY_MAP
selected_strategy_id: SO_01
```

---

## 8.3. Rule vẽ liên kết nhân quả

Quy tắc:

- Không được tạo liên kết từ một mục tiêu đến chính nó
- Không được tạo liên kết trùng lặp giữa cùng một cặp mục tiêu
- Chỉ được liên kết các mục tiêu thuộc cùng một bản đồ chiến lược riêng
- Nếu mục tiêu nguồn hoặc mục tiêu đích bị xóa, liên kết liên quan phải bị xóa hoặc đánh dấu không hợp lệ
- Hệ thống không nên tự ép hướng liên kết theo tầng BSC trong MVP
- FE có thể hỗ trợ hiển thị theo bố cục 4 tầng để CEO dễ vẽ đúng logic BSC

Gợi ý hiển thị:

```text
Học hỏi & phát triển
→ Quy trình nội bộ
→ Khách hàng
→ Tài chính
```

Đây là hướng nhân quả phổ biến trong BSC, nhưng trong MVP hệ thống có thể chỉ hướng dẫn bằng UI thay vì chặn cứng ở backend.

---

# 9. Gộp bản đồ chiến lược khi CEO chọn 2 chiến lược

## 9.1. Mục tiêu

Khi CEO chọn 2 chiến lược ở B3, mỗi chiến lược sẽ có một bản đồ riêng. Tuy nhiên, công ty cần một bản đồ chiến lược tổng duy nhất để triển khai xuống các bước sau.

Vì vậy hệ thống cần có bước gộp bản đồ chiến lược.

---

## 9.2. Nguồn dữ liệu gộp

Nguồn dữ liệu là toàn bộ mục tiêu chiến lược từ 2 bản đồ riêng.

Ví dụ:

```text
Chiến lược A có 12 mục tiêu
Chiến lược B có 12 mục tiêu
→ Tổng nguồn gộp tối đa 24 mục tiêu
```

Hệ thống render toàn bộ mục tiêu này để CEO xử lý.

---

## 9.3. Các thao tác trong bước gộp

CEO có thể thực hiện 4 thao tác:

| Thao tác | Ý nghĩa |
|---|---|
| Keep | Giữ mục tiêu gốc vào bản đồ tổng |
| Remove | Loại mục tiêu khỏi bản đồ tổng |
| Merge | Gộp nhiều mục tiêu tương đồng thành một mục tiêu mới |
| Edit / Rename | Chỉnh tên hoặc mô tả mục tiêu trong bản đồ tổng |

---

## 9.4. Keep mục tiêu

Khi CEO chọn Keep một mục tiêu, hệ thống tạo một mục tiêu trong bản đồ tổng dựa trên mục tiêu gốc.

Dữ liệu cần lưu:

- Mục tiêu tổng mới
- Source là mục tiêu gốc
- Loại mục tiêu tổng: ORIGINAL
- Góc độ BSC được giữ từ mục tiêu gốc
- Tên/mô tả có thể được CEO chỉnh lại nếu cần

Ví dụ:

```text
Mục tiêu gốc:
Tăng doanh thu từ khách hàng SME

CEO chọn Keep
→ Mục tiêu này xuất hiện trong bản đồ chiến lược tổng
```

---

## 9.5. Remove mục tiêu

Khi CEO chọn Remove một mục tiêu, mục tiêu đó không được đưa vào bản đồ chiến lược tổng.

Quy tắc:

- Remove không xóa mục tiêu gốc trong bản đồ riêng
- Remove chỉ có nghĩa là mục tiêu đó không được đưa vào bản đồ tổng
- Mục tiêu bị Remove sẽ không được chuyển sang B5

Ví dụ:

```text
Mục tiêu gốc:
Mở rộng kênh bán hàng truyền thống

CEO chọn Remove
→ Mục tiêu này không xuất hiện trong bản đồ chiến lược tổng
→ Mục tiêu này không trở thành đầu cá ở B5
```

---

## 9.6. Merge mục tiêu

Merge là thao tác gộp nhiều mục tiêu tương đồng thành một mục tiêu chiến lược tổng mới.

Đây là **manual merge**, không phải auto merge.

Hệ thống không tự quyết định mục tiêu nào giống nhau. CEO là người chọn các mục tiêu cần gộp.

Luồng merge:

```text
CEO chọn từ 2 mục tiêu trở lên
→ CEO bấm Merge
→ Hệ thống mở form nhập mục tiêu tổng mới
→ CEO nhập tên mục tiêu mới
→ CEO nhập mô tả nếu cần
→ CEO xác nhận
→ Hệ thống tạo mục tiêu tổng mới
→ Hệ thống lưu mapping nguồn gốc từ các mục tiêu đã merge
```

Ví dụ:

```text
Mục tiêu A1:
Tăng doanh thu khách hàng SME

Mục tiêu B3:
Mở rộng doanh thu nhóm doanh nghiệp nhỏ

CEO merge thành:
Tăng trưởng doanh thu từ nhóm khách hàng SME
```

---

## 9.7. Rule merge trong MVP

Để tránh phức tạp nghiệp vụ và kỹ thuật, MVP nên áp dụng rule:

- Chỉ cho merge các mục tiêu thuộc cùng một góc độ BSC
- Không cho merge mục tiêu thuộc các góc độ khác nhau
- Mục tiêu sau khi merge giữ góc độ BSC của nhóm mục tiêu được merge
- Cần chọn ít nhất 2 mục tiêu để merge
- Một mục tiêu nguồn chỉ nên thuộc một mục tiêu tổng
- Mục tiêu đã được merge thì không nên được Keep riêng lẻ nữa, trừ khi CEO hủy merge
- CEO có thể hủy merge khi BSC Strategy còn Draft

Ví dụ hợp lệ:

```text
Mục tiêu A1 thuộc Tài chính
Mục tiêu B3 thuộc Tài chính
→ Có thể merge
```

Ví dụ không hợp lệ:

```text
Mục tiêu A1 thuộc Tài chính
Mục tiêu B3 thuộc Khách hàng
→ Không cho merge trong MVP
```

Lý do: mỗi mục tiêu chiến lược trong BSC phải thuộc một góc độ rõ ràng. Nếu merge khác góc độ, hệ thống sẽ khó xác định mục tiêu mới thuộc góc độ nào.

---

## 9.8. Edit / Rename mục tiêu tổng

Sau khi Keep hoặc Merge, CEO có thể chỉnh tên/mô tả của mục tiêu trong bản đồ tổng.

Quy tắc:

- Không làm thay đổi mục tiêu gốc ở bản đồ riêng
- Chỉ thay đổi nội dung hiển thị trong bản đồ tổng
- Cần lưu lại nguồn gốc để trace được mục tiêu tổng đến từ đâu

Ví dụ:

```text
Mục tiêu gốc:
Tăng doanh thu khách hàng SME

CEO chỉnh trong bản đồ tổng thành:
Tăng trưởng doanh thu bền vững từ nhóm khách hàng SME
```

---

## 9.9. Output của bước gộp

Sau khi gộp, hệ thống tạo ra danh sách mục tiêu chiến lược tổng.

Mỗi mục tiêu tổng cần lưu:

- ID mục tiêu tổng
- Tên mục tiêu tổng
- Mô tả
- Góc độ BSC
- Loại mục tiêu: ORIGINAL, MERGED hoặc MANUAL_EDITED
- Danh sách mục tiêu nguồn
- Thứ tự hiển thị
- Trạng thái

Ví dụ mapping:

```text
Final Objective:
Tăng trưởng doanh thu từ nhóm khách hàng SME

Type:
MERGED

Sources:
- A1: Tăng doanh thu khách hàng SME
- B3: Mở rộng doanh thu nhóm doanh nghiệp nhỏ
```

---

# 10. Vẽ lại đường nhân quả tổng

## 10.1. Mục tiêu

Sau khi CEO chốt danh sách mục tiêu chiến lược tổng, CEO cần vẽ lại đường liên kết nhân quả cho bản đồ tổng.

Không nên tự động lấy nguyên đường nhân quả từ bản đồ riêng rồi ghép lại, vì sau khi Keep / Remove / Merge / Edit, logic nhân quả tổng thể có thể thay đổi.

---

## 10.2. Dữ liệu cần lưu cho đường nhân quả tổng

Mỗi đường liên kết nhân quả tổng cần lưu:

- ID mục tiêu tổng nguồn
- ID mục tiêu tổng đích
- Bản đồ tổng mà liên kết thuộc về
- Ghi chú nếu có
- Thứ tự hiển thị nếu cần

Ví dụ:

```text
source_final_objective_id: FINAL_OBJ_001
target_final_objective_id: FINAL_OBJ_002
map_type: FINAL_STRATEGY_MAP
```

---

## 10.3. Rule vẽ đường nhân quả tổng

Quy tắc:

- Không được tạo liên kết từ một mục tiêu đến chính nó
- Không được tạo liên kết trùng lặp giữa cùng một cặp mục tiêu
- Chỉ được liên kết các mục tiêu thuộc bản đồ chiến lược tổng
- Không được liên kết tới mục tiêu đã bị Remove
- Nếu một mục tiêu tổng bị xóa hoặc bị hủy khỏi bản đồ tổng, liên kết liên quan phải bị xóa hoặc đánh dấu không hợp lệ
- Nếu bản đồ tổng có nhiều hơn 1 mục tiêu, nên có ít nhất 1 liên kết nhân quả
- FE có thể hỗ trợ hiển thị theo bố cục 4 tầng BSC để CEO dễ vẽ

---

# 11. Validate tổng thể của B4

Để hoàn thành B4 và chuyển sang B5, hệ thống cần kiểm tra:

- B1 đã hoàn thành
- B2 đã hoàn thành
- B3 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- Người thao tác có role CEO hoặc quyền tương đương
- B3 có ít nhất 1 và tối đa 2 chiến lược được chọn

---

## 11.1. Validate khi CEO chọn 1 chiến lược ở B3

Hệ thống cần kiểm tra:

- Chiến lược được chọn vẫn tồn tại và hợp lệ
- Bản đồ riêng của chiến lược có đủ 4 góc độ BSC
- Mỗi góc độ có ít nhất 1 mục tiêu chiến lược
- Tổng mục tiêu chiến lược không vượt quá 12
- Tên mục tiêu không được rỗng
- Mỗi mục tiêu thuộc đúng một góc độ BSC
- Không có liên kết nhân quả trùng lặp
- Không có liên kết nhân quả tự trỏ về chính nó
- Bản đồ chiến lược riêng được đánh dấu là bản đồ chiến lược tổng

---

## 11.2. Validate khi CEO chọn 2 chiến lược ở B3

Hệ thống cần kiểm tra:

### Với từng bản đồ riêng

- Mỗi chiến lược được chọn vẫn tồn tại và hợp lệ
- Mỗi bản đồ riêng có đủ 4 góc độ BSC
- Mỗi góc độ trong từng bản đồ riêng có ít nhất 1 mục tiêu chiến lược
- Mỗi bản đồ riêng không vượt quá 12 mục tiêu chiến lược
- Tên mục tiêu không được rỗng
- Mỗi mục tiêu thuộc đúng một góc độ BSC
- Không có liên kết nhân quả trùng lặp trong cùng bản đồ
- Không có liên kết nhân quả tự trỏ về chính nó

### Với bản đồ tổng

- Danh sách mục tiêu tổng không được rỗng
- Bản đồ tổng có đủ 4 góc độ BSC
- Mỗi góc độ trong bản đồ tổng có ít nhất 1 mục tiêu chiến lược
- Tổng mục tiêu chiến lược trong bản đồ tổng không vượt quá 24
- Mục tiêu tổng có tên không rỗng
- Mỗi mục tiêu tổng thuộc đúng một góc độ BSC
- Mục tiêu nguồn chỉ được map vào tối đa một mục tiêu tổng
- Merge chỉ gồm các mục tiêu cùng góc độ BSC trong MVP
- Không có liên kết nhân quả tổng trùng lặp
- Không có liên kết nhân quả tổng tự trỏ về chính nó

---

# 12. Output của B4

Sau khi hoàn thành B4, hệ thống lưu lại:

- Bản đồ chiến lược riêng cho từng chiến lược được chọn
- Danh sách mục tiêu chiến lược của từng bản đồ riêng
- Đường liên kết nhân quả của từng bản đồ riêng
- Bản đồ chiến lược tổng của công ty
- Danh sách mục tiêu chiến lược tổng
- Mapping giữa mục tiêu tổng và mục tiêu nguồn
- Đường liên kết nhân quả tổng
- Người hoàn thành B4
- Thời điểm hoàn thành B4
- Trạng thái hoàn thành của B4

Dữ liệu quan trọng nhất được chuyển sang B5 là:

```text
Danh sách mục tiêu chiến lược tổng của công ty
```

Các mục tiêu chiến lược tổng này sẽ trở thành các **đầu cá lớn** trong B5 - Mô hình xương cá.

---

# 13. Liên kết với B5 - Mô hình xương cá

Sau khi B4 hoàn thành, hệ thống mở khóa B5.

Ở B5:

- CEO không thao tác, chỉ xem
- Trưởng phòng đăng nhập vào hệ thống
- Trưởng phòng xem danh sách mục tiêu chiến lược tổng
- Trưởng phòng tự chọn mục tiêu mà phòng ban mình tham gia
- Trưởng phòng tạo KPI nhỏ cho từng mục tiêu đã chọn
- Hệ thống dùng màu phòng ban để render KPI theo từng phòng ban
- Mỗi phòng ban có một mô hình xương cá riêng được gộp từ toàn bộ KPI của phòng đó

Ví dụ:

```text
B4 tạo ra 21 mục tiêu chiến lược tổng
→ B5 có 21 đầu cá lớn

Phòng HR tham gia 8/21 đầu cá
→ HR tạo KPI nhỏ cho 8 đầu cá đó
→ Hệ thống gộp toàn bộ KPI của HR thành mô hình xương cá riêng của phòng HR
```

---

# 14. Ảnh hưởng khi chỉnh sửa B3 sau khi đã có B4

Khi BSC Strategy còn Draft, CEO có thể quay lại B3 để chọn lại chiến lược.

Tuy nhiên, nếu B3 thay đổi sau khi B4 đã có dữ liệu, hệ thống cần xử lý cẩn thận.

Các trường hợp:

## 14.1. CEO bỏ chọn một chiến lược đã có bản đồ ở B4

Hệ thống cần cảnh báo:

```text
Chiến lược này đã có bản đồ chiến lược ở B4. Nếu bỏ chọn, dữ liệu bản đồ liên quan có thể không còn hợp lệ.
```

Có thể xử lý theo hướng MVP:

- Đánh dấu B4 là Invalidated
- Yêu cầu CEO xác nhận lại hoặc tạo lại B4

---

## 14.2. CEO chọn thêm chiến lược thứ hai sau khi trước đó chỉ chọn một chiến lược

Hệ thống cần:

- Giữ lại bản đồ của chiến lược đã có nếu còn hợp lệ
- Yêu cầu CEO tạo bản đồ cho chiến lược mới
- Yêu cầu CEO thực hiện bước gộp bản đồ chiến lược
- Đánh dấu B4 chưa hoàn thành cho đến khi gộp xong

---

## 14.3. CEO thay đổi toàn bộ chiến lược được chọn

Hệ thống nên:

- Đánh dấu B4 là Invalidated
- Không tự xóa dữ liệu cũ ngay lập tức
- Yêu cầu CEO xác nhận tạo lại B4
- Có thể lưu dữ liệu cũ dưới dạng draft/archived nếu cần audit

---

# 15. Trạng thái của bước

B4 có thể có các trạng thái:

- Not Started: chưa tạo bản đồ chiến lược
- In Progress: đang tạo bản đồ chiến lược
- Ready To Merge: đã tạo xong bản đồ riêng và sẵn sàng gộp
- Merging: đang xử lý bản đồ tổng
- Completed: đã hoàn thành bản đồ chiến lược tổng
- Invalidated: dữ liệu B3 hoặc chiến lược nguồn thay đổi làm B4 không còn hợp lệ
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B4.

Khi BSC Strategy đã Active, B4 bị hạn chế chỉnh sửa vì thay đổi bản đồ chiến lược tổng sẽ ảnh hưởng trực tiếp đến B5, B6, B7 và B8.

---

# 16. Gợi ý UI/UX cho B4

## 16.1. Màn hình tạo bản đồ riêng

Gợi ý layout:

- Cột trái: thông tin chiến lược được chọn
- Khu vực chính: 4 lane theo 4 góc độ BSC
- Trong mỗi lane: danh sách mục tiêu chiến lược
- Cho phép thêm/sửa/xóa mục tiêu
- Cho phép kéo thả để sắp xếp thứ tự
- Cho phép vẽ đường liên kết nhân quả giữa các mục tiêu

4 lane có thể hiển thị theo thứ tự:

```text
Tài chính
Khách hàng
Quy trình nội bộ
Học hỏi & phát triển
```

Hoặc theo hướng nhân quả từ dưới lên:

```text
Học hỏi & phát triển
Quy trình nội bộ
Khách hàng
Tài chính
```

---

## 16.2. Màn hình gộp bản đồ

Gợi ý layout:

- Cột trái: mục tiêu từ chiến lược 1
- Cột phải: mục tiêu từ chiến lược 2
- Khu vực giữa hoặc phía dưới: bản đồ chiến lược tổng
- CEO có thể chọn Keep, Remove, Merge
- Khi Merge, mở modal để nhập tên/mô tả mục tiêu tổng
- Các mục tiêu đã được dùng trong Merge thì đánh dấu là đã xử lý
- Các mục tiêu bị Remove thì làm mờ hoặc chuyển sang nhóm Removed

---

## 16.3. Màn hình vẽ nhân quả tổng

Gợi ý layout:

- Render danh sách mục tiêu tổng theo 4 góc độ BSC
- CEO kéo thả hoặc click để tạo liên kết
- Hiển thị mũi tên nhân quả
- Cho phép xóa liên kết
- Cảnh báo nếu chưa có liên kết nhân quả nào
- Cảnh báo nếu có mục tiêu bị cô lập, không có liên kết nào

---

# 17. Ghi chú nghiệp vụ

B4 là bước cực kỳ quan trọng vì nó biến chiến lược được chọn thành bản đồ chiến lược cụ thể.

Hệ thống không tự quyết định mục tiêu nào đúng, không tự merge mục tiêu và không tự vẽ đường nhân quả thay CEO trong MVP.

Vai trò của hệ thống là:

- Cung cấp khung 4 góc độ BSC
- Kiểm soát mỗi chiến lược có đủ 4 góc độ
- Kiểm soát số lượng mục tiêu chiến lược
- Cho CEO vẽ quan hệ nhân quả
- Cho CEO gộp mục tiêu thủ công bằng Keep / Remove / Merge / Edit
- Lưu lại bản đồ chiến lược tổng để chuyển sang B5

Kết quả quan trọng nhất của B4 là **bản đồ chiến lược tổng của công ty**.

Từ B5 trở đi, toàn bộ KPI phòng ban, phân bổ tỉ trọng, chỉ tiêu đo lường và action plan đều phải bám vào bản đồ chiến lược tổng này.
