package tdc.fit.bookingHotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.RoomType;
import tdc.fit.bookingHotel.repository.RoomTypeRepository;

@Service
public class RoomTypeService {
	 @Autowired
	    private RoomTypeRepository roomTypeRepository;

	    // Lấy tất cả RoomType
	    public ResponseEntity<?> getAllRoomTypes() {
	        return ResponseEntity.ok(roomTypeRepository.findAll());
	    }

	    // Lấy RoomType theo ID
	    public ResponseEntity<?> getRoomTypeById(Integer id) {
	        RoomType roomType = roomTypeRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));
	        return ResponseEntity.ok(roomType);
	    }

	    // Tạo mới RoomType
	    public ResponseEntity<?> createRoomType(RoomType roomType) {
	        RoomType savedRoomType = roomTypeRepository.save(roomType);
	        return ResponseEntity.ok(savedRoomType);
	    }

	    // Cập nhật RoomType
	    public ResponseEntity<?> updateRoomType(Integer id, RoomType roomTypeDetails) {
	        RoomType roomType = roomTypeRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

	        roomType.setName(roomTypeDetails.getName());
	        RoomType updatedRoomType = roomTypeRepository.save(roomType);
	        return ResponseEntity.ok(updatedRoomType);
	    }

	    // Xoá RoomType
	    public ResponseEntity<?> deleteRoomType(Integer id) {
	        RoomType roomType = roomTypeRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));
	        roomTypeRepository.delete(roomType);
	        return ResponseEntity.ok("RoomType deleted successfully");
	    }
}
