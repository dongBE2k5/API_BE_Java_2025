package tdc.fit.bookingHotel.entity;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import tdc.fit.bookingHotel.Json.LocationSerializer;

@JsonSerialize(using = LocationSerializer.class)
@Entity
@Table(name = "locations")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "locationId")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    
    @Lob
    @Column(name = "image")
    private String image;
    
    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonManagedReference
    @JsonIgnore
    private List<Hotel> hotels = new ArrayList<>();

    
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
