package tdc.fit.bookingHotel.controllerAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.repository.LocationRepository;

@RestController
@RequestMapping("api/location")
public class LocationCotrollerAPI {
	
	   @Autowired
	    private LocationRepository locationRepository;

	    // Lấy tất cả các Location
	    @GetMapping
	    public ResponseEntity<?> getAllLocations() {
	        return ResponseEntity.ok(locationRepository.findAll());
	    }

	    // Lấy Location theo ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
	        Location location = locationRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
	        return ResponseEntity.ok(location);
	    }

	    // Tạo mới một Location
	    @PostMapping
	    public ResponseEntity<?> createLocation(@RequestBody Location location) {
	        Location savedLocation = locationRepository.save(location);
	        return ResponseEntity.ok(savedLocation);
	    }

	    // Xóa Location theo ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
	        Location location = locationRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
	        locationRepository.delete(location);
	        return ResponseEntity.ok("Location deleted successfully");
	    }

	
	
	
	
	
}
