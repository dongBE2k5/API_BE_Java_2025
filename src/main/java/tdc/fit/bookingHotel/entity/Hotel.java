package tdc.fit.bookingHotel.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hotelId")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @Column(name = "address")
    private String address;
    
    
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "hotelier_id")
    private Hotelier hotelierId;
    
    
    @Lob
    @Column(name = "image")
    private String image;

    
	public Hotelier getHotelierId() {
		return hotelierId;
	}

	public void setHotelierId(Hotelier hotelierId) {
		this.hotelierId = hotelierId;
	}

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

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", name=" + name + ", locationId=" + locationId + ", address=" + address
				+ ", status=" + status + ", hotelierId=" + hotelierId + ", image=" + image + "]";
	}
    
	
}