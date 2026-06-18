# B5. Mô hình xương cá

## 1. Mục tiêu của bước

Bước **Mô hình xương cá** là bước chuyển bản đồ chiến lược tổng của công ty thành các KPI nhỏ theo từng phòng ban.

Ở B4, CEO đã tạo ra **bản đồ chiến lược tổng** gồm danh sách các mục tiêu chiến lược cuối cùng của công ty. Sang B5, các mục tiêu chiến lược này sẽ trở thành các **đầu cá lớn**.

Từng trưởng phòng sẽ đăng nhập vào hệ thống, xem danh sách mục tiêu chiến lược tổng và tự chọn các mục tiêu mà phòng ban mình có tham gia đóng góp. Sau đó trưởng phòng tạo các KPI nhỏ của phòng ban mình cho từng mục tiêu đã chọn.

B5 là bước bắt đầu mở chiến lược từ cấp CEO xuống cấp phòng ban.

---

## 2. Vai trò của B5 trong toàn workflow BSC

Luồng dữ liệu:

```text
B4. Bản đồ chiến lược tổng
→ B5. Mô hình xương cá
→ B6. Phân bổ tỉ trọng
→ B7. Đo lường & Chỉ tiêu
→ B8. Action Plan
```

Ở B5:

- CEO không tạo KPI
- Trưởng phòng tạo KPI nhỏ cho phòng ban
- Nhân viên có thể xem mô hình xương cá của phòng ban mình
- Các KPI nhỏ được tạo ở B5 sẽ được CEO phân bổ tỉ trọng ở B6
- Các KPI nhỏ được tạo ở B5 sẽ được CEO nhập chỉ tiêu đo lường ở B7
- Các KPI nhỏ được tạo ở B5 sẽ trở thành gốc để tạo action plan và task ở B8

B5 là cầu nối từ:

```text
Mục tiêu chiến lược cấp công ty
→ KPI nhỏ của phòng ban
→ Công việc thực thi của nhân viên
```

---

## 3. Role được phép thao tác

## 3.1. CEO

CEO được phép:

- Xem mô hình xương cá tổng của công ty
- Xem phòng ban nào tham gia mục tiêu chiến lược nào
- Xem KPI nhỏ do từng phòng ban tạo
- Xem trạng thái hoàn thành B5
- Xem cảnh báo nếu có mục tiêu chưa có phòng ban tham gia hoặc chưa có KPI

CEO không được phép:

- Tạo KPI nhỏ thay trưởng phòng
- Sửa KPI nhỏ của phòng ban
- Xóa KPI nhỏ của phòng ban
- Chọn phòng ban tham gia đầu cá thay trưởng phòng

Lý do: Theo nghiệp vụ mentor truyền đạt, từ B5 CEO chỉ xem, còn trưởng phòng là người tự chọn đầu cá và tạo KPI nhỏ cho phòng ban.

---

## 3.2. Trưởng phòng

Trưởng phòng là người thao tác chính trong B5.

Trưởng phòng được phép:

- Xem danh sách mục tiêu chiến lược tổng từ B4
- Tự chọn mục tiêu chiến lược mà phòng ban mình tham gia
- Tạo KPI nhỏ cho phòng ban mình trong từng mục tiêu đã chọn
- Chỉnh sửa KPI nhỏ của phòng ban mình khi BSC Strategy còn Draft
- Xóa KPI nhỏ của phòng ban mình khi BSC Strategy còn Draft
- Xem mô hình xương cá riêng của phòng ban mình
- Submit phần KPI của phòng ban mình nếu hệ thống có workflow nộp dữ liệu

Trưởng phòng không được phép:

- Tạo KPI cho phòng ban khác
- Sửa KPI của phòng ban khác
- Xóa KPI của phòng ban khác
- Sửa mục tiêu chiến lược tổng từ B4
- Sửa đường liên kết nhân quả từ B4
- Phân bổ tỉ trọng ở B6
- Nhập chỉ tiêu đo lường ở B7 nếu theo requirement hiện tại CEO là người nhập B7

---

## 3.3. Nhân viên

Nhân viên được phép:

- Xem mô hình xương cá riêng của phòng ban mình
- Xem các KPI nhỏ mà phòng ban mình đang tham gia
- Xem các mục tiêu chiến lược tổng liên quan đến phòng ban mình ở mức cần thiết để hiểu KPI

Nhân viên không được phép:

- Xem dữ liệu chiến lược bí mật từ B1 đến B4
- Tạo KPI nhỏ
- Sửa KPI nhỏ
- Xóa KPI nhỏ
- Xem KPI của phòng ban khác nếu không được cấp quyền

---

## 3.4. Company Admin

Company Admin chủ yếu phụ trách setup dữ liệu nền như công ty, phòng ban, chức vụ, nhân viên và phân quyền.

Company Admin không nên được xem hoặc thao tác nội dung B5 nếu không được CEO cấp quyền đặc biệt.

Lý do: B5 bắt đầu chứa dữ liệu KPI chiến lược của từng phòng ban.

---

## 3.5. System Admin

System Admin thuộc bên cung cấp nền tảng.

System Admin chỉ có quyền monitoring hệ thống, không xem nội dung KPI chiến lược chi tiết của doanh nghiệp.

---

## 4. Điều kiện truy cập bước

B5 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- Công ty đã có phòng ban
- Công ty đã có trưởng phòng được gán cho phòng ban
- Mỗi phòng ban có màu đại diện nếu hệ thống dùng màu để render
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B3 - Kết quả chiến lược đã hoàn thành
- B4 - Bản đồ chiến lược đã hoàn thành
- B4 đã có bản đồ chiến lược tổng
- B4 đã có danh sách mục tiêu chiến lược tổng

Nếu B4 chưa hoàn thành, hệ thống không mở B5.

---

## 5. Khái niệm chính trong B5

## 5.1. Đầu cá lớn

Đầu cá lớn là các mục tiêu chiến lược tổng được tạo ra ở B4.

Ví dụ B4 tạo ra 21 mục tiêu chiến lược tổng, thì B5 sẽ có 21 đầu cá lớn.

```text
B4:
21 mục tiêu chiến lược tổng

B5:
21 đầu cá lớn
```

Mỗi đầu cá lớn thuộc một trong 4 góc độ BSC:

- Tài chính
- Khách hàng
- Quy trình nội bộ
- Học hỏi & phát triển

---

## 5.2. Phòng ban tham gia đầu cá

Trưởng phòng tự chọn các đầu cá mà phòng ban mình có tham gia đóng góp.

Ví dụ công ty có 21 đầu cá lớn:

```text
Phòng HR tham gia 8/21 đầu cá
Phòng Sales tham gia 12/21 đầu cá
Phòng Marketing tham gia 9/21 đầu cá
Phòng Product tham gia 10/21 đầu cá
```

Việc tham gia đầu cá thể hiện rằng phòng ban đó có trách nhiệm đóng góp KPI cho mục tiêu chiến lược đó.

---

## 5.3. KPI nhỏ của phòng ban

KPI nhỏ là KPI do trưởng phòng tạo ra cho phòng ban mình trong từng đầu cá đã chọn.

Ví dụ đầu cá lớn:

```text
Tăng mức độ hài lòng khách hàng
```

Các phòng ban có thể tạo KPI nhỏ:

| Phòng ban | KPI nhỏ |
|---|---|
| CSKH | Giảm thời gian phản hồi ticket xuống dưới 2 giờ |
| Product | Giảm số lỗi nghiêm trọng ảnh hưởng đến khách hàng |
| HR | Đào tạo kỹ năng giao tiếp khách hàng cho nhân viên |
| Sales | Tăng tỷ lệ follow-up khách hàng sau demo |

KPI nhỏ ở B5 chưa cần có tỉ trọng, chỉ tiêu đo lường hoặc target chi tiết. Các phần đó sẽ được xử lý ở B6 và B7.

---

## 5.4. Mô hình xương cá tổng của công ty

Mô hình xương cá tổng là view dành cho CEO để nhìn toàn bộ mục tiêu chiến lược và KPI nhỏ từ các phòng ban.

Cấu trúc:

```text
Mục tiêu chiến lược tổng / đầu cá lớn
→ KPI nhỏ của phòng ban A
→ KPI nhỏ của phòng ban B
→ KPI nhỏ của phòng ban C
```

Mỗi KPI nhỏ được hiển thị theo màu đại diện của phòng ban.

Ví dụ:

- HR màu tím
- Sales màu xanh lá
- Marketing màu cam
- Product màu xanh dương

Màu phòng ban giúp CEO nhìn nhanh mục tiêu nào đang có phòng ban nào tham gia.

---

## 5.5. Mô hình xương cá riêng của phòng ban

Mỗi phòng ban có một mô hình xương cá riêng.

Mô hình xương cá riêng của phòng ban được gộp từ toàn bộ KPI nhỏ mà phòng ban đó đã tạo trong các đầu cá đã tham gia.

Ví dụ:

```text
Phòng HR tham gia 8/21 đầu cá
→ HR tạo KPI nhỏ trong 8 đầu cá đó
→ Hệ thống gom toàn bộ KPI nhỏ của HR
→ Render thành mô hình xương cá riêng của phòng HR
```

Mô hình này giúp trưởng phòng và nhân viên trong phòng thấy được:

- Phòng mình đang tham gia những mục tiêu chiến lược nào
- Phòng mình có những KPI nhỏ nào
- KPI nào thuộc mục tiêu chiến lược nào
- Sau này KPI nào có tỉ trọng, chỉ tiêu và action plan ra sao

---

## 6. User flow của B5

## 6.1. CEO xem B5

Luồng CEO:

```text
CEO hoàn thành B4
→ Hệ thống mở B5
→ CEO vào xem mô hình xương cá tổng
→ CEO thấy danh sách đầu cá lớn từ B4
→ CEO thấy phòng ban nào đã tham gia đầu cá nào
→ CEO thấy KPI nhỏ do các trưởng phòng tạo
→ CEO chỉ xem, không chỉnh sửa
```

CEO có thể thấy các cảnh báo như:

- Mục tiêu chiến lược chưa có phòng ban nào tham gia
- Mục tiêu chiến lược đã có phòng ban tham gia nhưng chưa có KPI nhỏ
- Phòng ban chưa tham gia bất kỳ mục tiêu nào
- KPI nhỏ thiếu tên hoặc mô tả
- Trưởng phòng chưa submit phần KPI của phòng ban

---

## 6.2. Trưởng phòng chọn đầu cá

Luồng trưởng phòng:

```text
Trưởng phòng đăng nhập
→ Vào B5 - Mô hình xương cá
→ Hệ thống hiển thị danh sách mục tiêu chiến lược tổng từ B4
→ Trưởng phòng chọn các mục tiêu mà phòng ban mình tham gia
→ Hệ thống tạo quan hệ phòng ban tham gia mục tiêu
→ Trưởng phòng bắt đầu tạo KPI nhỏ cho từng mục tiêu đã chọn
```

Quy tắc:

- Trưởng phòng chỉ chọn cho phòng ban của mình
- Một phòng ban có thể tham gia nhiều mục tiêu chiến lược
- Một mục tiêu chiến lược có thể có nhiều phòng ban tham gia
- Trưởng phòng có thể bỏ chọn mục tiêu nếu chưa có KPI hoặc khi BSC Strategy còn Draft
- Nếu đã có KPI trong mục tiêu đó, khi bỏ chọn hệ thống cần cảnh báo vì KPI liên quan sẽ bị ảnh hưởng

---

## 6.3. Trưởng phòng tạo KPI nhỏ

Sau khi chọn đầu cá, trưởng phòng tạo KPI nhỏ cho từng đầu cá.

Dữ liệu cần nhập cho KPI nhỏ:

- Tên KPI
- Mô tả KPI
- Mục tiêu chiến lược liên kết
- Phòng ban phụ trách
- Người tạo
- Thứ tự hiển thị
- Ghi chú nếu có

Ví dụ:

```text
Mục tiêu chiến lược:
Tăng mức độ hài lòng khách hàng

Phòng ban:
CSKH

KPI nhỏ:
Giảm thời gian phản hồi ticket xuống dưới 2 giờ

Mô tả:
Đảm bảo đội CSKH phản hồi các ticket của khách hàng trong thời gian tối đa 2 giờ làm việc.
```

Ở B5, KPI nhỏ chỉ cần mô tả được ý nghĩa quản trị. Các thông tin như tỉ trọng, target, đơn vị đo, kỳ báo cáo sẽ được bổ sung ở B6 và B7.

---

## 6.4. Trưởng phòng xem mô hình xương cá phòng ban

Sau khi tạo KPI nhỏ, trưởng phòng có thể xem mô hình xương cá riêng của phòng ban mình.

Ví dụ phòng HR:

```text
Phòng HR
├── Mục tiêu chiến lược 1
│   ├── KPI HR 1
│   └── KPI HR 2
├── Mục tiêu chiến lược 2
│   └── KPI HR 3
├── Mục tiêu chiến lược 3
│   ├── KPI HR 4
│   └── KPI HR 5
```

View này giúp trưởng phòng nhìn tổng thể trách nhiệm chiến lược của phòng ban.

---

## 6.5. Nhân viên xem mô hình xương cá phòng ban

Nhân viên trong phòng ban có thể xem mô hình xương cá của phòng mình để hiểu:

- Phòng ban mình đang đóng góp vào mục tiêu chiến lược nào
- Các KPI quan trọng của phòng ban là gì
- Vì sao sau này mình được giao task liên quan đến KPI đó

Nhân viên không chỉnh sửa dữ liệu B5.

---

## 7. Rule nghiệp vụ chính

## 7.1. Đầu cá lấy từ B4

Tất cả đầu cá ở B5 phải lấy từ bản đồ chiến lược tổng cuối cùng của B4.

Không cho tạo đầu cá tự do ở B5.

```text
B4 Final Strategic Objective
→ B5 Fishbone Head
```

Nếu một mục tiêu chiến lược không nằm trong bản đồ chiến lược tổng của B4, mục tiêu đó không được xuất hiện ở B5.

---

## 7.2. Trưởng phòng tự chọn đầu cá

Theo nghiệp vụ mentor truyền đạt:

- CEO không assign phòng ban vào đầu cá
- Trưởng phòng tự chọn mục tiêu mà phòng ban mình tham gia
- CEO chỉ xem kết quả

Hệ thống cần hỗ trợ trưởng phòng tự chọn, nhưng vẫn nên hiển thị cảnh báo để tránh bỏ sót mục tiêu quan trọng.

---

## 7.3. Một mục tiêu có thể có nhiều phòng ban tham gia

Một đầu cá lớn có thể có nhiều phòng ban cùng tham gia.

Ví dụ:

```text
Mục tiêu:
Tăng mức độ hài lòng khách hàng

Phòng ban tham gia:
- CSKH
- Sales
- Product
- HR
```

Mỗi phòng ban sẽ tạo KPI nhỏ riêng của phòng ban đó.

---

## 7.4. Một phòng ban có thể tham gia nhiều mục tiêu

Một phòng ban có thể tham gia nhiều đầu cá.

Ví dụ:

```text
Phòng HR tham gia:
- Nâng cao năng lực nhân viên
- Tăng mức độ hài lòng khách hàng
- Cải thiện quy trình nội bộ
- Giảm tỷ lệ nghỉ việc
```

Hệ thống sẽ gom toàn bộ KPI nhỏ của phòng HR thành mô hình xương cá riêng của phòng HR.

---

## 7.5. KPI nhỏ bắt buộc thuộc một mục tiêu chiến lược

Không cho tạo KPI nhỏ tự do ở B5.

Mỗi KPI nhỏ bắt buộc phải gắn với:

- Một mục tiêu chiến lược tổng từ B4
- Một phòng ban
- Một trưởng phòng tạo

Quan hệ dữ liệu:

```text
Final Strategic Objective
→ Department Participation
→ Department KPI
```

---

## 7.6. KPI nhỏ chưa có tỉ trọng và chỉ tiêu ở B5

B5 chỉ tạo tên và ý nghĩa KPI nhỏ.

Không xử lý:

- Tỉ trọng KPI
- Target KPI
- Đơn vị đo
- Công thức đo
- Kỳ báo cáo
- Ngưỡng đánh giá

Các phần này thuộc B6 và B7.

---

## 7.7. Màu phòng ban dùng để render

Trong giai đoạn setup dữ liệu nền, mỗi phòng ban nên có một màu đại diện.

Ở B5:

- KPI nhỏ của phòng ban nào thì dùng màu của phòng ban đó
- Màu giúp CEO nhìn mô hình xương cá tổng dễ hơn
- Màu giúp phân biệt phòng ban trong cùng một mục tiêu chiến lược

Ví dụ:

| Phòng ban | Màu |
|---|---|
| HR | Tím |
| Sales | Xanh lá |
| Marketing | Cam |
| Product | Xanh dương |

---

## 8. Dữ liệu cần lưu

## 8.1. Department Participation

Dùng để lưu quan hệ phòng ban tham gia mục tiêu chiến lược nào.

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Final Strategic Objective ID
- Department ID
- Department Head ID
- Status
- Created by
- Created at
- Updated at

Ví dụ:

```text
Department:
HR

Final Strategic Objective:
Nâng cao năng lực đội ngũ

Status:
ACTIVE
```

---

## 8.2. Department KPI

Dùng để lưu KPI nhỏ do trưởng phòng tạo.

Dữ liệu gợi ý:

- ID
- BSC Strategy ID
- Final Strategic Objective ID
- Department ID
- Department Participation ID
- KPI name
- KPI description
- Owner role / created by department head
- Display order
- Status
- Created by
- Created at
- Updated at

Ví dụ:

```text
KPI name:
Đào tạo kỹ năng giao tiếp khách hàng cho nhân viên

Department:
HR

Final Strategic Objective:
Tăng mức độ hài lòng khách hàng
```

---

## 8.3. Department Fishbone View

Có thể không cần lưu thành bảng riêng nếu FE có thể render từ dữ liệu Department KPI.

Logic render:

```text
Lấy tất cả KPI nhỏ của department_id
→ Group theo Final Strategic Objective
→ Render thành mô hình xương cá riêng của phòng ban
```

---

## 8.4. Company Fishbone View

Có thể không cần lưu thành bảng riêng nếu FE có thể render từ dữ liệu Department KPI.

Logic render:

```text
Lấy tất cả Final Strategic Objective từ B4
→ Với mỗi objective, lấy tất cả Department KPI liên quan
→ Group KPI theo phòng ban
→ Render mô hình xương cá tổng của công ty
```

---

## 9. Validate của B5

## 9.1. Validate khi trưởng phòng chọn đầu cá

Hệ thống cần kiểm tra:

- B4 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là trưởng phòng
- Trưởng phòng thuộc phòng ban hợp lệ
- Mục tiêu chiến lược được chọn thuộc bản đồ chiến lược tổng của B4
- Quan hệ phòng ban - mục tiêu không bị trùng
- Không chọn mục tiêu đã bị xóa hoặc không còn hợp lệ

---

## 9.2. Validate khi trưởng phòng tạo KPI nhỏ

Hệ thống cần kiểm tra:

- Người thao tác là trưởng phòng của phòng ban đó
- KPI phải gắn với một mục tiêu chiến lược tổng hợp lệ
- KPI phải thuộc phòng ban của trưởng phòng
- Phòng ban đã tham gia mục tiêu đó trước khi tạo KPI
- Tên KPI không được rỗng
- Tên KPI nên được trim khoảng trắng đầu/cuối
- Mô tả KPI có thể không bắt buộc nhưng nên có
- KPI không được tạo trùng hoàn toàn tên trong cùng một phòng ban và cùng một mục tiêu
- BSC Strategy đang ở trạng thái Draft

---

## 9.3. Validate để hoàn thành B5

Để hoàn thành B5 và chuyển sang B6, hệ thống nên kiểm tra:

- B4 đã hoàn thành
- Có danh sách mục tiêu chiến lược tổng từ B4
- Mỗi mục tiêu chiến lược tổng có ít nhất 1 phòng ban tham gia
- Mỗi mục tiêu chiến lược tổng có ít nhất 1 KPI nhỏ
- Mỗi KPI nhỏ thuộc đúng một phòng ban
- Mỗi KPI nhỏ thuộc đúng một mục tiêu chiến lược tổng
- Không có KPI nhỏ rỗng tên
- Không có phòng ban tham gia mục tiêu nhưng chưa tạo KPI
- BSC Strategy đang ở trạng thái Draft

Lý do nên bắt buộc mỗi mục tiêu có ít nhất 1 KPI nhỏ:

```text
B6 cần phân bổ tỉ trọng từ mục tiêu chiến lược xuống KPI nhỏ.
Nếu một mục tiêu chiến lược không có KPI nhỏ, hệ thống không có dữ liệu để phân bổ tỉ trọng và đo lường ở B7.
```

---

## 10. Output của B5

Sau khi hoàn thành B5, hệ thống lưu lại:

- Danh sách mục tiêu chiến lược tổng từ B4 được dùng làm đầu cá
- Danh sách phòng ban tham gia từng mục tiêu
- Danh sách KPI nhỏ của từng phòng ban trong từng mục tiêu
- Màu phòng ban dùng để render
- Trạng thái hoàn thành B5

Dữ liệu quan trọng nhất được chuyển sang B6 là:

```text
Danh sách KPI nhỏ theo từng mục tiêu chiến lược tổng
```

Các KPI nhỏ này sẽ được CEO phân bổ tỉ trọng ở B6.

---

## 11. Liên kết với B6 - Phân bổ tỉ trọng

Sau khi B5 hoàn thành, B6 sẽ sử dụng dữ liệu từ B5 để CEO phân bổ tỉ trọng.

Luồng liên kết:

```text
Góc độ BSC
→ Mục tiêu chiến lược tổng
→ KPI nhỏ của phòng ban
```

Ở B6:

- CEO phân bổ 100% cho 4 góc độ BSC
- Mỗi góc độ chia tiếp xuống các mục tiêu chiến lược thuộc góc độ đó
- Mỗi mục tiêu chiến lược chia tiếp xuống các KPI nhỏ bên trong
- Tỉ trọng là tỉ trọng tuyệt đối trên tổng 100%, không chia lại thành 100% ở từng tầng con

Ví dụ:

```text
Tài chính = 40%

Trong Tài chính:
- Tăng doanh thu = 20%
- Tối ưu chi phí = 12%
- Tăng lợi nhuận = 8%

Trong mục tiêu Tăng doanh thu:
- KPI Sales = 10%
- KPI Marketing = 7%
- KPI Product = 3%
```

Tổng KPI nhỏ trong mục tiêu **Tăng doanh thu** phải bằng 20%.

---

## 12. Liên kết với B7 - Đo lường & Chỉ tiêu

B7 sẽ sử dụng danh sách KPI nhỏ từ B5 và tỉ trọng từ B6 để CEO nhập:

- Đơn vị đo
- Target
- Giá trị hiện tại nếu có
- Công thức đo
- Kỳ báo cáo
- Chiều tốt của KPI
- Ngưỡng đánh giá
- Người/phòng ban chịu trách nhiệm báo cáo nếu cần

B5 chỉ tạo KPI nhỏ, chưa đo lường KPI.

---

## 13. Liên kết với B8 - Action Plan

B8 sẽ sử dụng KPI nhỏ từ B5 làm gốc để tạo action plan và task.

Rule quan trọng:

```text
Task ở B8 không được tạo tự do.
Task phải liên kết với KPI nhỏ đã có từ B5.
```

Luồng:

```text
Mục tiêu chiến lược tổng
→ KPI nhỏ của phòng ban
→ Action Plan
→ Task
→ Nhân viên phụ trách
→ Báo cáo tiến độ
→ Dashboard
```

Nhờ vậy CEO có thể truy ngược từ dashboard:

```text
Task của nhân viên
→ KPI nhỏ của phòng ban
→ Mục tiêu chiến lược tổng
→ Góc độ BSC
→ Tình trạng toàn công ty
```

---

## 14. Ảnh hưởng khi chỉnh sửa B4 sau khi đã có B5

Khi BSC Strategy còn Draft, CEO có thể quay lại B4 để chỉnh sửa bản đồ chiến lược tổng.

Tuy nhiên, nếu B4 thay đổi sau khi B5 đã có dữ liệu, hệ thống cần xử lý cẩn thận.

## 14.1. Mục tiêu chiến lược tổng bị xóa khỏi B4

Nếu một mục tiêu chiến lược tổng bị xóa hoặc không còn nằm trong bản đồ tổng, các dữ liệu B5 liên quan sẽ bị ảnh hưởng:

- Quan hệ phòng ban tham gia mục tiêu đó
- KPI nhỏ của các phòng ban trong mục tiêu đó
- Mô hình xương cá tổng
- Mô hình xương cá riêng của phòng ban

Hệ thống nên:

- Cảnh báo CEO trước khi thay đổi B4
- Đánh dấu B5 là Invalidated
- Không tự xóa cứng KPI ngay lập tức
- Yêu cầu trưởng phòng kiểm tra lại KPI liên quan

---

## 14.2. Mục tiêu chiến lược tổng bị đổi tên

Nếu mục tiêu chỉ đổi tên/mô tả nhưng ID không đổi:

- B5 có thể giữ nguyên KPI nhỏ
- Hệ thống cập nhật tên mục tiêu mới khi render
- Không cần invalid toàn bộ B5

---

## 14.3. Mục tiêu chiến lược tổng bị merge lại

Nếu B4 thay đổi do merge mục tiêu:

- Các KPI nhỏ cũ có thể cần map lại sang mục tiêu mới
- Hệ thống nên cảnh báo và yêu cầu xác nhận
- Với MVP, có thể đánh dấu B5 là Invalidated và yêu cầu trưởng phòng kiểm tra lại

---

## 15. Trạng thái của bước

B5 có thể có các trạng thái:

- Not Started: chưa có phòng ban nào tham gia đầu cá
- In Progress: đang có trưởng phòng chọn đầu cá và tạo KPI
- Waiting For Departments: còn phòng ban hoặc mục tiêu chưa có dữ liệu KPI
- Ready For Review: dữ liệu B5 đã đủ điều kiện để CEO xem
- Completed: đã có KPI nhỏ hợp lệ để chuyển sang B6
- Invalidated: dữ liệu B4 thay đổi làm B5 không còn hợp lệ
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Gợi ý xử lý trạng thái:

- Trưởng phòng tạo KPI và submit phần phòng ban mình
- Hệ thống tự kiểm tra toàn bộ B5
- Khi tất cả mục tiêu chiến lược tổng đều có KPI nhỏ hợp lệ, B5 có thể chuyển sang Completed hoặc cho phép CEO đi tiếp B6
- CEO không chỉnh sửa nội dung B5

---

## 16. Gợi ý UI/UX cho B5

## 16.1. View CEO - Mô hình xương cá tổng

Gợi ý hiển thị:

- Danh sách đầu cá lớn lấy từ B4
- Mỗi đầu cá hiển thị các KPI nhỏ bên dưới
- KPI được tô màu theo phòng ban
- Có filter theo phòng ban
- Có filter theo góc độ BSC
- Có trạng thái mục tiêu đã có/chưa có KPI
- Có cảnh báo mục tiêu chưa có phòng ban tham gia

Ví dụ layout logic:

```text
Mục tiêu chiến lược: Tăng mức độ hài lòng khách hàng

KPI nhỏ:
[CSKH] Giảm thời gian phản hồi ticket
[Product] Giảm lỗi nghiêm trọng
[HR] Đào tạo kỹ năng giao tiếp khách hàng
[Sales] Tăng tỷ lệ follow-up sau demo
```

---

## 16.2. View trưởng phòng - Chọn đầu cá

Gợi ý hiển thị:

- Danh sách mục tiêu chiến lược tổng
- Mỗi mục tiêu có góc độ BSC
- Có mô tả mục tiêu
- Có checkbox hoặc nút "Phòng ban tôi tham gia"
- Sau khi chọn, hiển thị form tạo KPI nhỏ
- Mục tiêu đã chọn được gom vào danh sách riêng của phòng ban

---

## 16.3. View trưởng phòng - Mô hình xương cá phòng ban

Gợi ý hiển thị:

- Tên phòng ban
- Màu phòng ban
- Danh sách mục tiêu mà phòng ban tham gia
- KPI nhỏ của phòng ban trong từng mục tiêu
- Cho phép thêm/sửa/xóa KPI nếu còn Draft
- Có nút submit phần KPI của phòng ban nếu cần

---

## 16.4. View nhân viên - Mô hình xương cá phòng ban

Gợi ý hiển thị:

- Danh sách KPI của phòng ban
- Nhóm theo mục tiêu chiến lược
- Chỉ xem, không chỉnh sửa
- Sau này có thể liên kết sang task ở B8

---

## 17. Ghi chú nghiệp vụ

B5 là bước đưa chiến lược từ cấp công ty xuống cấp phòng ban.

Kết quả quan trọng nhất của B5 là danh sách KPI nhỏ của từng phòng ban gắn với từng mục tiêu chiến lược tổng.

Hệ thống không cho tạo KPI tự do. Mỗi KPI nhỏ phải bám vào một mục tiêu chiến lược tổng từ B4.

CEO chỉ xem B5, không thao tác nội dung KPI. Trưởng phòng là người tự chọn đầu cá và tạo KPI nhỏ cho phòng ban mình.

Mỗi phòng ban có một mô hình xương cá riêng được gộp từ toàn bộ KPI nhỏ của phòng ban đó.

Từ B6 trở đi, các KPI nhỏ này sẽ được phân bổ tỉ trọng, đo lường, giao action plan và theo dõi tiến độ.
