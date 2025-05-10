package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	
}

