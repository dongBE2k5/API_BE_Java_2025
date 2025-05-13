package tdc.fit.bookingHotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.HotelierRepository;
import tdc.fit.bookingHotel.repository.LocationRepository;
import tdc.fit.bookingHotel.repository.UserRepository;

@Service
public class HotelService {
	@Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelierRepository hotelierRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Lấy tất cả các khách sạn
//    @PreAuthorize("hasAuthority('ROLE_SUPPERADMIN')")
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return ResponseEntity.ok(hotels);
    }

    // Lấy khách sạn theo ID
    
    public ResponseEntity<?> getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        return ResponseEntity.ok(hotel);
    }
    
    // lấy theo Hotelier
    public ResponseEntity<?> getHotelByHotelier(Authentication authentication) {
    	
   	 String username = authentication.getName();
	 User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	 Hotelier hotelier = hotelierRepository.findByUserId(user);
	 
        List<Hotel> hotel = hotelRepository.findByHotelierId(hotelier);
        if (hotel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found");
        }
               
        return ResponseEntity.ok(hotel);
    }
    
    // lấy theo Location
//    public ResponseEntity<?> getHotelByLocation(Long locationId ) {
//    	
//    	Location location = locationRepository.findById(locationId)
//    		    .orElseThrow(() -> new EntityNotFoundException("Location not found"));;
//        List<Hotel> hotel = hotelRepository.findByLocationId(location);
//        if (hotel.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found");
//        }
//               
//        return ResponseEntity.ok(hotel);
//    }


    // Tạo mới một khách sạn
    public ResponseEntity<?> createHotel(Hotel hotel) {
        // Kiểm tra xem location và hotelier có tồn tại không
        if (locationRepository.existsById(hotel.getLocationId().getLocationId()) &&
            hotelierRepository.existsById(hotel.getHotelierId().getHotelierId())) {

            Hotel savedHotel = hotelRepository.save(hotel);
            return ResponseEntity.ok(savedHotel);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Location or Hotelier not found");
        }
    }

    // Cập nhật thông tin khách sạn
    public ResponseEntity<?> updateHotel(Long hotelId, Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        // Cập nhật thông tin khách sạn
        hotel.setName(hotelDetails.getName());
        hotel.setAddress(hotelDetails.getAddress());
        hotel.setStatus(hotelDetails.getStatus());
        hotel.setLocationId(hotelDetails.getLocationId());
//        hotel.setHotelierId(hotelDetails.getHotelierId());
        hotel.setImage(hotelDetails.getImage());

        Hotel updatedHotel = hotelRepository.save(hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    // Xoá khách sạn theo ID
    public ResponseEntity<?> deleteHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        hotelRepository.delete(hotel);
        return ResponseEntity.ok("Hotel deleted successfully");
    }
}
