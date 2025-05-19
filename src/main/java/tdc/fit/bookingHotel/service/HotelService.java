package tdc.fit.bookingHotel.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.HotelierRepository;
import tdc.fit.bookingHotel.repository.LocationRepository;
import tdc.fit.bookingHotel.repository.UserRepository;

@Service
public class HotelService {
	@Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelierRepository hotelierRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Lấy tất cả các khách sạn
//    @PreAuthorize("hasAuthority('ROLE_SUPERADMIN')")
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return ResponseEntity.ok(hotels);
    }

    // Lấy khách sạn theo ID
    public ResponseEntity<?> getHotelById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        return ResponseEntity.ok(hotel);
    }
    
    // lấy theo Hotelier
    public ResponseEntity<?> getHotelByHotelier(Authentication authentication) {
    	
   	 String username = authentication.getName();
	 User user = userRepository.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	 Hotelier hotelier = hotelierRepository.findByUserId(user);
	 
        List<Hotel> hotel = hotelRepository.findByHotelier(hotelier);
        if (hotel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found");
        }
               
        return ResponseEntity.ok(hotel);
    }
    
    // lấy theo Location
    public ResponseEntity<?> getHotelByLocation(Long locationId ) {
    	
    	Location location = locationRepository.findById(locationId)
    		    .orElseThrow(() -> new EntityNotFoundException("Location not found"));;
        List<Hotel> hotel = hotelRepository.findByLocation(location);
        if (hotel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found");
        }
               
        return ResponseEntity.ok(hotel);
    }


    // Tạo mới một khách sạn
//    public ResponseEntity<?> createHotel(Hotel hotel) {
//        // Kiểm tra xem location và hotelier có tồn tại không
//        if (locationRepository.existsById(hotel.getLocationId().getLocationId()) &&
//            hotelierRepository.existsById(hotel.getHotelierId().getHotelierId())) {
//
//            Hotel savedHotel = hotelRepository.save(hotel);
//            return ResponseEntity.ok(savedHotel);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Location or Hotelier not found");
//        }
//    }

    public ResponseEntity<Hotel> createHotel(@RequestParam("name") String name,
			@RequestParam("locationId") Long locationID, @RequestParam("address") String address,
			@RequestParam("status") String status, @RequestParam("hotelierId") Long hotelierId,
			@RequestParam("image") MultipartFile image) throws IOException {
		Location location = locationRepository.findById(locationID).orElse(null);
		Hotelier hotelier = hotelierRepository.findById(hotelierId).orElse(null);
		if (location != null && hotelier != null && image != null && !image.isEmpty()) {
			try {
				String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
				String uploadDir = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/static/uploads";
				Path filepath = Paths.get(uploadDir, filename);
				Files.createDirectories(filepath.getParent());
				image.transferTo(filepath.toFile());

				Hotel hotel = new Hotel();
				hotel.setName(name);
				hotel.setAddress(address);
				hotel.setStatus(status);
				hotel.setLocation(location);
				hotel.setHotelier(hotelier);
				hotel.setImage(filename); // Nếu bạn có trường 'image' trong entity

				hotelRepository.save(hotel); // Lưu vào DB

				return ResponseEntity.ok(hotel);

			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
		return ResponseEntity.notFound().build();
	}
    
    

    public ResponseEntity<Hotel> updateHotel(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("locationId") Long locationID,
            @RequestParam("address") String address,
            @RequestParam("status") String status,
            @RequestParam("hotelierId") Long hotelierId,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        // 1. Lấy các entity liên quan
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        Location location = locationRepository.findById(locationID).orElse(null);
        Hotelier hotelier = hotelierRepository.findById(hotelierId).orElse(null);

        // 2. Kiểm tra tồn tại
        if (hotel == null || location == null || hotelier == null) {
            return ResponseEntity.notFound().build();
        }

        // 3. Nếu có ảnh mới, xoá ảnh cũ và lưu ảnh mới
        if (image != null && !image.isEmpty()) {
            deleteUploadedFile(hotel.getImage());
            try {
                String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                String uploadDir = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/static/uploads";
                Path filepath = Paths.get(uploadDir, filename);
                Files.createDirectories(filepath.getParent());
                image.transferTo(filepath.toFile());
                hotel.setImage(filename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 4. Cập nhật thông tin khách sạn
        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setStatus(status);
        hotel.setLocation(location);
        hotel.setHotelier(hotelier);

        // 5. Lưu và trả về
        hotelRepository.save(hotel);
        return ResponseEntity.ok(hotel);
    }


    public static void deleteUploadedFile(String filename) {
        if (filename == null || filename.isEmpty()) return;

        try {
            String uploadDir = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/static/uploads";
            Path filePath = Paths.get(uploadDir, filename);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            System.err.println("Could not delete file: " + filename);
            e.printStackTrace();
        }
        }


    // Xoá khách sạn theo ID
    public ResponseEntity<?> deleteHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        hotelRepository.delete(hotel);
        return ResponseEntity.ok("Hotel deleted successfully");
    }
}
