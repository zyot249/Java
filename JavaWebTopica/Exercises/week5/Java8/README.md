# `Vấn đề của date và time API cũ`:
#### - Các lớp Java Date Time không được định nghĩa thống nhất, chúng ta có lớp Date trong cả java.util cũng như là java.sql packages. Các lớp formatting và parsing lại được định nghĩa trong java.text package.
#### - java.util.Date bao gồm cả date và time, trong khi đó java.sql.Date chỉ có date. Việc chỉ có date trong java.sql package là không hợp lý. Hơn nữa là chúng lại có cùng tên và thiết kế của chúng cũng rất tệ.
#### - Chưa có những lớp được định nghĩa rõ ràng cho time, timestamp, formatting and parsing. Chúng ta có lớp trừu tượng java.text.DateFormat để parsing và formatting khi cần nhưng lại thường xuyên dùng lớp SimpleDateFormat.
#### - Tất cả các lớp Date là dễ thay đổi, vì thế chúng không an toàn trong đa luồng. Đó thực sự là 1 vấn đề rất lớp đối với các lớp Java Date và Java Calendar.
#### - Lớp Date chưa cung cấp sự quốc tế hóa, cụ thể là nó không hỗ trợ timezone. Do đó mà Java Calendar và Java Timezone được ra đời nhưng chúng vẫn tồn tại các vấn đề trên.

# `Date Time API mới`:
#### - Tính bất biến: Tất cả các lớp trong Date Time API mới đều là không thay đổi được và an toàn trong môi trường đa luồng.
#### - Phân chia sự liên quan: APT mới phân chia rõ ràng giữa date time cho người và cho máy(unix timestamp). Nó định nghĩa các lớp cụ thể cho Date, Time, DateTime, Timestamp, Timezone, ....
#### - Sự rõ ràng: Các phương thức được định nghĩa rõ ràng và thực hiện các hoạt động chung trong tất cả các lớp. Ví dụ, để lấy thực thể hiện tại chúng ta dùng phương thức now(): LocalDate.now(), LocalTime.now(), .... Có phương thức format và parse cho từng lớp.
#### - Tất cả các lớp đều sử dụng Factory Pattern và Strategy Pattern để xử lý tốt hơn. Một khi bạn có các phương thức trong mô hình của một lớp, thao tác với các lớp khác sẽ không còn khó khăn.
#### - Utility operations: All the new Date Time API classes comes with methods to perform common tasks, such as plus, minus, format, parsing, getting separate part in date/time etc.
#### - Khả năng mở rộng: Date Time API mới làm việc trên ISO-8601 hệ thống lịch nhưng chúng ta hoàn toàn có thể sử dụng chúng với các hệ thống lịch tiêu chuẩn khác.