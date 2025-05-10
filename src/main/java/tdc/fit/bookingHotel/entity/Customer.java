package tdc.fit.bookingHotel.entity;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;
import org.springframework.lang.Nullable;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Nullable
	private String fullname;
	
	@Column(nullable = false)
	private String email;
	@Nullable
	private String phone;
	@Nullable
	private String cccd;

	 @OneToOne
	 @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user_id;

	public User getIdUser() {
		return user_id;
	}

	public void setIdUser(User user_id) {
		this.user_id = user_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

}
