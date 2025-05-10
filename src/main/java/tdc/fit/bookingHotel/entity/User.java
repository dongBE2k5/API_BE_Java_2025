package tdc.fit.bookingHotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username; // tên đăng nhập (Primary Key)

    @Column(nullable = false)
    private String password; // mật khẩu (nên lưu dạng mã hoá BCrypt)

    @Column(nullable = false)
    private String roles; // danh sách quyền (dạng chuỗi, phân cách bằng dấu phẩy): "ROLE_USER,ROLE_ADMIN"

    // Getter/Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
}