package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}