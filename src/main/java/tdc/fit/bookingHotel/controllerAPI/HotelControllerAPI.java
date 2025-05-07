package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelControllerAPI {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Integer id) {
        return hotelRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Integer id, @RequestBody Hotel hotelDetails) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelDetails.getName());
                    hotel.setLocation(hotelDetails.getLocation());
                    hotel.setAddress(hotelDetails.getAddress());
                    hotel.setPhone(hotelDetails.getPhone());
                    hotel.setEmail(hotelDetails.getEmail());
                    return ResponseEntity.ok(hotelRepository.save(hotel));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Integer id) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotelRepository.delete(hotel);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
