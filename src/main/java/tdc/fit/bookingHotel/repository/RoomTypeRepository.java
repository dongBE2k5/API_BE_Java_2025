package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
