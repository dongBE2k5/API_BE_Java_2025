package tdc.fit.bookingHotel.entity;
import org.springframework.data.annotation.Transient;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "name")
    private String name;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location locationId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "status")
    private String status;

    
    @Lob
    @Column(name = "image")
    private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", name=" + name + ", locationId=" + locationId + ", address=" + address
				+ ", phone=" + phone + ", email=" + email + ", status=" + status + ", image=" + image + "]";
	}
    
	
}