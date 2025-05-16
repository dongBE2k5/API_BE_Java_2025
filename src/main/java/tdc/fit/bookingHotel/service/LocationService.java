package tdc.fit.bookingHotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.repository.LocationRepository;

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

    // Cập nhật Location theo ID
    public ResponseEntity<?> updateLocation(Long id, Location updatedLocation) {
        // Kiểm tra xem Location có tồn tại hay không
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        // Cập nhật thông tin Location
        existingLocation.setName(updatedLocation.getName());  // Cập nhật tên
        existingLocation.setDescription(updatedLocation.getDescription());  // Cập nhật mô tả
         // Cập nhật địa chỉ

        // Lưu Location đã được cập nhật
        Location savedLocation = locationRepository.save(existingLocation);
        return ResponseEntity.ok(savedLocation);
    }
}
