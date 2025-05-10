package tdc.fit.bookingHotel.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hoteliers")
public class Hotelier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotelier_id")
	private Long hotelierId;

	@Column(nullable = false, unique = true)
	private String name; // tên đăng nhập (Primary Key)

	@Column(nullable = false)
	private String fullName; // mật khẩu (nên lưu dạng mã hoá BCrypt)

	@Nullable
	private String phone;
	@Nullable
	private String cccd;
	
	 @Column(name = "email")
	    private String email;
	

	@OneToMany(mappedBy = "hotelierId", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Hotel> hotels = new ArrayList<>();
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getHotelierId() {
		return hotelierId;
	}

	public void setHotelierId(Long hotelierId) {
		this.hotelierId = hotelierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
	
	

}
