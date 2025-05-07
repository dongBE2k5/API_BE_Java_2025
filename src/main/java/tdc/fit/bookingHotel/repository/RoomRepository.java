package tdc.fit.bookingHotel.repository;

import tdc.fit.bookingHotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}