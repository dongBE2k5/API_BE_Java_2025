package tdc.fit.bookingHotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tdc.fit.bookingHotel.Json.BookingSerializer;
import tdc.fit.bookingHotel.Json.CustomerSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(using = BookingSerializer.class)
@Entity
@Table(name = "bookings")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookingId")
@Getter
@Setter
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Integer bookingId;

	@ManyToOne
	@JoinColumn(name ="customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@Column(name = "check_in_date")
	private LocalDate checkInDate;

	@Column(name = "check_out_date")
	private LocalDate checkOutDate;

	@Column(name = "status")
	private String status;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "ngayTraPhong")
	private LocalDate ngayTraPhong;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
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

	public LocalDate getNgayTraPhong() {
		return ngayTraPhong;
	}

	public void setNgayTraPhong(LocalDate ngayTraPhong) {
		this.ngayTraPhong = ngayTraPhong;
	}

	@Override
	public String toString() {
		return "Booking{" +
				"bookingId=" + bookingId +
				", customer=" + customer +
				", room=" + room +
				", checkInDate=" + checkInDate +
				", checkOutDate=" + checkOutDate +
				", status='" + status + '\'' +
				'}';
	}
}
