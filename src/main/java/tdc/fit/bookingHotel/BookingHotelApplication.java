package tdc.fit.bookingHotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "tdc.fit.bookingHotel")
@EnableJpaRepositories(basePackages = "tdc.fit.bookingHotel")
@EntityScan(basePackages = "tdc.fit.bookingHotel")
public class BookingHotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingHotelApplication.class, args);
	}

}
