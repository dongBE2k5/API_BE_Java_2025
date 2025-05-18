package tdc.fit.bookingHotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tdc.fit.bookingHotel.Json.RoomSerializer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = RoomSerializer.class)
@Entity
@Table(name = "rooms")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomId")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_number")
    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private BigDecimal price;
    
    @Lob
    @Column(name = "image")
    private String image;
    
    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    
    @OneToMany(mappedBy = "room",  orphanRemoval = true)
    @JsonIgnore
	private List<Booking> bookings = new ArrayList<>();

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	
    
}