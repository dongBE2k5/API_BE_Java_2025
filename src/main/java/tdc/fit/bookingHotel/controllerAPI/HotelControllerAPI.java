package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.entity.DTO.HotelDTO;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelControllerAPI {

    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody HotelDTO hotelDTO) {
    	Location location = locationRepository.findById(hotelDTO.getLocationId()).orElse(null);
    	if(location != null) {
    		Hotel hotel = new Hotel();
    		hotel.setAddress(hotelDTO.getAddress());
    	
    		hotel.setName(hotelDTO.getName());

    		hotel.setStatus(hotelDTO.getStatus());
    		hotel.setLocationId(location);
            return ResponseEntity.ok(hotel);

    	}
    	return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
    	Location location = locationRepository.findById(hotelDTO.getLocationId()).orElse(null);
    	
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelDTO.getName());
                    hotel.setLocationId(location);
                    hotel.setAddress(hotelDTO.getAddress());

                    return ResponseEntity.ok(hotelRepository.save(hotel));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotelRepository.delete(hotel);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}