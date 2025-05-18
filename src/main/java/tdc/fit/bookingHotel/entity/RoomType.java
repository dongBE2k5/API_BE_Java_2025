package tdc.fit.bookingHotel.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import tdc.fit.bookingHotel.Json.RoomTypeSerializer;

@JsonSerialize(using = RoomTypeSerializer.class)
@Entity
@Table(name = "room_types")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomTypeId")
@Getter
@Setter
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Integer roomTypeId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roomType",  orphanRemoval = true)
    @JsonIgnore
	private List<Room> rooms = new ArrayList<>();
    
    
	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
    
}