# B2. Xây dựng chiến lược

## 1. Mục tiêu của bước

Bước **Xây dựng chiến lược** là bước CEO sử dụng các khung phân tích chiến lược để tạo ra danh sách chiến lược khả thi cho doanh nghiệp.

Ở bước này, hệ thống cung cấp các khung phân tích có cấu trúc gồm:

- Mô hình 7S
- Mô hình 5 áp lực cạnh tranh
- Mô hình PESTEL
- Phân tích SWOT
- Xây dựng chiến lược SO, ST, WO, WT

Dữ liệu ở bước này vẫn thuộc nhóm **bí mật chiến lược của công ty**, chỉ CEO hoặc người được CEO cấp quyền đặc biệt mới được xem và thao tác.

Hệ thống không tự phân tích thay CEO. Hệ thống chỉ đóng vai trò là khung nhập liệu, khung chọn dữ liệu và nơi lưu trữ logic chiến lược theo đúng workflow BSC.

---

## 2. Role được phép thao tác

### 2.1. CEO

CEO là người có quyền chính trong B2.

CEO được phép:

- Xem dữ liệu B2
- Tạo dữ liệu B2
- Chỉnh sửa dữ liệu B2 khi BSC Strategy còn ở trạng thái Draft
- Tạo SWOT từ dữ liệu 7S, 5 Forces và PESTEL
- Tạo chiến lược SO/ST/WO/WT từ SWOT
- Hoàn thành B2 để chuyển sang B3

### 2.2. BSC Consultant

Nếu doanh nghiệp có BSC Consultant hỗ trợ, Consultant có thể ngồi cùng CEO để nhập liệu.

Về mặt hệ thống, Consultant có thể sử dụng tài khoản CEO hoặc một tài khoản được CEO cấp quyền đặc biệt nếu sau này hệ thống hỗ trợ role này.

### 2.3. Các role khác

Các role sau không được xem hoặc thao tác B2:

- Trưởng phòng
- Nhân viên
- Company Admin
- System Admin

Lý do: B2 chứa các phân tích nội bộ, phân tích thị trường, phân tích đối thủ và định hướng chiến lược quan trọng của doanh nghiệp.

System Admin chỉ có quyền monitoring hệ thống, không xem nội dung chiến lược chi tiết của doanh nghiệp.

---

## 3. Điều kiện truy cập bước

B2 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- Người thao tác có role CEO hoặc quyền tương đương

Nếu B1 chưa hoàn thành, hệ thống không cho CEO hoàn thành B2.

---

## 4. Cấu trúc tổng quan của B2

B2 gồm 5 phần nhỏ:

```text
B2.1. Mô hình 7S
B2.2. Mô hình 5 áp lực cạnh tranh
B2.3. Mô hình PESTEL
B2.4. Phân tích SWOT
B2.5. Xây dựng chiến lược SO/ST/WO/WT
```

Luồng dữ liệu chính:

```text
7S
→ sinh nguồn dữ liệu để chọn S và W trong SWOT

5 Forces + PESTEL
→ sinh nguồn dữ liệu để chọn O và T trong SWOT

SWOT
→ sinh nguồn dữ liệu để tạo chiến lược SO/ST/WO/WT

SO/ST/WO/WT
→ sinh danh sách chiến lược để B3 lựa chọn
```

---

# B2.1. Mô hình 7S

## 1. Mục tiêu

Mô hình 7S dùng để CEO đánh giá các yếu tố nội bộ của doanh nghiệp.

Dữ liệu 7S sau này được dùng làm nguồn để chọn:

- S - Strengths / Điểm mạnh
- W - Weaknesses / Điểm yếu

trong bước SWOT.

---

## 2. Các nhóm dữ liệu của 7S

Mô hình 7S gồm 7 yếu tố:

| Mã | Tên tiếng Anh | Tên tiếng Việt gợi ý |
|---|---|---|
| STRATEGY | Strategy | Chiến lược |
| STRUCTURE | Structure | Cơ cấu tổ chức |
| SYSTEMS | Systems | Hệ thống / Quy trình |
| SHARED_VALUES | Shared Values | Giá trị chung |
| SKILLS | Skills | Kỹ năng / Năng lực |
| STYLE | Style | Phong cách quản lý |
| STAFF | Staff | Nhân sự |

Mỗi yếu tố được biểu diễn dưới dạng **dynamic text list**.

Ví dụ yếu tố `Skills`:

- Đội ngũ kỹ thuật triển khai nhanh
- Nhân viên sales chưa mạnh về tư vấn giải pháp
- Bộ phận CSKH có kinh nghiệm xử lý khách hàng SME

---

## 3. Quy tắc nhập liệu

- Mỗi yếu tố 7S là một nhóm dynamic text list
- CEO có thể thêm nhiều item trong từng nhóm
- Không cho lưu item rỗng
- Nên trim khoảng trắng đầu/cuối trước khi lưu
- Không bắt buộc giới hạn số lượng item
- Mỗi item phải thuộc đúng một yếu tố 7S
- CEO có thể sửa/xóa item khi BSC Strategy còn Draft

---

## 4. Output của B2.1

Sau khi hoàn thành B2.1, hệ thống lưu danh sách item của 7S.

Các item này sẽ được render lại ở B2.4 - SWOT để CEO chọn vào:

- S - Strengths
- W - Weaknesses

---

# B2.2. Mô hình 5 áp lực cạnh tranh

## 1. Mục tiêu

Mô hình 5 áp lực cạnh tranh dùng để CEO đánh giá môi trường cạnh tranh xung quanh doanh nghiệp.

Dữ liệu 5 Forces sau này được dùng làm nguồn để chọn:

- O - Opportunities / Cơ hội
- T - Threats / Thách thức

trong bước SWOT.

---

## 2. Các nhóm dữ liệu của 5 áp lực cạnh tranh

Mô hình 5 Forces gồm 5 yếu tố:

| Mã | Tên tiếng Anh | Tên tiếng Việt gợi ý |
|---|---|---|
| COMPETITIVE_RIVALRY | Competitive Rivalry | Mức độ cạnh tranh trong ngành |
| SUPPLIER_POWER | Supplier Power | Quyền lực nhà cung cấp |
| BUYER_POWER | Buyer Power | Quyền lực khách hàng |
| THREAT_OF_SUBSTITUTES | Threat of Substitutes | Nguy cơ từ sản phẩm thay thế |
| THREAT_OF_NEW_ENTRANTS | Threat of New Entrants | Nguy cơ từ đối thủ mới gia nhập |

Mỗi yếu tố được biểu diễn dưới dạng **dynamic text list**.

Ví dụ yếu tố `Competitive Rivalry`:

- Nhiều đối thủ đang cung cấp giải pháp tương tự
- Một số đối thủ cạnh tranh bằng giá thấp
- Thị trường SME còn phân mảnh, chưa có đơn vị thống trị tuyệt đối

---

## 3. Quy tắc nhập liệu

- Mỗi áp lực cạnh tranh là một nhóm dynamic text list
- CEO có thể thêm nhiều item trong từng nhóm
- Không cho lưu item rỗng
- Nên trim khoảng trắng đầu/cuối trước khi lưu
- Không bắt buộc giới hạn số lượng item
- Mỗi item phải thuộc đúng một yếu tố 5 Forces
- CEO có thể sửa/xóa item khi BSC Strategy còn Draft

---

## 4. Output của B2.2

Sau khi hoàn thành B2.2, hệ thống lưu danh sách item của 5 Forces.

Các item này sẽ được render lại ở B2.4 - SWOT để CEO chọn vào:

- O - Opportunities
- T - Threats

---

# B2.3. Mô hình PESTEL

## 1. Mục tiêu

Mô hình PESTEL dùng để CEO đánh giá các yếu tố môi trường vĩ mô có thể ảnh hưởng đến doanh nghiệp.

Dữ liệu PESTEL sau này được dùng làm nguồn để chọn:

- O - Opportunities / Cơ hội
- T - Threats / Thách thức

trong bước SWOT.

---

## 2. Các nhóm dữ liệu của PESTEL

Mô hình PESTEL gồm 6 yếu tố:

| Mã | Tên tiếng Anh | Tên tiếng Việt gợi ý |
|---|---|---|
| POLITICAL | Political | Chính trị |
| ECONOMIC | Economic | Kinh tế |
| SOCIAL | Social | Xã hội |
| TECHNOLOGICAL | Technological | Công nghệ |
| ENVIRONMENTAL | Environmental | Môi trường |
| LEGAL | Legal | Pháp lý |

Mỗi yếu tố được biểu diễn dưới dạng **dynamic text list**.

Ví dụ yếu tố `Technological`:

- Doanh nghiệp SME bắt đầu quan tâm đến chuyển đổi số
- AI giúp tự động hóa nhiều quy trình vận hành
- Khách hàng ngày càng quen với dashboard và báo cáo realtime

---

## 3. Quy tắc nhập liệu

- Mỗi yếu tố PESTEL là một nhóm dynamic text list
- CEO có thể thêm nhiều item trong từng nhóm
- Không cho lưu item rỗng
- Nên trim khoảng trắng đầu/cuối trước khi lưu
- Không bắt buộc giới hạn số lượng item
- Mỗi item phải thuộc đúng một yếu tố PESTEL
- CEO có thể sửa/xóa item khi BSC Strategy còn Draft

---

## 4. Output của B2.3

Sau khi hoàn thành B2.3, hệ thống lưu danh sách item của PESTEL.

Các item này sẽ được render lại ở B2.4 - SWOT để CEO chọn vào:

- O - Opportunities
- T - Threats

---

# B2.4. Phân tích SWOT

## 1. Mục tiêu

SWOT dùng để CEO chọn lọc các yếu tố quan trọng từ các mô hình phân tích trước đó.

SWOT không phải nhập tự do hoàn toàn. SWOT được tạo bằng cách chọn dữ liệu từ:

- 7S cho S và W
- 5 Forces + PESTEL cho O và T

Cách này giúp các yếu tố SWOT có nguồn gốc rõ ràng, tránh việc nhập cảm tính và rời rạc.

---

## 2. Nguồn dữ liệu của SWOT

| Nhóm SWOT | Ý nghĩa | Nguồn chọn dữ liệu |
|---|---|---|
| S | Strengths / Điểm mạnh | Mô hình 7S |
| W | Weaknesses / Điểm yếu | Mô hình 7S |
| O | Opportunities / Cơ hội | 5 Forces + PESTEL |
| T | Threats / Thách thức | 5 Forces + PESTEL |

---

## 3. Rule chọn S và W

CEO chọn S và W từ toàn bộ item của mô hình 7S.

Hệ thống cần render đầy đủ 7 yếu tố 7S để CEO dễ chọn.

Quy tắc:

- Một item 7S chỉ được chọn vào một trong hai nhóm: S hoặc W
- Nếu một item đã được chọn vào S thì không được chọn vào W
- Nếu một item đã được chọn vào W thì không được chọn vào S
- Item đã chọn ở một nhóm cần bị disable hoặc ẩn ở nhóm còn lại
- CEO có thể bỏ chọn item khi BSC Strategy còn Draft
- Khi bỏ chọn, item được mở lại để chọn vào nhóm khác

Ví dụ:

```text
Item từ 7S:
"Đội ngũ kỹ thuật triển khai nhanh"

Nếu CEO chọn item này vào S:
→ item này không còn được chọn vào W
```

---

## 4. Rule chọn O và T

CEO chọn O và T từ toàn bộ item của:

- 5 Forces
- PESTEL

Hệ thống cần render đầy đủ các yếu tố của 5 Forces và PESTEL để CEO dễ chọn.

Quy tắc:

- Một item từ 5 Forces hoặc PESTEL chỉ được chọn vào một trong hai nhóm: O hoặc T
- Nếu một item đã được chọn vào O thì không được chọn vào T
- Nếu một item đã được chọn vào T thì không được chọn vào O
- Item đã chọn ở một nhóm cần bị disable hoặc ẩn ở nhóm còn lại
- CEO có thể bỏ chọn item khi BSC Strategy còn Draft
- Khi bỏ chọn, item được mở lại để chọn vào nhóm khác

Ví dụ:

```text
Item từ PESTEL:
"Doanh nghiệp SME bắt đầu quan tâm đến chuyển đổi số"

Nếu CEO chọn item này vào O:
→ item này không còn được chọn vào T
```

---

## 5. Validate của SWOT

Để hoàn thành B2.4, hệ thống nên kiểm tra:

- Có ít nhất 1 item trong S
- Có ít nhất 1 item trong W
- Có ít nhất 1 item trong O
- Có ít nhất 1 item trong T
- Không có item 7S nào đồng thời nằm trong cả S và W
- Không có item 5 Forces/PESTEL nào đồng thời nằm trong cả O và T
- Tất cả item được chọn phải thuộc cùng BSC Strategy hiện tại
- BSC Strategy vẫn đang ở trạng thái Draft

---

## 6. Output của B2.4

Sau khi hoàn thành SWOT, hệ thống lưu lại:

- Danh sách S
- Danh sách W
- Danh sách O
- Danh sách T
- Mapping giữa SWOT item và item gốc

Ví dụ mapping:

```text
SWOT item S1
→ source_type: 7S
→ source_item_id: 7S_ITEM_001
```

Mapping này giúp hệ thống biết SWOT item được tạo từ dữ liệu nào.

---

# B2.5. Xây dựng chiến lược SO/ST/WO/WT

## 1. Mục tiêu

Sau khi có SWOT, CEO xây dựng các chiến lược hành động theo 4 nhóm:

| Nhóm chiến lược | Ý nghĩa |
|---|---|
| SO | Dùng điểm mạnh để tận dụng cơ hội |
| ST | Dùng điểm mạnh để đối phó thách thức |
| WO | Khắc phục điểm yếu để tận dụng cơ hội |
| WT | Giảm điểm yếu để hạn chế thách thức |

Kết quả của B2.5 là danh sách chiến lược ứng viên. Danh sách này sẽ được chuyển sang B3 để CEO chọn ra 1–2 chiến lược triển khai chính thức.

---

## 2. Số lượng chiến lược

Quy tắc:

- Tổng số chiến lược SO/ST/WO/WT không được vượt quá 12
- User không bắt buộc phải tạo đủ cả 4 nhóm SO, ST, WO, WT
- Theo framework, tốt nhất nên có đủ cả 4 nhóm
- Hệ thống có thể cảnh báo nếu thiếu nhóm chiến lược, nhưng không nhất thiết chặn
- Cần có ít nhất 1 chiến lược để hoàn thành B2

Ví dụ hợp lệ:

| Nhóm | Số lượng |
|---|---:|
| SO | 3 |
| ST | 2 |
| WO | 3 |
| WT | 1 |
| Tổng | 9 |

---

## 3. Cách tạo chiến lược

Khi tạo một chiến lược, CEO cần nhập:

- Nhóm chiến lược: SO, ST, WO hoặc WT
- Tên chiến lược
- Mô tả chiến lược
- Danh sách SWOT item được chọn để tạo chiến lược

Tùy theo nhóm chiến lược, hệ thống render đúng nhóm SWOT tương ứng.

### 3.1. Chiến lược SO

Nguồn chọn:

- S
- O

Quy tắc:

- Được chọn một hoặc nhiều S
- Chỉ được chọn đúng một O
- Mỗi S hoặc O chỉ được dùng một lần trên toàn bộ danh sách chiến lược

### 3.2. Chiến lược ST

Nguồn chọn:

- S
- T

Quy tắc:

- Được chọn một hoặc nhiều S
- Chỉ được chọn đúng một T
- Mỗi S hoặc T chỉ được dùng một lần trên toàn bộ danh sách chiến lược

### 3.3. Chiến lược WO

Nguồn chọn:

- W
- O

Quy tắc:

- Được chọn một hoặc nhiều W
- Chỉ được chọn đúng một O
- Mỗi W hoặc O chỉ được dùng một lần trên toàn bộ danh sách chiến lược

### 3.4. Chiến lược WT

Nguồn chọn:

- W
- T

Quy tắc:

- Được chọn một hoặc nhiều W
- Chỉ được chọn đúng một T
- Mỗi W hoặc T chỉ được dùng một lần trên toàn bộ danh sách chiến lược

---

## 4. Rule quan trọng: SWOT item chỉ được dùng một lần trên toàn bộ chiến lược

Đây là rule nghiệp vụ bắt buộc.

Một SWOT item sau khi đã được dùng để tạo bất kỳ chiến lược nào thì không được render để chọn ở các chiến lược sau nữa.

Ví dụ:

```text
S1 đã được dùng trong chiến lược SO-01
→ S1 không còn xuất hiện trong danh sách chọn cho SO/ST/WO/WT sau đó

O1 đã được dùng trong chiến lược SO-02
→ O1 không còn xuất hiện trong danh sách chọn cho các chiến lược sau đó

T1 đã được dùng trong chiến lược ST-01
→ T1 không còn xuất hiện trong danh sách chọn cho ST hoặc WT sau đó
```

Quy tắc này áp dụng trên toàn bộ danh sách tối đa 12 chiến lược, không chỉ trong phạm vi một chiến lược.

---

## 5. Xóa hoặc chỉnh sửa chiến lược trong Draft

Khi BSC Strategy còn ở trạng thái Draft, CEO có thể chỉnh sửa hoặc xóa chiến lược đã tạo.

Nếu một chiến lược bị xóa, các SWOT item đã dùng trong chiến lược đó nên được mở lại để CEO có thể dùng cho chiến lược khác.

Ví dụ:

```text
SO-01 dùng S1 và O1

CEO xóa SO-01
→ S1 được mở lại
→ O1 được mở lại
```

Nếu CEO chỉnh sửa chiến lược, hệ thống cần cập nhật lại danh sách SWOT item đã bị chiếm dụng.

---

## 6. Validate khi tạo chiến lược

Khi tạo hoặc cập nhật chiến lược, hệ thống cần kiểm tra:

- Tổng số chiến lược sau khi lưu không vượt quá 12
- Tên chiến lược không được rỗng
- Nhóm chiến lược phải thuộc một trong bốn loại: SO, ST, WO, WT
- Chiến lược SO phải có ít nhất 1 S và đúng 1 O
- Chiến lược ST phải có ít nhất 1 S và đúng 1 T
- Chiến lược WO phải có ít nhất 1 W và đúng 1 O
- Chiến lược WT phải có ít nhất 1 W và đúng 1 T
- Mỗi SWOT item chỉ được dùng trong tối đa 1 chiến lược
- SWOT item được chọn phải thuộc BSC Strategy hiện tại
- BSC Strategy vẫn đang ở trạng thái Draft

---

## 7. Output của B2.5

Sau khi hoàn thành B2.5, hệ thống lưu lại danh sách chiến lược ứng viên.

Mỗi chiến lược gồm:

- ID chiến lược
- Nhóm chiến lược: SO/ST/WO/WT
- Tên chiến lược
- Mô tả chiến lược
- Danh sách SWOT item đã dùng
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

Danh sách chiến lược ứng viên này sẽ được chuyển sang B3 - Kết quả chiến lược.

---

# 5. Validate tổng thể của B2

Để hoàn thành B2 và chuyển sang B3, hệ thống cần kiểm tra:

- B1 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- B2.1 có dữ liệu 7S
- B2.2 có dữ liệu 5 Forces
- B2.3 có dữ liệu PESTEL
- B2.4 có dữ liệu SWOT
- B2.5 có ít nhất 1 chiến lược
- Tổng số chiến lược không vượt quá 12
- Không có SWOT item nào được dùng nhiều hơn 1 lần trong các chiến lược
- Người thao tác có quyền CEO hoặc quyền tương đương

Với MVP, có thể chọn một trong hai mức validate:

### Mức validate linh hoạt

- Cho phép một số nhóm dynamic text list trong 7S, 5 Forces hoặc PESTEL để trống
- Chỉ cần có đủ dữ liệu để tạo SWOT và ít nhất 1 chiến lược

### Mức validate nghiêm túc theo framework

- Mỗi nhóm trong 7S phải có ít nhất 1 item
- Mỗi nhóm trong 5 Forces phải có ít nhất 1 item
- Mỗi nhóm trong PESTEL phải có ít nhất 1 item
- SWOT phải có đủ S, W, O, T
- Có ít nhất 1 chiến lược được tạo

Khuyến nghị cho hệ thống BSC SkillForge: dùng mức validate nghiêm túc nếu muốn đảm bảo người dùng đi đúng framework BSC.

---

# 6. Output của B2

Sau khi hoàn thành B2, hệ thống lưu lại:

- Dữ liệu mô hình 7S
- Dữ liệu mô hình 5 áp lực cạnh tranh
- Dữ liệu mô hình PESTEL
- Dữ liệu SWOT
- Mapping giữa SWOT item và dữ liệu nguồn
- Danh sách chiến lược SO/ST/WO/WT
- Mapping giữa chiến lược và SWOT item đã dùng

B2 sau khi hoàn thành sẽ mở khóa **B3 - Kết quả chiến lược**.

---

# 7. Trạng thái của bước

B2 có thể có các trạng thái:

- Not Started: chưa nhập dữ liệu
- In Progress: đang nhập dữ liệu
- Completed: đã nhập đủ dữ liệu theo rule và được xác nhận hoàn thành
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B2.

Khi BSC Strategy đã Active, B2 bị hạn chế chỉnh sửa vì thay đổi dữ liệu phân tích chiến lược có thể ảnh hưởng đến toàn bộ các bước phía sau như B3, B4, B5, B6, B7 và B8.

---

# 8. Ghi chú nghiệp vụ

B2 là bước biến dữ liệu đánh giá và phân tích thành danh sách chiến lược ứng viên.

Hệ thống không tự quyết định chiến lược nào là đúng. CEO hoặc BSC Consultant là người phân tích và chọn dữ liệu.

Vai trò của hệ thống là:

- Cung cấp khung nhập liệu 7S, 5 Forces, PESTEL
- Cho phép tạo SWOT có nguồn gốc rõ ràng
- Kiểm soát rule không trùng lặp giữa S/W và O/T
- Kiểm soát rule mỗi SWOT item chỉ được dùng một lần khi tạo chiến lược
- Lưu lại danh sách chiến lược ứng viên để chuyển sang B3

B2 là bước quan trọng vì nó tạo ra đầu vào trực tiếp cho B3 - Kết quả chiến lược.
