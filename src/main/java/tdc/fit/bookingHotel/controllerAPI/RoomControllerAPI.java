package tdc.fit.bookingHotel.controllerAPI;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import tdc.fit.bookingHotel.entity.Room;
import tdc.fit.bookingHotel.repository.RoomRepository;
import tdc.fit.bookingHotel.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomControllerAPI {

    @Autowired
    private RoomService roomService;
    


    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }
    
    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> getRoomByHotel(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomByHotel(id));
    }

    @PostMapping
    public ResponseEntity<?> createRoom( @RequestParam String roomNumber,
            @RequestParam Integer roomTypeId,
            @RequestParam BigDecimal price,
            @RequestParam int capacity,
            @RequestParam String description,
            @RequestParam Long hotelId,
            Authentication authentication) {

        return ResponseEntity.ok(roomService.createRoom(roomNumber,roomTypeId,price,capacity,description,hotelId,authentication));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }
    
    
    
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
    	ResponseEntity.ok(roomService.deleteRoom(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(  @RequestParam Long id,
            @RequestParam String roomNumber,
            @RequestParam Integer roomTypeId,
            @RequestParam BigDecimal price,
            @RequestParam int capacity,
            @RequestParam String description,
            @RequestParam Long hotelId,
            Authentication authentication
            ) {
    	return ResponseEntity.ok(roomService.updateRoom(id,roomNumber,roomTypeId,price,capacity,description,hotelId,authentication));
    }

    
}
