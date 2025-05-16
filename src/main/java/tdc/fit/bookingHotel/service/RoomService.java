package tdc.fit.bookingHotel.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Room;
import tdc.fit.bookingHotel.entity.RoomType;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.HotelierRepository;
import tdc.fit.bookingHotel.repository.RoomRepository;
import tdc.fit.bookingHotel.repository.RoomTypeRepository;
import tdc.fit.bookingHotel.repository.UserRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private HotelierRepository hotelierRepository;






    // Lấy tất cả các Room
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }
    
    public ResponseEntity<?> getRoomByHotel(Long id ) {
    	Hotel hotel = hotelRepository.findById(id) 
    			.orElseThrow(() -> new EntityNotFoundException("Hotel not found"));;
        return ResponseEntity.ok(roomRepository.findByHotel(hotel));
    }


    // Lấy Room theo ID
    public ResponseEntity<?> getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        return ResponseEntity.ok(room);
    }


    public ResponseEntity<?> createRoom(
            @RequestParam String roomNumber,
            @RequestParam Integer roomTypeId,
            @RequestParam BigDecimal price,
            @RequestParam int capacity,
            @RequestParam String description,
            @RequestParam Long hotelId,
            Authentication authentication
    ) {
        // Lấy thông tin người dùng từ Authentication
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Lấy thông tin hotelier từ user
        Hotelier hotelier = hotelierRepository.findByUserId(user);
        if (hotelier == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to create a room.");
        }

        // Kiểm tra dữ liệu đầu vào
        if (roomNumber == null || roomNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Room number cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Room price must be greater than zero");
        }

        if (capacity <= 0) {
            return ResponseEntity.badRequest().body("Room capacity must be greater than zero");
        }

        // Lấy RoomType
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        // Lấy Hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        // Kiểm tra xem hotel này có thuộc về hotelier hiện tại không
        if (!hotel.getHotelierId().getHotelierId().equals(hotelier.getHotelierId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only add rooms to your own hotel.");
        }

        // Tạo Room mới
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomTypeId(roomType);
        room.setPrice(price);
        room.setCapacity(capacity);
        room.setDescription(description);
        room.setHotel(hotel);
        room.setStatus("AVAILABLE");

        // Lưu vào DB
        Room savedRoom = roomRepository.save(room);

        return ResponseEntity.ok(savedRoom);
    }

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
    

    public ResponseEntity<?> updateRoom(
            @RequestParam Long id,
            @RequestParam String roomNumber,
            @RequestParam Integer roomTypeId,
            @RequestParam BigDecimal price,
            @RequestParam int capacity,
            @RequestParam String description,
            @RequestParam Long hotelId,
            Authentication authentication
    ) {
        // Lấy thông tin người dùng
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Hotelier hotelier = hotelierRepository.findByUserId(user);
        if (hotelier == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update rooms.");
        }

        // Kiểm tra tồn tại phòng
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Kiểm tra hotel và quyền sở hữu hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        if (!hotel.getHotelierId().getHotelierId().equals(hotelier.getHotelierId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only add rooms to your own hotel.");
        }

        // Kiểm tra roomType
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        // Kiểm tra đầu vào
        if (roomNumber == null || roomNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Room number cannot be empty");
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Room price must be greater than zero");
        }

        if (capacity <= 0) {
            return ResponseEntity.badRequest().body("Room capacity must be greater than zero");
        }

        // Cập nhật thông tin
        room.setRoomNumber(roomNumber);
        room.setRoomTypeId(roomType);
        room.setPrice(price);
        room.setCapacity(capacity);
        room.setDescription(description);
        room.setHotel(hotel);

        Room updatedRoom = roomRepository.save(room);
        return ResponseEntity.ok(updatedRoom);
    }

    // Xóa Room
    public ResponseEntity<?> deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        roomRepository.delete(room);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
