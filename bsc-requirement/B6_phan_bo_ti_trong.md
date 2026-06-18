# B6. Phân bổ tỉ trọng

## 1. Mục tiêu của bước

Bước **Phân bổ tỉ trọng** là bước CEO xác định mức độ quan trọng của từng phần trong hệ thống BSC.

Sau khi B5 hoàn thành, hệ thống đã có:

- 4 góc độ BSC
- Danh sách mục tiêu chiến lược tổng từ B4
- Danh sách KPI nhỏ của các phòng ban từ B5

Sang B6, CEO sẽ phân bổ tỉ trọng từ tổng 100% xuống từng tầng:

```text
100% toàn bộ chiến lược BSC
→ 4 góc độ BSC
→ Mục tiêu chiến lược trong từng góc độ
→ KPI nhỏ trong từng mục tiêu chiến lược
```

Điểm quan trọng: **tỉ trọng trong B6 là tỉ trọng tuyệt đối trên tổng 100%, không phải tỉ trọng tương đối trong từng nhóm con**.

Ví dụ nếu góc độ Tài chính là 40%, thì tổng các mục tiêu chiến lược thuộc Tài chính phải cộng lại đúng 40%, không phải 100%.

---

## 2. Vai trò của B6 trong toàn workflow BSC

Luồng dữ liệu:

```text
B4. Bản đồ chiến lược tổng
→ B5. Mô hình xương cá
→ B6. Phân bổ tỉ trọng
→ B7. Đo lường & Chỉ tiêu
→ B8. Action Plan
```

B6 giúp hệ thống biết:

- Góc độ BSC nào quan trọng hơn
- Mục tiêu chiến lược nào có trọng số cao hơn
- KPI nhỏ nào ảnh hưởng nhiều hơn đến kết quả chung của công ty
- Sau này dashboard sẽ tính điểm tổng thể theo trọng số nào

Nếu không có B6, dashboard chỉ biết KPI đạt hay chưa đạt, nhưng không biết KPI nào quan trọng hơn KPI nào.

---

## 3. Role được phép thao tác

## 3.1. CEO

CEO là người thao tác chính trong B6.

CEO được phép:

- Xem danh sách 4 góc độ BSC
- Xem danh sách mục tiêu chiến lược tổng từ B4
- Xem danh sách KPI nhỏ từ B5
- Phân bổ tỉ trọng cho 4 góc độ BSC
- Phân bổ tỉ trọng cho mục tiêu chiến lược trong từng góc độ
- Phân bổ tỉ trọng cho KPI nhỏ trong từng mục tiêu chiến lược
- Chỉnh sửa tỉ trọng khi BSC Strategy còn Draft
- Hoàn thành B6 để chuyển sang B7

CEO là người quyết định tỉ trọng vì tỉ trọng phản ánh mức độ ưu tiên chiến lược của toàn công ty.

---

## 3.2. Trưởng phòng

Trưởng phòng được phép:

- Xem tỉ trọng của các KPI nhỏ thuộc phòng ban mình
- Xem tỉ trọng của mục tiêu chiến lược mà phòng ban mình tham gia
- Xem tỉ trọng tổng liên quan đến phòng ban mình nếu được hệ thống hỗ trợ

Trưởng phòng không được phép:

- Phân bổ tỉ trọng
- Sửa tỉ trọng
- Xóa tỉ trọng
- Thay đổi tỉ trọng của KPI phòng mình hoặc phòng ban khác

Lý do: theo requirement hiện tại, CEO là người phân bổ tỉ trọng ở B6.

---

## 3.3. Nhân viên

Nhân viên có thể được phép xem tỉ trọng của KPI liên quan đến phòng ban mình hoặc task của mình ở các màn hình vận hành sau này.

Nhân viên không được phép thao tác B6.

---

## 3.4. Company Admin

Company Admin không được thao tác B6.

Company Admin chỉ setup dữ liệu nền công ty, phòng ban, chức vụ, nhân viên và phân quyền.

---

## 3.5. System Admin

System Admin thuộc bên cung cấp nền tảng.

System Admin chỉ monitoring hệ thống, không xem hoặc chỉnh sửa nội dung tỉ trọng chiến lược của doanh nghiệp.

---

## 4. Điều kiện truy cập bước

B6 chỉ được mở khi:

- Công ty đã được setup dữ liệu nền
- BSC Strategy đang ở trạng thái Draft
- B1 - Đánh giá đã hoàn thành
- B2 - Xây dựng chiến lược đã hoàn thành
- B3 - Kết quả chiến lược đã hoàn thành
- B4 - Bản đồ chiến lược đã hoàn thành
- B5 - Mô hình xương cá đã hoàn thành
- B4 đã có bản đồ chiến lược tổng
- B5 đã có KPI nhỏ hợp lệ cho từng mục tiêu chiến lược tổng
- Người thao tác có role CEO hoặc quyền tương đương

Nếu B5 chưa hoàn thành, hệ thống không cho CEO hoàn thành B6.

---

## 5. Nguyên tắc cốt lõi của B6

## 5.1. Tổng toàn bộ BSC luôn bằng 100%

Tất cả tỉ trọng trong B6 đều nằm trong tổng 100% của toàn bộ chiến lược BSC.

```text
Tổng BSC = 100%
```

4 góc độ BSC cộng lại phải đúng 100%.

---

## 5.2. Tỉ trọng là tỉ trọng tuyệt đối

Tỉ trọng ở tầng con không chia lại thành 100%.

Ví dụ sai:

```text
Tài chính = 40%

Trong Tài chính:
- Tăng doanh thu = 50%
- Tối ưu chi phí = 30%
- Tăng lợi nhuận = 20%

Tổng = 100% trong nhóm Tài chính
```

Cách trên là sai với requirement hiện tại vì nó dùng tỉ trọng tương đối trong nhóm.

Ví dụ đúng:

```text
Tài chính = 40%

Trong Tài chính:
- Tăng doanh thu = 20%
- Tối ưu chi phí = 12%
- Tăng lợi nhuận = 8%

Tổng = 40%
```

Tức là các mục tiêu trong Tài chính phải cộng lại đúng bằng 40%.

---

## 5.3. Tỉ trọng có thể rất nhỏ

Do bản đồ chiến lược tổng có thể có tối đa 24 mục tiêu, và mỗi mục tiêu có thể có nhiều KPI nhỏ từ nhiều phòng ban, nên tỉ trọng KPI nhỏ có thể rất nhỏ.

Ví dụ:

```text
0.1%
0.25%
0.5%
1.75%
```

Hệ thống cần hỗ trợ số thập phân.

Khuyến nghị kỹ thuật:

- Lưu bằng kiểu decimal, không dùng float/double để tránh sai số
- Có thể hỗ trợ tối thiểu 1 chữ số thập phân
- Tốt hơn nên hỗ trợ 2 chữ số thập phân nếu cần chi tiết hơn
- Khi validate tổng, nên xử lý bằng BigDecimal ở backend

---

## 6. Cấu trúc phân bổ tỉ trọng

B6 có 3 tầng phân bổ chính:

```text
Tầng 1: Tỉ trọng 4 góc độ BSC
Tầng 2: Tỉ trọng mục tiêu chiến lược trong từng góc độ
Tầng 3: Tỉ trọng KPI nhỏ trong từng mục tiêu chiến lược
```

---

# 7. Tầng 1 - Phân bổ tỉ trọng cho 4 góc độ BSC

## 7.1. Mục tiêu

CEO phân bổ 100% cho 4 góc độ BSC:

- Tài chính
- Khách hàng
- Quy trình nội bộ
- Học hỏi & phát triển

Ví dụ:

| Góc độ BSC | Tỉ trọng |
|---|---:|
| Tài chính | 40% |
| Khách hàng | 30% |
| Quy trình nội bộ | 15% |
| Học hỏi & phát triển | 15% |
| **Tổng** | **100%** |

---

## 7.2. Dữ liệu cần lưu

Mỗi tỉ trọng góc độ cần lưu:

- BSC Strategy ID
- Perspective code
- Perspective name
- Weight percent
- Created by
- Updated by
- Created at
- Updated at

Ví dụ:

```text
Perspective:
FINANCIAL

Weight:
40%
```

---

## 7.3. Rule validate tầng 1

Hệ thống cần kiểm tra:

- Có đủ 4 góc độ BSC
- Mỗi góc độ phải có tỉ trọng
- Tỉ trọng mỗi góc độ phải lớn hơn 0
- Tổng tỉ trọng 4 góc độ phải bằng 100%
- Không cho nhập số âm
- Không cho nhập text không phải số
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương

---

# 8. Tầng 2 - Phân bổ tỉ trọng cho mục tiêu chiến lược

## 8.1. Mục tiêu

Sau khi phân bổ tỉ trọng cho 4 góc độ, CEO phân bổ tiếp tỉ trọng cho các mục tiêu chiến lược trong từng góc độ.

Các mục tiêu chiến lược này lấy từ bản đồ chiến lược tổng ở B4.

Ví dụ:

```text
Tài chính = 40%
```

Trong góc độ Tài chính có 3 mục tiêu:

| Mục tiêu chiến lược | Tỉ trọng |
|---|---:|
| Tăng doanh thu | 20% |
| Tối ưu chi phí | 12% |
| Tăng lợi nhuận ròng | 8% |
| **Tổng trong Tài chính** | **40%** |

Tổng tỉ trọng các mục tiêu thuộc Tài chính phải bằng đúng tỉ trọng của góc độ Tài chính.

---

## 8.2. Dữ liệu cần lưu

Mỗi tỉ trọng mục tiêu chiến lược cần lưu:

- BSC Strategy ID
- Final Strategic Objective ID
- Perspective code
- Weight percent
- Created by
- Updated by
- Created at
- Updated at

Ví dụ:

```text
Final Strategic Objective:
Tăng doanh thu

Perspective:
FINANCIAL

Weight:
20%
```

---

## 8.3. Rule validate tầng 2

Hệ thống cần kiểm tra:

- Mỗi mục tiêu chiến lược tổng từ B4 phải có tỉ trọng
- Mỗi mục tiêu chiến lược phải thuộc một góc độ BSC hợp lệ
- Tỉ trọng mỗi mục tiêu phải lớn hơn 0
- Tỉ trọng mỗi mục tiêu không được vượt quá tỉ trọng góc độ cha
- Tổng tỉ trọng mục tiêu trong từng góc độ phải bằng tỉ trọng của góc độ đó
- Không cho nhập số âm
- Không cho nhập text không phải số
- Chỉ phân bổ cho mục tiêu thuộc bản đồ chiến lược tổng hiện tại
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương

---

# 9. Tầng 3 - Phân bổ tỉ trọng cho KPI nhỏ

## 9.1. Mục tiêu

Sau khi phân bổ tỉ trọng cho mục tiêu chiến lược, CEO phân bổ tiếp tỉ trọng cho các KPI nhỏ bên trong từng mục tiêu.

Các KPI nhỏ này được trưởng phòng tạo ở B5.

Ví dụ:

```text
Mục tiêu chiến lược:
Tăng doanh thu

Tỉ trọng mục tiêu:
20%
```

Trong mục tiêu này có 3 KPI nhỏ:

| KPI nhỏ | Phòng ban | Tỉ trọng |
|---|---|---:|
| Tăng số lượng lead chất lượng | Marketing | 7% |
| Tăng tỷ lệ chốt đơn | Sales | 10% |
| Tối ưu gói sản phẩm bán chạy | Product | 3% |
| **Tổng KPI trong mục tiêu** |  | **20%** |

Tổng tỉ trọng KPI nhỏ trong mục tiêu **Tăng doanh thu** phải bằng đúng tỉ trọng của mục tiêu chiến lược đó.

---

## 9.2. Dữ liệu cần lưu

Mỗi tỉ trọng KPI nhỏ cần lưu:

- BSC Strategy ID
- Department KPI ID
- Final Strategic Objective ID
- Department ID
- Perspective code
- Weight percent
- Created by
- Updated by
- Created at
- Updated at

Ví dụ:

```text
Department KPI:
Tăng tỷ lệ chốt đơn

Department:
Sales

Final Strategic Objective:
Tăng doanh thu

Weight:
10%
```

---

## 9.3. Rule validate tầng 3

Hệ thống cần kiểm tra:

- Mỗi KPI nhỏ từ B5 phải có tỉ trọng
- KPI nhỏ phải thuộc một mục tiêu chiến lược tổng hợp lệ
- KPI nhỏ phải thuộc một phòng ban hợp lệ
- Tỉ trọng mỗi KPI nhỏ phải lớn hơn 0
- Tỉ trọng mỗi KPI nhỏ không được vượt quá tỉ trọng mục tiêu chiến lược cha
- Tổng tỉ trọng KPI nhỏ trong từng mục tiêu chiến lược phải bằng tỉ trọng của mục tiêu đó
- Không cho nhập số âm
- Không cho nhập text không phải số
- Chỉ phân bổ cho KPI nhỏ thuộc BSC Strategy hiện tại
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương

---

## 10. Ví dụ đầy đủ về phân bổ tỉ trọng

Ví dụ BSC có tổng 100%.

## 10.1. Tầng 1 - Góc độ BSC

| Góc độ | Tỉ trọng |
|---|---:|
| Tài chính | 40% |
| Khách hàng | 30% |
| Quy trình nội bộ | 15% |
| Học hỏi & phát triển | 15% |
| **Tổng** | **100%** |

---

## 10.2. Tầng 2 - Mục tiêu chiến lược

### Tài chính = 40%

| Mục tiêu chiến lược | Tỉ trọng |
|---|---:|
| Tăng doanh thu | 20% |
| Tối ưu chi phí | 12% |
| Tăng lợi nhuận ròng | 8% |
| **Tổng** | **40%** |

### Khách hàng = 30%

| Mục tiêu chiến lược | Tỉ trọng |
|---|---:|
| Tăng mức độ hài lòng khách hàng | 18% |
| Tăng tỷ lệ khách hàng quay lại | 12% |
| **Tổng** | **30%** |

### Quy trình nội bộ = 15%

| Mục tiêu chiến lược | Tỉ trọng |
|---|---:|
| Rút ngắn thời gian xử lý đơn hàng | 9% |
| Chuẩn hóa quy trình chăm sóc khách hàng | 6% |
| **Tổng** | **15%** |

### Học hỏi & phát triển = 15%

| Mục tiêu chiến lược | Tỉ trọng |
|---|---:|
| Nâng cao năng lực đội ngũ sales | 8% |
| Cải thiện năng lực quản lý nội bộ | 7% |
| **Tổng** | **15%** |

---

## 10.3. Tầng 3 - KPI nhỏ

Ví dụ mục tiêu **Tăng doanh thu = 20%**:

| KPI nhỏ | Phòng ban | Tỉ trọng |
|---|---|---:|
| Tăng số lượng lead chất lượng | Marketing | 7% |
| Tăng tỷ lệ chốt đơn | Sales | 10% |
| Tối ưu gói sản phẩm bán chạy | Product | 3% |
| **Tổng** |  | **20%** |

Ví dụ mục tiêu **Tăng mức độ hài lòng khách hàng = 18%**:

| KPI nhỏ | Phòng ban | Tỉ trọng |
|---|---|---:|
| Giảm thời gian phản hồi ticket | CSKH | 6% |
| Giảm lỗi nghiêm trọng ảnh hưởng khách hàng | Product | 5% |
| Đào tạo kỹ năng giao tiếp khách hàng | HR | 3% |
| Tăng tỷ lệ follow-up sau demo | Sales | 4% |
| **Tổng** |  | **18%** |

---

## 11. User flow của B6

## 11.1. CEO vào màn hình B6

Luồng:

```text
CEO hoàn thành B5
→ Hệ thống mở B6
→ CEO vào màn hình Phân bổ tỉ trọng
→ Hệ thống hiển thị 4 góc độ BSC
→ Hệ thống hiển thị mục tiêu chiến lược theo từng góc độ
→ Hệ thống hiển thị KPI nhỏ trong từng mục tiêu
```

---

## 11.2. CEO phân bổ tỉ trọng góc độ BSC

CEO nhập tỉ trọng cho 4 góc độ.

Hệ thống có thể hiển thị tổng realtime:

```text
Tài chính: 40%
Khách hàng: 30%
Quy trình nội bộ: 15%
Học hỏi & phát triển: 15%

Tổng: 100%
```

Nếu tổng chưa đủ hoặc vượt 100%, hệ thống cảnh báo.

---

## 11.3. CEO phân bổ tỉ trọng mục tiêu chiến lược

Sau khi phân bổ góc độ, CEO phân bổ tỉ trọng cho mục tiêu trong từng góc độ.

Ví dụ:

```text
Tài chính = 40%
```

CEO cần chia 40% này cho các mục tiêu thuộc Tài chính.

Hệ thống nên hiển thị:

```text
Tổng cần phân bổ: 40%
Đã phân bổ: 32%
Còn lại: 8%
```

---

## 11.4. CEO phân bổ tỉ trọng KPI nhỏ

Sau khi phân bổ mục tiêu, CEO phân bổ tỉ trọng cho KPI nhỏ trong từng mục tiêu.

Ví dụ:

```text
Tăng doanh thu = 20%
```

CEO cần chia 20% này cho các KPI nhỏ thuộc mục tiêu Tăng doanh thu.

Hệ thống nên hiển thị:

```text
Tổng cần phân bổ: 20%
Đã phân bổ: 17%
Còn lại: 3%
```

---

## 11.5. CEO xác nhận hoàn thành B6

Khi tất cả tổng đều đúng, CEO bấm hoàn thành B6.

Hệ thống validate toàn bộ:

```text
Tổng 4 góc độ = 100%
Tổng mục tiêu trong từng góc độ = tỉ trọng góc độ đó
Tổng KPI trong từng mục tiêu = tỉ trọng mục tiêu đó
```

Nếu hợp lệ, B6 chuyển sang trạng thái Completed và mở khóa B7.

---

## 12. Validate tổng thể của B6

Để hoàn thành B6, hệ thống cần kiểm tra:

- B1 đã hoàn thành
- B2 đã hoàn thành
- B3 đã hoàn thành
- B4 đã hoàn thành
- B5 đã hoàn thành
- BSC Strategy đang ở trạng thái Draft
- Người thao tác là CEO hoặc quyền tương đương
- Có đủ 4 góc độ BSC
- Tổng tỉ trọng 4 góc độ bằng 100%
- Mỗi mục tiêu chiến lược tổng có tỉ trọng
- Tổng tỉ trọng mục tiêu trong từng góc độ bằng tỉ trọng góc độ đó
- Mỗi KPI nhỏ có tỉ trọng
- Tổng tỉ trọng KPI nhỏ trong từng mục tiêu bằng tỉ trọng mục tiêu đó
- Không có tỉ trọng âm
- Không có tỉ trọng bằng 0 nếu item đó vẫn được giữ trong workflow
- Không có item thuộc B4/B5 bị thiếu tỉ trọng
- Không phân bổ tỉ trọng cho mục tiêu hoặc KPI không thuộc BSC Strategy hiện tại

---

## 13. Gợi ý xử lý sai số thập phân

Vì tỉ trọng có thể là số thập phân nhỏ như 0.1%, hệ thống cần xử lý sai số cẩn thận.

Khuyến nghị:

```text
Backend:
- Dùng BigDecimal
- Không dùng float/double để validate tổng
- Chuẩn hóa scale, ví dụ 2 chữ số thập phân

Database:
- Dùng DECIMAL, ví dụ DECIMAL(6,2) hoặc DECIMAL(7,3)
```

Ví dụ:

```text
DECIMAL(6,2)
Cho phép lưu tối đa 9999.99

DECIMAL(7,3)
Cho phép lưu tối đa 9999.999
```

Với tỉ trọng phần trăm, thường chỉ cần giá trị từ 0 đến 100.

Gợi ý thực tế:

```text
weight_percent DECIMAL(6,3)
```

Cách này hỗ trợ các giá trị như:

```text
0.100
1.250
12.500
40.000
100.000
```

Nếu FE chỉ muốn hiển thị gọn, có thể format lại thành:

```text
0.1%
1.25%
12.5%
40%
```

---

## 14. Output của B6

Sau khi hoàn thành B6, hệ thống lưu lại:

- Tỉ trọng của 4 góc độ BSC
- Tỉ trọng của từng mục tiêu chiến lược tổng
- Tỉ trọng của từng KPI nhỏ
- Người phân bổ tỉ trọng
- Thời điểm phân bổ
- Trạng thái hoàn thành B6

Dữ liệu quan trọng nhất được chuyển sang B7 là:

```text
Danh sách KPI nhỏ đã có tỉ trọng
```

B7 sẽ sử dụng danh sách này để CEO nhập đo lường và chỉ tiêu.

---

## 15. Liên kết với B7 - Đo lường & Chỉ tiêu

Sau khi B6 hoàn thành, B7 sẽ lấy dữ liệu KPI nhỏ đã có tỉ trọng để CEO nhập:

- Đơn vị đo
- Target
- Giá trị hiện tại nếu có
- Công thức đo
- Kỳ báo cáo
- Chiều tốt của KPI
- Ngưỡng đánh giá
- Người/phòng ban chịu trách nhiệm báo cáo nếu cần

B6 chỉ xác định KPI nào quan trọng bao nhiêu. B7 mới xác định KPI đó được đo như thế nào và cần đạt bao nhiêu.

---

## 16. Liên kết với dashboard

B6 là nền tảng để dashboard tính điểm tổng thể.

Ví dụ:

```text
KPI A có tỉ trọng 10%
KPI A đạt 80%

Đóng góp của KPI A vào toàn công ty:
10% * 80% = 8%
```

Từ KPI nhỏ, hệ thống có thể cộng ngược lên:

```text
KPI nhỏ
→ Mục tiêu chiến lược
→ Góc độ BSC
→ Toàn công ty
```

Nhờ B6, CEO có thể nhìn dashboard theo nhiều tầng:

- Toàn công ty đạt bao nhiêu %
- Góc độ Tài chính đạt bao nhiêu %
- Mục tiêu Tăng doanh thu đạt bao nhiêu %
- KPI Sales/Marketing/Product trong mục tiêu đó đạt bao nhiêu %

---

## 17. Ảnh hưởng khi chỉnh sửa B5 sau khi đã có B6

Khi BSC Strategy còn Draft, trưởng phòng có thể chỉnh sửa KPI ở B5. Tuy nhiên, nếu B5 thay đổi sau khi B6 đã phân bổ tỉ trọng, hệ thống cần xử lý cẩn thận.

## 17.1. KPI nhỏ bị xóa

Nếu một KPI nhỏ đã có tỉ trọng ở B6 bị xóa ở B5:

- Tỉ trọng của KPI đó không còn hợp lệ
- Tổng tỉ trọng KPI trong mục tiêu cha có thể bị thiếu
- B6 cần được đánh dấu Invalidated
- CEO cần phân bổ lại tỉ trọng trong mục tiêu đó

Ví dụ:

```text
Mục tiêu Tăng doanh thu = 20%

KPI Sales = 10%
KPI Marketing = 7%
KPI Product = 3%

Nếu KPI Product bị xóa:
→ Tổng còn 17%
→ Thiếu 3%
→ B6 không còn hợp lệ
```

---

## 17.2. KPI nhỏ được thêm mới

Nếu trưởng phòng thêm KPI mới ở B5 sau khi B6 đã hoàn thành:

- KPI mới chưa có tỉ trọng
- B6 cần được cập nhật lại
- CEO cần chia lại tỉ trọng trong mục tiêu chứa KPI mới

---

## 17.3. KPI nhỏ đổi tên/mô tả

Nếu KPI chỉ đổi tên hoặc mô tả nhưng ID không đổi:

- B6 có thể giữ nguyên tỉ trọng
- Hệ thống cập nhật tên/mô tả mới khi render
- Không cần invalid toàn bộ B6

---

## 17.4. Mục tiêu chiến lược tổng thay đổi từ B4

Nếu B4 thay đổi làm mục tiêu chiến lược bị thêm/xóa/merge, B5 và B6 đều có thể bị ảnh hưởng.

Với MVP, hướng xử lý an toàn:

```text
B4 thay đổi lớn
→ B5 Invalidated
→ B6 Invalidated
→ CEO/trưởng phòng cần kiểm tra lại từ B5
```

---

## 18. Trạng thái của bước

B6 có thể có các trạng thái:

- Not Started: chưa nhập tỉ trọng
- In Progress: đang phân bổ tỉ trọng
- Invalid: tổng tỉ trọng chưa hợp lệ
- Completed: đã phân bổ hợp lệ và xác nhận hoàn thành
- Invalidated: dữ liệu B4 hoặc B5 thay đổi làm tỉ trọng không còn hợp lệ
- Locked: bị khóa chỉnh sửa sau khi BSC Strategy được kích hoạt

Khi BSC Strategy còn Draft, CEO có thể quay lại chỉnh sửa B6.

Khi BSC Strategy đã Active, B6 bị hạn chế chỉnh sửa vì thay đổi tỉ trọng sẽ ảnh hưởng trực tiếp đến cách tính KPI, dashboard và báo cáo.

---

## 19. Gợi ý UI/UX cho B6

## 19.1. View phân bổ theo cây

Gợi ý hiển thị dạng cây:

```text
100% BSC
├── Tài chính: 40%
│   ├── Tăng doanh thu: 20%
│   │   ├── KPI Sales: 10%
│   │   ├── KPI Marketing: 7%
│   │   └── KPI Product: 3%
│   ├── Tối ưu chi phí: 12%
│   └── Tăng lợi nhuận ròng: 8%
├── Khách hàng: 30%
├── Quy trình nội bộ: 15%
└── Học hỏi & phát triển: 15%
```

View này giúp CEO nhìn rõ tỉ trọng đi từ tổng xuống từng tầng.

---

## 19.2. Hiển thị tổng realtime

Ở mỗi nhóm, FE nên hiển thị:

- Tổng cần phân bổ
- Đã phân bổ
- Còn lại
- Trạng thái hợp lệ/chưa hợp lệ

Ví dụ:

```text
Tài chính
Tổng cần phân bổ: 40%
Đã phân bổ: 36%
Còn lại: 4%
Trạng thái: Chưa hợp lệ
```

Khi đã đúng:

```text
Tài chính
Tổng cần phân bổ: 40%
Đã phân bổ: 40%
Còn lại: 0%
Trạng thái: Hợp lệ
```

---

## 19.3. Cảnh báo lỗi nhập liệu

Các lỗi thường gặp:

```text
Tổng 4 góc độ vượt quá 100%
Tổng mục tiêu trong Tài chính chưa bằng 40%
KPI trong mục tiêu Tăng doanh thu còn thiếu 3%
Không được nhập tỉ trọng âm
Tỉ trọng phải là số
KPI này chưa được phân bổ tỉ trọng
```

---

## 19.4. Auto-fill gợi ý

Để hỗ trợ UX, hệ thống có thể có nút auto-fill đơn giản:

- Chia đều tỉ trọng cho các item trong nhóm
- Sau đó CEO chỉnh lại thủ công

Ví dụ:

```text
Mục tiêu Tăng doanh thu = 20%
Có 4 KPI nhỏ

Auto-fill:
Mỗi KPI = 5%
```

Lưu ý: auto-fill chỉ là hỗ trợ nhập liệu, không phải quyết định chiến lược thay CEO.

---

## 20. Ghi chú nghiệp vụ

B6 là bước giúp lượng hóa mức độ ưu tiên của chiến lược.

Kết quả quan trọng nhất của B6 là hệ thống biết mỗi KPI nhỏ đóng góp bao nhiêu phần trăm vào toàn bộ chiến lược BSC của công ty.

Nguyên tắc quan trọng nhất:

```text
Tỉ trọng là tỉ trọng tuyệt đối trên tổng 100%.
Không chia lại thành 100% ở từng tầng con.
```

CEO là người phân bổ tỉ trọng vì đây là quyết định cấp chiến lược.

Từ B7 trở đi, các KPI nhỏ đã có tỉ trọng sẽ được định nghĩa cách đo, chỉ tiêu và sau đó liên kết với action plan/task để vận hành.
