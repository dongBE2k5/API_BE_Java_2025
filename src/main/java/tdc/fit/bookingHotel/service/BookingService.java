package tdc.fit.bookingHotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.*;
import tdc.fit.bookingHotel.entity.DTO.BookingDTO;
import tdc.fit.bookingHotel.repository.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelierRepository hotelierRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }

    public ResponseEntity<?> createBooking(Booking booking, Long roomId, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Customer customer = customerRepository.findById(user.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new EntityNotFoundException("Room not found"));

            if ("MAINTENANCE".equals(room.getStatus())) {
                return ResponseEntity.badRequest().body("Room is under MAINTENANCE");
            }

            boolean isRoomAvailable = !bookingRepository.existsOverlappingBookings(
                    roomId, booking.getCheckInDate(), booking.getCheckOutDate());

            if (!isRoomAvailable) {
                return ResponseEntity.badRequest().body("Room is not available for the selected dates.");
            }

            room.setStatus("RESERVED");
            booking.setStatus("CHƯA NHẬN PHÒNG");
            booking.setCustomer(customer);
            booking.setRoom(room);

            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error creating booking: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> createBookingNew(BookingDTO bookingDTO) {
        try {
            System.out.println(bookingDTO.toString());

            Room room = roomRepository.findById(bookingDTO.getRoomId())
                    .orElseThrow(() -> new EntityNotFoundException("Room not found"));
            Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

            if ("MAINTENANCE".equals(room.getStatus())) {
                return ResponseEntity.badRequest().body("Room is under MAINTENANCE");
            }
            if (bookingDTO.getCheckinDate().isAfter(bookingDTO.getCheckoutDate())) {
                return ResponseEntity.badRequest().body("Check-in date must be before or equal to check-out date.");
            }
            boolean isRoomAvailable = !bookingRepository.existsOverlappingBookings(
                    bookingDTO.getRoomId(), bookingDTO.getCheckinDate(), bookingDTO.getCheckoutDate());

            if (!isRoomAvailable) {
                return ResponseEntity.badRequest().body("Room is not available for the selected dates.");
            }

            Booking booking = new Booking();
            room.setStatus("RESERVED");
            booking.setStatus("CHƯA NHẬN PHÒNG");
            booking.setRoom(room);
            booking.setCustomer(customer);
            booking.setCheckInDate(bookingDTO.getCheckinDate());
            booking.setCheckOutDate(bookingDTO.getCheckoutDate());
            System.out.println("Checkkout" + booking.getCheckOutDate());
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.ok(savedBooking);

        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Error creating booking: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> deleteBooking(Integer bookingId) {
        try {
            bookingRepository.deleteById(bookingId);
            return ResponseEntity.ok("Booking deleted successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(404).body("Booking not found or couldn't be deleted.");
        }
    }

    public ResponseEntity<?> deleteUserBooking(Integer bookingId, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Customer customer = customerRepository.findByUserId(user);
            bookingRepository.deleteByCustomerAndBookingId(customer, bookingId);

            return ResponseEntity.ok("Booking deleted successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(404).body("Error deleting booking: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> getBookingById(Integer bookingId) {
        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
            return ResponseEntity.ok(booking);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> getBookingByCustomerID(Long id) {
        List<Booking> bookings = bookingRepository.findByCustomer_CustomerId(id);
        return ResponseEntity.ok(bookings);
    }

    public ResponseEntity<?> getBookingByCustomer(Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Customer customer = customerRepository.findByUserId(user);
            List<Booking> bookings = bookingRepository.findByCustomer(customer);

            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body("Could not retrieve bookings: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> getBookingByRoom(Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Hotelier hotelier = hotelierRepository.findByUserId(user);
            List<Hotel> hotels = hotelRepository.findByHotelier(hotelier);
            List<Room> rooms = roomRepository.findByHotelIn(hotels);
            List<Booking> bookings = bookingRepository.findByRoomIn(rooms);

            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error retrieving bookings: " + ex.getMessage());
        }
    }

    public ResponseEntity<?> updateBookingStatus(Integer bookingId, String action) {
        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

            String currentStatus = booking.getStatus();

            switch (action.toLowerCase()) {
                case "checkin":
                    if ("CHƯA NHẬN PHÒNG".equals(currentStatus)) {
                        booking.setStatus("ĐÃ NHẬN PHÒNG");
                        booking.getRoom().setStatus("OCCUPIED");
                    } else {
                        return ResponseEntity.badRequest()
                                .body("Không thể check-in ở trạng thái hiện tại: " + currentStatus);
                    }
                    break;

                case "checkout":
                    if ("ĐÃ NHẬN PHÒNG".equals(currentStatus)) {
                        booking.setStatus("ĐÃ TRẢ PHÒNG");
                        booking.getRoom().setStatus("AVAILABLE");
                    } else {
                        return ResponseEntity.badRequest()
                                .body("Không thể check-out ở trạng thái hiện tại: " + currentStatus);
                    }
                    break;

                default:
                    return ResponseEntity.badRequest()
                            .body("Hành động không hợp lệ. Chỉ nhận 'checkin' hoặc 'checkout'");
            }

            bookingRepository.save(booking);
            return ResponseEntity.ok(booking);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Lỗi khi cập nhật trạng thái: " + ex.getMessage());
        }
    }
}
