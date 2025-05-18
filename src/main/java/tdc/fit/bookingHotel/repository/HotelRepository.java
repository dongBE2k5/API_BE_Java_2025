package tdc.fit.bookingHotel.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Location;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
    List<Hotel> findByHotelier(Hotelier hotelier);
    List<Hotel> findByHotelierIn(List<Hotelier> hoteliers);
    List<Hotel> findByLocation(Location location);
    List<Hotel> findByLocationIn(List<Location> location);
}