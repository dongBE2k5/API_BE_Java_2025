package tdc.fit.bookingHotel.controllerAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.repository.LocationRepository;

@RestController
@RequestMapping("api/location")
public class LocationCotrollerAPI {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@GetMapping
	public List<Location> getAllLocation(){
		return locationRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Location> getLocationByID(@PathVariable Integer id){
		return locationRepository.findById(id).map(ResponseEntity::ok)
				.orElseGet(()-> ResponseEntity.notFound().build());
		
		
	}
	
	
	
	
	
}
