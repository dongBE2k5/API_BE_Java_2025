package tdc.fit.bookingHotel.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Booking;
import tdc.fit.bookingHotel.entity.Customer;
import tdc.fit.bookingHotel.entity.Room;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	   @Query("SELECT b FROM Booking b WHERE b.room.roomId = :roomId AND " +
	           "(b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
	    List<Booking> findOverlappingBookings(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
	   
	    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b " +
	            "WHERE b.room.roomId = :roomId " +
	            "AND (b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
	     boolean existsOverlappingBookings( Long roomId,
	                                    LocalDate checkInDate,
	                                     LocalDate checkOutDate);
	   
	   List<Booking> findByCustomer(Customer customer);
	   List<Booking> findByRoomIn(List<Room> rooms);
	   
	   void deleteByCustomerAndBookingId(Customer customer, Integer bookingId);

	List<Booking> findByCustomer_CustomerId(Long id);
}
