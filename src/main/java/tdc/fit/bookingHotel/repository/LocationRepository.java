package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import tdc.fit.bookingHotel.entity.Location;



public interface LocationRepository extends JpaRepository<Location, Long> {
	
}

