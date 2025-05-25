package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.entity.DTO.HotelDTO;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.LocationRepository;
import tdc.fit.bookingHotel.service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelControllerAPI {

    @Autowired
    private HotelService hotelService;
    
  
    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id)) ;
    }
    
    @GetMapping("/location/{id}")
    public ResponseEntity<?> getHotelByLocation(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelByLocation(id)) ;
    }
    
    @GetMapping("/hotelier/{id}")
    public ResponseEntity<?> getHotelHotelier(@PathVariable Long id ) {
        return ResponseEntity.ok(hotelService.getHotelByHotelier(id)) ;
    }
    @GetMapping("/hotelier")
    public ResponseEntity<?> getHotelHotelier(Authentication authentication) {
        return ResponseEntity.ok(hotelService.getHotelByHotelier(authentication)) ;
    }

    @PostMapping( consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createHotel(@RequestParam("name") String name,
			@RequestParam("locationId") Long locationID, @RequestParam("address") String address,
			@RequestParam("status") String status, @RequestParam("hotelierId") Long hotelierId,
			@RequestParam("image") MultipartFile image) throws IOException {
    
    	return ResponseEntity.ok(hotelService.createHotel(name,locationID,address,status,hotelierId,image));
    }

    @PutMapping(value="/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateHotel(@PathVariable Long id,
	        @RequestParam("name") String name,
	        @RequestParam("locationId") Long locationID,
	        @RequestParam("address") String address,
	        @RequestParam("status") String status,
	        @RequestParam("hotelierId") Long hotelierId,
	        @RequestPart("image") MultipartFile image) {
    	return ResponseEntity.ok(hotelService.updateHotel(id, name,locationID,address,status,hotelierId,image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.deleteHotel(id));
    }
}