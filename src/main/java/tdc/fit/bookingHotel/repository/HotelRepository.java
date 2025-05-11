package tdc.fit.bookingHotel.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByHotelierId(Hotelier hotelier);
    List<Hotel> findByHotelierIdIn(List<Hotelier> hoteliers);
}