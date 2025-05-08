package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import tdc.fit.bookingHotel.entity.RoomType;


public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
