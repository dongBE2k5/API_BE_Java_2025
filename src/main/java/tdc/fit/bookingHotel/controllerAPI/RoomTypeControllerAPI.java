package tdc.fit.bookingHotel.controllerAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tdc.fit.bookingHotel.entity.RoomType;
import tdc.fit.bookingHotel.service.RoomTypeService;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeControllerAPI {
	 @Autowired
	    private RoomTypeService roomTypeService;

	    // Lấy tất cả RoomType
	    @GetMapping
	    public ResponseEntity<?> getAllRoomTypes() {
	        return roomTypeService.getAllRoomTypes();
	    }

	    // Lấy RoomType theo ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getRoomTypeById(@PathVariable Integer id) {
	        return roomTypeService.getRoomTypeById(id);
	    }

	    // Tạo mới RoomType
	    @PostMapping
	    public ResponseEntity<?> createRoomType(@RequestBody RoomType roomType) {
	        return roomTypeService.createRoomType(roomType);
	    }

	    // Cập nhật RoomType
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateRoomType(@PathVariable Integer id, @RequestBody RoomType roomType) {
	        return roomTypeService.updateRoomType(id, roomType);
	    }

	    // Xoá RoomType
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteRoomType(@PathVariable Integer id) {
	        return roomTypeService.deleteRoomType(id);
	    }
}
