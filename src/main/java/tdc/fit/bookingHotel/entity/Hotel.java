package tdc.fit.bookingHotel.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import tdc.fit.bookingHotel.Json.HotelSerializer;
import tdc.fit.bookingHotel.Json.HotelierSerializer;
import tdc.fit.bookingHotel.Json.LocationSerializer;


@JsonSerialize(using = HotelSerializer.class)
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

 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @JsonSerialize(using = LocationSerializer.class)
//  @JsonIgnoreProperties({"hotels"})
    private Location location;
//    @JsonBackReference

    @Column(name = "address")
    private String address;
    
    
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelier_id")
    @JsonSerialize(using = HotelierSerializer.class)
//    @JsonIgnoreProperties({"hotels"}) 
    private Hotelier hotelier;
    
    
    @Lob
    @Column(name = "image")
    private String image;

    
	public Hotelier getHotelier() {
		return hotelier;
	}

	public void setHotelier(Hotelier hotelier) {
		this.hotelier = hotelier;
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

	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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
		return "Hotel [hotelId=" + hotelId + ", name=" + name + ", locationId=" + location + ", address=" + address
				+ ", status=" + status + ", hotelierId=" + hotelier + ", image=" + image + "]";
	}
    
	
}