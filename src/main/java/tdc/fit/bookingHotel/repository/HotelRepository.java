package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import tdc.fit.bookingHotel.entity.Hotel;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
}