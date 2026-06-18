# B1. Đánh giá hiện trạng doanh nghiệp

## 1. Mục tiêu của bước

Bước **Đánh giá** dùng để CEO nhập các thông tin tổng quan về tình hình hiện tại và định hướng tương lai của doanh nghiệp trước khi bắt đầu xây dựng chiến lược BSC.

Đây là bước nền tảng giúp CEO nhìn lại doanh nghiệp ở các khía cạnh chính như tài chính, thị phần, phân khúc khách hàng, nhóm sản phẩm chủ lực, điểm mạnh nội bộ, đối thủ cạnh tranh và lợi thế cạnh tranh của doanh nghiệp.

Dữ liệu ở bước này chủ yếu là dữ liệu mô tả, dữ liệu phân tích và dữ liệu biểu diễn trực quan. Hệ thống không tự đánh giá đúng sai về mặt chiến lược, mà cung cấp khung nhập liệu có cấu trúc để CEO hoặc BSC Consultant điền thông tin.

---

## 2. Role được phép thao tác

### 2.1. CEO

CEO là người có quyền chính trong bước này.

CEO được phép:

- Xem dữ liệu B1
- Tạo dữ liệu B1
- Chỉnh sửa dữ liệu B1 khi chiến lược BSC còn ở trạng thái Draft
- Hoàn thành B1 để chuyển sang B2

### 2.2. BSC Consultant

Nếu doanh nghiệp có BSC Consultant hỗ trợ, Consultant có thể ngồi cùng CEO để nhập dữ liệu vào hệ thống.

Về mặt hệ thống, Consultant có thể sử dụng tài khoản CEO hoặc một tài khoản được CEO cấp quyền đặc biệt nếu sau này hệ thống hỗ trợ role này.

### 2.3. Các role khác

Các role sau không được xem hoặc thao tác B1:

- Trưởng phòng
- Nhân viên
- Company Admin
- System Admin

Lý do: B1 thuộc nhóm dữ liệu chiến lược và có thể chứa bí mật kinh doanh của công ty.

System Admin chỉ có quyền monitoring hệ thống, không xem nội dung chiến lược chi tiết của doanh nghiệp.

---

## 3. Điều kiện truy cập bước

B1 là bước đầu tiên trong workflow triển khai BSC.

Điều kiện để bắt đầu B1:

- Công ty đã được tạo trong hệ thống
- Dữ liệu nền tối thiểu của công ty đã được setup
- CEO đã được phân quyền đúng
- BSC Strategy đang ở trạng thái Draft

Sau khi CEO hoàn thành B1, hệ thống mới cho phép chuyển sang **B2 - Xây dựng chiến lược**.

---

## 4. Các nhóm dữ liệu trong B1

## 4.1. Tài chính

CEO nhập dữ liệu tài chính để hệ thống biểu diễn bằng biểu đồ cột.

Dữ liệu gồm:

- Năm
- Doanh thu
- Lợi nhuận

Quy tắc:

- Tối đa 3 năm
- Mỗi năm gồm 1 cặp dữ liệu: doanh thu và lợi nhuận
- Tổng cộng tối đa 3 cột doanh thu và 3 cột lợi nhuận
- Năm được tính từ năm hiện tại đến tối đa 2 năm tiếp theo
- Doanh thu và lợi nhuận là số
- Doanh thu không được âm
- Lợi nhuận có thể âm nếu doanh nghiệp bị lỗ

Ví dụ:

| Năm | Doanh thu | Lợi nhuận |
|---|---:|---:|
| 2026 | 10 tỷ | 1 tỷ |
| 2027 | 15 tỷ | 2 tỷ |
| 2028 | 20 tỷ | 3 tỷ |

Output hiển thị:

- Biểu đồ cột nhóm
- Mỗi năm có 2 cột: doanh thu và lợi nhuận

---

## 4.2. Thị phần

CEO nhập dữ liệu thị phần để hệ thống biểu diễn bằng 2 biểu đồ tròn:

- Biểu đồ thị phần hiện tại
- Biểu đồ thị phần tương lai

Mỗi biểu đồ gồm danh sách công ty/doanh nghiệp tham gia thị trường.

Dữ liệu mỗi phần gồm:

- Tên công ty
- Tỉ lệ thị phần
- Đánh dấu công ty của mình hoặc đối thủ

Quy tắc:

- Không giới hạn số lượng đối thủ
- Best practice: 10–20 phần trong một biểu đồ
- Bắt buộc phải có công ty của mình trong mỗi biểu đồ
- Tổng tỉ lệ thị phần trong mỗi biểu đồ phải bằng 100%
- Nếu tổng khác 100%, hệ thống cảnh báo và không cho hoàn thành phần thị phần

Output hiển thị:

- Pie chart thị phần hiện tại
- Pie chart thị phần tương lai

---

## 4.3. Phân khúc / Nhóm sản phẩm chủ lực

Phần này được xử lý bởi FE dưới dạng các đề mục nhập liệu rõ ràng.

Bao gồm 4 nhóm dynamic text list:

- Phân khúc hiện tại
- Phân khúc tương lai
- Nhóm sản phẩm chủ lực hiện tại
- Nhóm sản phẩm chủ lực tương lai

Mỗi nhóm cho phép CEO nhập nhiều dòng nội dung.

Ví dụ phân khúc hiện tại:

- SME ngành giáo dục
- Trung tâm đào tạo nội bộ
- Công ty dịch vụ quy mô nhỏ

Ví dụ phân khúc tương lai:

- Doanh nghiệp sản xuất vừa
- Chuỗi bán lẻ
- Công ty có nhiều chi nhánh

Quy tắc:

- Mỗi item là một dòng text
- Cho phép thêm, sửa, xóa item khi BSC Strategy còn Draft
- Không bắt buộc giới hạn số lượng item
- Không cho lưu item rỗng
- Nên trim khoảng trắng đầu/cuối khi lưu

---

## 4.4. Điểm mạnh công ty

CEO nhập danh sách điểm mạnh hiện tại của công ty dưới dạng dynamic text list.

Ví dụ:

- Đội ngũ kỹ thuật triển khai nhanh
- Có quan hệ tốt với khách hàng SME
- Quy trình chăm sóc khách hàng linh hoạt

Quy tắc:

- Cho phép nhập nhiều item
- Không cho lưu item rỗng
- Không bắt buộc giới hạn số lượng

---

## 4.5. Yếu tố thành công trong ngành

CEO nhập các yếu tố được xem là quan trọng để thành công trong ngành mà doanh nghiệp đang hoạt động.

Ví dụ:

- Tốc độ triển khai nhanh
- Khả năng tùy chỉnh theo nhu cầu khách hàng
- Chất lượng chăm sóc sau bán hàng
- Giá cả cạnh tranh

Quy tắc:

- Dynamic text list
- Không cho lưu item rỗng
- Không bắt buộc giới hạn số lượng

---

## 4.6. Điểm mạnh đối thủ

CEO nhập danh sách điểm mạnh của các đối thủ cạnh tranh.

Ví dụ:

- Đối thủ có thương hiệu mạnh
- Đối thủ có đội sales lớn
- Đối thủ có giá thấp hơn
- Đối thủ có hệ sinh thái sản phẩm hoàn chỉnh

Quy tắc:

- Dynamic text list
- Không cho lưu item rỗng
- Không bắt buộc giới hạn số lượng

---

## 4.7. Điểm yếu đối thủ

CEO nhập danh sách điểm yếu của các đối thủ cạnh tranh.

Ví dụ:

- Quy trình hỗ trợ khách hàng chậm
- Sản phẩm khó tùy chỉnh
- Chi phí triển khai cao
- Không tập trung vào nhóm SME

Quy tắc:

- Dynamic text list
- Không cho lưu item rỗng
- Không bắt buộc giới hạn số lượng

---

## 4.8. Lợi thế cạnh tranh của doanh nghiệp

CEO nhập danh sách lợi thế cạnh tranh mà doanh nghiệp có thể tận dụng.

Ví dụ:

- Triển khai nhanh hơn đối thủ
- Hiểu sâu nhu cầu khách hàng SME
- Chi phí linh hoạt
- Có khả năng cá nhân hóa giải pháp theo từng doanh nghiệp

Quy tắc:

- Dynamic text list
- Không cho lưu item rỗng
- Không bắt buộc giới hạn số lượng

---

## 5. Validate tổng thể của B1

Để hoàn thành B1 và chuyển sang B2, hệ thống cần kiểm tra:

- Phần tài chính có ít nhất 1 năm dữ liệu
- Phần tài chính không vượt quá 3 năm
- Mỗi năm tài chính có đủ doanh thu và lợi nhuận
- Phần thị phần hiện tại có công ty của mình
- Phần thị phần tương lai có công ty của mình
- Tổng thị phần hiện tại bằng 100%
- Tổng thị phần tương lai bằng 100%
- Các dynamic text list không chứa item rỗng
- BSC Strategy vẫn đang ở trạng thái Draft

Các nhóm dynamic text list có thể chưa bắt buộc phải nhập hết ngay từ đầu, tùy mức độ strict của MVP. Tuy nhiên, nếu đi theo framework nghiêm túc thì nên bắt buộc mỗi nhóm phải có ít nhất 1 item.

---

## 6. Output của B1

Sau khi hoàn thành B1, hệ thống lưu lại:

- Dữ liệu tài chính
- Dữ liệu thị phần hiện tại
- Dữ liệu thị phần tương lai
- Danh sách phân khúc hiện tại
- Danh sách phân khúc tương lai
- Danh sách nhóm sản phẩm chủ lực hiện tại
- Danh sách nhóm sản phẩm chủ lực tương lai
- Danh sách điểm mạnh công ty
- Danh sách yếu tố thành công trong ngành
- Danh sách điểm mạnh đối thủ
- Danh sách điểm yếu đối thủ
- Danh sách lợi thế cạnh tranh của doanh nghiệp

B1 sau khi hoàn thành sẽ mở khóa **B2 - Xây dựng chiến lược**.

---

## 7. Trạng thái của bước

B1 có thể có các trạng thái:

- Not Started: chưa nhập dữ liệu
- In Progress: đang nhập dữ liệu
- Completed: đã nhập đủ dữ liệu theo rule và được xác nhận hoàn thành
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B1.

Khi BSC Strategy đã Active, B1 bị hạn chế chỉnh sửa vì thay đổi dữ liệu đánh giá ban đầu có thể ảnh hưởng đến toàn bộ workflow chiến lược phía sau.

---

## 8. Ghi chú nghiệp vụ

B1 chỉ là bước đánh giá và lưu trữ dữ liệu đầu vào. Hệ thống không tự phân tích, không tự kết luận chiến lược và không tự sinh KPI từ B1.

Vai trò của hệ thống ở bước này là cung cấp một khung nhập liệu có cấu trúc, giúp CEO hoặc BSC Consultant ghi nhận dữ liệu đánh giá doanh nghiệp một cách rõ ràng, có thể xem lại và sử dụng làm nền cho các bước xây dựng chiến lược tiếp theo.
