package tdc.fit.bookingHotel.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
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
    
    @OneToMany(mappedBy = "locationId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Hotel> hotels = new ArrayList<>();

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
