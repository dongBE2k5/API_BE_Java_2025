package tdc.fit.bookingHotel.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

	public ResponseEntity<?> getRoomByHotel(Long id) {
		Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
		;
		return ResponseEntity.ok(roomRepository.findByHotel(hotel));
	}

	// Lấy Room theo ID
	public ResponseEntity<?> getRoomById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));
		return ResponseEntity.ok(room);
	}

	public ResponseEntity<?> createRoom(String roomNumber, Integer roomTypeId, BigDecimal price, int capacity,
			String description, Long hotelId, MultipartFile image, Authentication authentication) {
		// 1. Xác thực và lấy Hotelier từ người dùng hiện tại
		String username = authentication.getName();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		Hotelier hotelier = hotelierRepository.findByUserId(user);
		if (hotelier == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền tạo phòng.");
		}

		// 2. Validate các tham số đầu vào
		if (roomNumber == null || roomNumber.isBlank()) {
			return ResponseEntity.badRequest().body("Room number is required.");
		}
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
			return ResponseEntity.badRequest().body("Price must be greater than 0.");
		}
		if (capacity <= 0) {
			return ResponseEntity.badRequest().body("Capacity must be greater than 0.");
		}

		// 3. Lấy RoomType & Hotel
		RoomType roomType = roomTypeRepository.findById(roomTypeId)
				.orElseThrow(() -> new EntityNotFoundException("Room type not found."));

		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new EntityNotFoundException("Hotel not found."));

		if (!hotel.getHotelier().getHotelierId().equals(hotelier.getHotelierId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn chỉ có thể tạo phòng cho khách sạn của bạn.");
		}

		// 4. Xử lý file ảnh
		String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
		Path uploadPath = Paths.get("src/main/resources/static/uploads", filename).toAbsolutePath();
		try {
			Files.createDirectories(uploadPath.getParent());
			image.transferTo(uploadPath.toFile());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu ảnh: " + e.getMessage());
		}

		// 5. Tạo đối tượng Room và lưu
		Room room = new Room();
		room.setRoomNumber(roomNumber);
		room.setRoomType(roomType);
		room.setPrice(price);
		room.setCapacity(capacity);
		room.setDescription(description);
		room.setHotel(hotel);
		room.setImage(filename);
		room.setStatus("AVAILABLE");

		return ResponseEntity.ok(roomRepository.save(room));
	}

	public ResponseEntity<?> createRoom(Room room) {
		Room savedRoom = roomRepository.save(room);
		return ResponseEntity.ok(savedRoom);
	}

	// Cập nhật Room
	public ResponseEntity<?> updateRoom(Long id, Room updatedRoom) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));

		room.setRoomNumber(updatedRoom.getRoomNumber());
		room.setRoomType(updatedRoom.getRoomType());
		room.setStatus(updatedRoom.getStatus());
		room.setPrice(updatedRoom.getPrice());
		room.setImage(updatedRoom.getImage());
		room.setCapacity(updatedRoom.getCapacity());
		room.setDescription(updatedRoom.getDescription());
		room.setHotel(updatedRoom.getHotel());

		Room savedRoom = roomRepository.save(room);
		return ResponseEntity.ok(savedRoom);
	}

	public ResponseEntity<?> updateRoom(Long id, String roomNumber, Integer roomTypeId, BigDecimal price, int capacity,
			String description, Long hotelId, String status, @RequestParam(required = false) MultipartFile image,
			Authentication authentication) {
		// 1. Xác thực người dùng
		String username = authentication.getName();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Hotelier hotelier = hotelierRepository.findByUserId(user);
		if (hotelier == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền cập nhật phòng.");
		}

		// 2. Validate đầu vào
		if (roomNumber == null || roomNumber.isBlank()) {
			return ResponseEntity.badRequest().body("Room number is required.");
		}
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
			return ResponseEntity.badRequest().body("Price must be greater than 0.");
		}
		if (capacity <= 0) {
			return ResponseEntity.badRequest().body("Capacity must be greater than 0.");
		}

		// 3. Lấy Room
		Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found."));

		// 4. Kiểm tra hotel sở hữu
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new EntityNotFoundException("Hotel not found."));
		if (!hotel.getHotelier().getHotelierId().equals(hotelier.getHotelierId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Bạn chỉ được phép sửa phòng thuộc khách sạn của mình.");
		}

		// 5. Lấy roomType
		RoomType roomType = roomTypeRepository.findById(roomTypeId)
				.orElseThrow(() -> new EntityNotFoundException("Room type not found."));

		// 6. Nếu có ảnh mới thì xóa ảnh cũ và lưu ảnh mới
		if (image != null && !image.isEmpty()) {
			try {
				// Xoá ảnh cũ (nếu tồn tại)
				deleteUploadedFile(room.getImage());

				// Lưu ảnh mới
				String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
				String uploadDir = Paths.get("").toAbsolutePath() + "/src/main/resources/static/uploads";
				Path filepath = Paths.get(uploadDir, filename);
				Files.createDirectories(filepath.getParent());
				image.transferTo(filepath.toFile());

				room.setImage(filename);
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image.");
			}
		}

		// 7. Cập nhật thông tin phòng
		room.setRoomNumber(roomNumber);
		room.setRoomType(roomType);
		room.setPrice(price);
		room.setCapacity(capacity);
		room.setDescription(description);
		room.setHotel(hotel);
		room.setStatus(status);

		return ResponseEntity.ok(roomRepository.save(room));
	}

	public static void deleteUploadedFile(String filename) {
		if (filename == null || filename.isEmpty())
			return;

		try {
			String uploadDir = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/static/uploads";
			Path filePath = Paths.get(uploadDir, filename);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			System.err.println("Could not delete file: " + filename);
			e.printStackTrace();
		}
	}

	// Xóa Room
	public ResponseEntity<?> deleteRoom(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found"));
		roomRepository.delete(room);
		return ResponseEntity.ok("Room deleted successfully");
	}
}
