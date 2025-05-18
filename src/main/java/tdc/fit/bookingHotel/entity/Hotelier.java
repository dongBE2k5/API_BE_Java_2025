package tdc.fit.bookingHotel.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tdc.fit.bookingHotel.Json.CustomerSerializer;
import tdc.fit.bookingHotel.Json.HotelierSerializer;


@JsonSerialize(using = HotelierSerializer.class)
@Entity
@Table(name = "hoteliers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hotelierId")
@Getter
@Setter
public class Hotelier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotelier_id")
	private Long hotelierId;
	
    @JsonIgnore  // ⛔️ Ẩn khi trả JSON
	@Column(nullable = false, unique = true)
	private String name; 

	@Column(nullable = false)
	private String fullName; // mật khẩu (nên lưu dạng mã hoá BCrypt)

	@Nullable
	private String phone;
	
    @JsonIgnore  // ⛔️ Ẩn khi trả JSON
	@Nullable
	private String cccd;
	
	 @Column(name = "email")
	    private String email;
	
	 @OneToOne
	 @JoinColumn(name = "user_id")
	private User userId;
	 
	@OneToMany(mappedBy = "hotelier", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
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
