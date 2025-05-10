package tdc.fit.bookingHotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}