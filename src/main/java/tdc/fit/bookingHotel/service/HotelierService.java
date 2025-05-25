package tdc.fit.bookingHotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.repository.HotelierRepository;



@PreAuthorize("hasAuthority('ROLE_SUPERADMIN') or hasAuthority('ROLE_ADMIN') ")
@Service
public class HotelierService {

    @Autowired
    private HotelierRepository hotelierRepository;

    // Lấy tất cả Hoteliers

    public ResponseEntity<?> getAllHoteliers() {
        List<Hotelier> hoteliers = hotelierRepository.findAll();
        return ResponseEntity.ok(hoteliers);
    }

    // Lấy Hotelier theo ID
    
    public ResponseEntity<?> getHotelierById(Long id) {
    	Hotelier hotelier=hotelierRepository.findById(id)
            
                .orElseThrow(() -> new EntityNotFoundException("Hotelier not found"));
        return ResponseEntity.ok(hotelier) ;
    }

    // Tạo mới Hotelier
    public ResponseEntity<?> createHotelier(Hotelier hotelier) {
        Hotelier savedHotelier = hotelierRepository.save(hotelier);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotelier);
    }

    // Cập nhật Hotelier
    public ResponseEntity<?> updateHotelier(Long id, Hotelier updatedHotelier) {
        return hotelierRepository.findById(id).<ResponseEntity<?>>map(hotelier -> {
            hotelier.setName(updatedHotelier.getName());
            hotelier.setFullName(updatedHotelier.getFullName());
            hotelier.setPhone(updatedHotelier.getPhone());
            hotelier.setCccd(updatedHotelier.getCccd());
            hotelier.setEmail(updatedHotelier.getEmail());
           
            Hotelier saved = hotelierRepository.save(hotelier);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotelier not found"));
    }

    // Xoá Hotelier
    public ResponseEntity<?> deleteHotelier(Long id) {
        return hotelierRepository.findById(id).map(hotelier -> {
            hotelierRepository.delete(hotelier);
            return ResponseEntity.ok("Hotelier deleted successfully");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotelier not found"));
    }
}
