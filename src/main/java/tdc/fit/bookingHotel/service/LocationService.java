package tdc.fit.bookingHotel.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

 // Lấy tất cả các Location
    public ResponseEntity<?> getAllLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    // Lấy Location theo ID
    public ResponseEntity<?> getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        return ResponseEntity.ok(location);
    }

    // Tạo mới một Location
    public ResponseEntity<?> createLocation(Location location) {
        Location savedLocation = locationRepository.save(location);
        return ResponseEntity.ok(savedLocation);
    }

    // Xóa Location theo ID
    public ResponseEntity<?> deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
        locationRepository.delete(location);
        return ResponseEntity.ok("Location deleted successfully");
    }
}

