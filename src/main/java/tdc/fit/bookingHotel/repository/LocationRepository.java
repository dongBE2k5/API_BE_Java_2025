package tdc.fit.bookingHotel.repository;

import  tdc.fit.bookingHotel.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Integer> {
}

