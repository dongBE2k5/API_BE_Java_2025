package tdc.fit.bookingHotel.repository;

import tdc.fit.bookingHotel.entity.Booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	   @Query("SELECT b FROM Booking b WHERE b.room.roomId = :roomId AND " +
	           "(b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
	    List<Booking> findOverlappingBookings(Integer roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
