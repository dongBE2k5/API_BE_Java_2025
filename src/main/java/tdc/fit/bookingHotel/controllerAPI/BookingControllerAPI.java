package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Booking;

import tdc.fit.bookingHotel.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingControllerAPI {

    @Autowired
    private  BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id);
    }
    

    @GetMapping("/user")
    public ResponseEntity<?>  getBookingByCustomer(Authentication authentication) {
    	
        return bookingService.getBookingByCustomer(authentication);
    }
    
    @GetMapping("/hotelier")
    public ResponseEntity<?>  getBookingByRoom(Authentication authentication) {
    	
        return bookingService.getBookingByRoom(authentication);
    }
    
    
    @PutMapping("/bookings/{id}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Integer id, @RequestParam String action) {
        return bookingService.updateBookingStatus(id, action);
    }
    
    
    @PostMapping
    public ResponseEntity<?>  createBooking(@RequestBody Booking booking,Authentication authentication) {
       
        return bookingService.createBooking(booking, booking.getRoom().getRoomId(),authentication);
    }

//    @PostMapping
//    public Booking createBooking(@RequestBody Booking booking) {
//        // TODO: Thêm logic kiểm tra phòng trống
//        return bookingRepository.save(booking);
//    }

//    @PutMapping("/{id}")
//    public Booking updateBooking(@PathVariable Integer id, @RequestBody Booking bookingDetails) {
//        return bookingService.editBooking(id, bookingDetails);
//    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Integer id) {
         bookingService.deleteBooking(id);
    }
    
    
    @DeleteMapping("/user/{id}")
    public void deleteUserBooking(@PathVariable Integer id ,Authentication authentication) {
         bookingService.deleteUserBooking(id,authentication);
    }
}