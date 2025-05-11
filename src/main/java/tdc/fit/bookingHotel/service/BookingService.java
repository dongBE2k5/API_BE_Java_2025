package tdc.fit.bookingHotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Booking;
import tdc.fit.bookingHotel.entity.Customer;
import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.entity.Room;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.BookingRepository;
import tdc.fit.bookingHotel.repository.CustomerRepository;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.HotelierRepository;
import tdc.fit.bookingHotel.repository.RoomRepository;
import tdc.fit.bookingHotel.repository.UserRepository;

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
    
    

    // Lấy tất cả các booking
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Tạo một booking mới
    public ResponseEntity<?> createBooking(Booking booking, Long roomId,Authentication authentication) {
        // Kiểm tra khách hàng và phòng hợp lệ
    	 String username = authentication.getName();
    	 User user = userRepository.findByUsername(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    	
        Customer customer = customerRepository.findById(user.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        
//        customer.getUserId().getUserId().equals(user.getUserId());
        	if(room.getStatus().equals("MAINTENANCE")) {
        	      return ResponseEntity.badRequest().body("Room is under MAINTENANCE");
        	}
        boolean isRoomAvailable= !bookingRepository.existsOverlappingBookings(roomId,booking.getCheckInDate() , booking.getCheckInDate());
        if (isRoomAvailable) {
        	
        	  room.setStatus("RESERVED");
        	  booking.setStatus("CHƯA NHẬN PHÒNG");
              // Gán thông tin khách hàng và phòng cho booking
              booking.setCustomer(customer);
              booking.setRoom(room);
              return ResponseEntity.ok(bookingRepository.save(booking));
        
        }
        
      return ResponseEntity.badRequest().body("Room is not available for the selected dates.");
    }

    
    public void deleteBooking(Integer bookingId) {
   
       bookingRepository.deleteById(bookingId);
   }
   
    
    // Xóa một booking
//    @PreAuthorize("hasPermission(#bookingId, 'delete')")
    public void deleteUserBooking(Integer bookingId,Authentication authentication) {
    	 String username = authentication.getName();
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	 Customer customer = customerRepository.findByUserId(user);
        bookingRepository.deleteByCustomerAndBookingId(customer,bookingId);
    }
    
    

    // Chỉnh sửa thông tin booking
//    @PreAuthorize("hasPermission(#bookingId, 'edit')")
//    public Booking editBooking(Integer bookingId, Booking updatedBooking) {
//        Booking existingBooking = bookingRepository.findById(bookingId)
//            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
//
//        existingBooking.setCheckInDate(updatedBooking.getCheckInDate());
//        existingBooking.setCheckOutDate(updatedBooking.getCheckOutDate());
//        existingBooking.setStatus(updatedBooking.getStatus());
//        return bookingRepository.save(existingBooking);
//    }

    
 // Lấy một booking theo id
    public ResponseEntity<?> getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return ResponseEntity.ok(booking);
    }

    // Lấy booking theo customer (user hiện tại)
    public ResponseEntity<?> getBookingByCustomer(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Customer customer = customerRepository.findByUserId(user);

        List<Booking> bookings = bookingRepository.findByCustomer(customer);
        return ResponseEntity.ok(bookings);
    }

    // Lấy booking theo hotelier (user hiện tại)
    public ResponseEntity<?> getBookingByRoom(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Hotelier hotelier = hotelierRepository.findByUserId(user);
        List<Hotel> hotels = hotelRepository.findByHotelierId(hotelier);
        List<Room> rooms = roomRepository.findByHotelIn(hotels);

        List<Booking> bookings = bookingRepository.findByRoomIn(rooms);
        return ResponseEntity.ok(bookings);
    }
    
    
 // Cập nhật trạng thái booking (check-in, check-out)
    public ResponseEntity<?> updateBookingStatus(Integer bookingId, String action) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        String currentStatus = booking.getStatus();

        switch (action.toLowerCase()) {
            case "checkin":
                if ("CHƯA NHẬN PHÒNG".equals(currentStatus)) {
                	
                    booking.setStatus("ĐÃ NHẬN PHÒNG");
                    booking.getRoom().setStatus("OCCUPIED");
                } else {
                    return ResponseEntity.badRequest().body("Không thể check-in ở trạng thái hiện tại: " + currentStatus);
                }
                break;

            case "checkout":
                if ("ĐÃ NHẬN PHÒNG".equals(currentStatus)) {
                    booking.setStatus("ĐÃ TRẢ PHÒNG");
                    booking.getRoom().setStatus("AVAILABLE");
                } else {
                    return ResponseEntity.badRequest().body("Không thể check-out ở trạng thái hiện tại: " + currentStatus);
                }
                break;

            default:
                return ResponseEntity.badRequest().body("Hành động không hợp lệ. Chỉ nhận 'checkin' hoặc 'checkout'");
        }

        bookingRepository.save(booking);
        return ResponseEntity.ok(booking);
    }

}



