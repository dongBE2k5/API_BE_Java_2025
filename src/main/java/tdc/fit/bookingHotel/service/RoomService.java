package tdc.fit.bookingHotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Room;
import tdc.fit.bookingHotel.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Lấy tất cả các Room
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    // Lấy Room theo ID
    public ResponseEntity<?> getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        return ResponseEntity.ok(room);
    }

    // Tạo mới Room
    public ResponseEntity<?> createRoom(Room room) {
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    // Cập nhật Room
    public ResponseEntity<?> updateRoom(Long id, Room updatedRoom) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomTypeId(updatedRoom.getRoomTypeId());
        room.setStatus(updatedRoom.getStatus());
        room.setPrice(updatedRoom.getPrice());
        room.setImage(updatedRoom.getImage());
        room.setCapacity(updatedRoom.getCapacity());
        room.setDescription(updatedRoom.getDescription());
        room.setHotel(updatedRoom.getHotel());

        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    // Xóa Room
    public ResponseEntity<?> deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        roomRepository.delete(room);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
