package tdc.fit.bookingHotel.repository;

import tdc.fit.bookingHotel.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}