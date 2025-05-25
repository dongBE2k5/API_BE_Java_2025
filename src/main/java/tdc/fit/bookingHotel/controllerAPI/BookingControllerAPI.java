package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Booking;
import tdc.fit.bookingHotel.entity.DTO.BookingDTO;
import tdc.fit.bookingHotel.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingControllerAPI {

    @Autowired
    private BookingService bookingService;

    // Lấy tất cả bookings
    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Lấy booking theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // Lấy booking theo user (customer)
    @GetMapping("/user")
    public ResponseEntity<?> getBookingByCustomer(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getBookingByCustomer(authentication));
    }

    // Lấy booking theo hotelier (quản lý khách sạn)
    @GetMapping("/hotelier")
    public ResponseEntity<?> getBookingByRoom(Authentication authentication) {
        return ResponseEntity.ok(bookingService.getBookingByRoom(authentication));
    }

    // Cập nhật trạng thái booking (checkin/checkout)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Integer id, @RequestParam String action) {
        return bookingService.updateBookingStatus(id, action);
    }

    // Tạo booking mới
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking, Authentication authentication) {
        System.out.println(authentication.getName());
        if (booking.getRoom() == null || booking.getRoom().getRoomId() == null) {
            return ResponseEntity.badRequest().body("Room ID must be provided in the booking data.");
        }
        return bookingService.createBooking(booking, booking.getRoom().getRoomId(), authentication);
    }

    @PostMapping("/booking-new")
    public ResponseEntity<?> createBookingNew(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBookingNew(bookingDTO)) ;

    }

    @GetMapping("/customerId/{id}")
    public ResponseEntity<?> getBookingByCustomerID(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingByCustomerID(id));
    }

    // Xóa booking (dành cho admin hoặc hệ thống)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id) {
        return bookingService.deleteBooking(id);
    }

    // Xóa booking thuộc về user hiện tại
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserBooking(@PathVariable Integer id, Authentication authentication) {
        return bookingService.deleteUserBooking(id, authentication);
    }
}
