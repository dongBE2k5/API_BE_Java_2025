package tdc.fit.bookingHotel.controllerAPI;

import tdc.fit.bookingHotel.entity.Booking;
import tdc.fit.bookingHotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingControllerAPI {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(
                booking.getRoom().getRoomId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
        if (!overlappingBookings.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

//    @PostMapping
//    public Booking createBooking(@RequestBody Booking booking) {
//        // TODO: Thêm logic kiểm tra phòng trống
//        return bookingRepository.save(booking);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @RequestBody Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setCustomer(bookingDetails.getCustomer());
                    booking.setRoom(bookingDetails.getRoom());
                    booking.setCheckInDate(bookingDetails.getCheckInDate());
                    booking.setCheckOutDate(bookingDetails.getCheckOutDate());
                    booking.setStatus(bookingDetails.getStatus());
                    return ResponseEntity.ok(bookingRepository.save(booking));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    bookingRepository.delete(booking);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}