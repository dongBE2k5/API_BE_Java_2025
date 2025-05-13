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
import tdc.fit.bookingHotel.service.LocationService;

@RestController
@RequestMapping("api/location")
public class LocationCotrollerAPI {
	
	   @Autowired
	    private LocationService locationService;

	    // Lấy tất cả các Location
	    @GetMapping
	    public ResponseEntity<?> getAllLocations() {
	        return ResponseEntity.ok(locationService.getAllLocations());
	    }

	    // Lấy Location theo ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
	      
	        return ResponseEntity.ok(locationService.getLocationById(id));
	    }

	    // Tạo mới một Location
	    @PostMapping
	    public ResponseEntity<?> createLocation(@RequestBody Location location) {
	    
	        return ResponseEntity.ok(locationService.createLocation(location));
	    }

	    // Xóa Location theo ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
	       
	        return ResponseEntity.ok(locationService.deleteLocation(id));
	    }

	
	
	
	
	
}
