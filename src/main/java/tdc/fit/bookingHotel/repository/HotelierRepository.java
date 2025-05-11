package tdc.fit.bookingHotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.User;


@Repository
public interface HotelierRepository extends JpaRepository<Hotelier, Long> {
		Hotelier findByUserId (User user) ;
	

}
