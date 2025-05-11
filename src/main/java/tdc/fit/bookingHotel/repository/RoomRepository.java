package tdc.fit.bookingHotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	List<Room> findByHotel(Hotel hotel);

	List<Room> findByHotelIn(List<Hotel> hotel);
}