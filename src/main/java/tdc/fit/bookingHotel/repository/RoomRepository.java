package tdc.fit.bookingHotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tdc.fit.bookingHotel.entity.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {
}