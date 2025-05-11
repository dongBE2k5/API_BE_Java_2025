package tdc.fit.bookingHotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Customer;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.CustomerRepository;
import tdc.fit.bookingHotel.repository.UserRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Lấy tất cả các customer
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy customer theo id
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    // Tạo một customer mới
    public  ResponseEntity<?> createCustomer(Customer customer ,Authentication authentication) {
    	// Lấy username từ Authentication
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Gán User cho Customer (không lấy từ client nữa)
        customer.setUserId(user);

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    // Xóa một customer
//    @PreAuthorize("hasPermission(#customerId, 'delete')")
//    public void deleteCustomer(Long customerId) {
//        customerRepository.deleteById(customerId);
//    }

    // Chỉnh sửa thông tin customer
//    @PreAuthorize("hasPermission(#customerId, 'edit')")
    public ResponseEntity<?> editCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        existingCustomer.setFullname(updatedCustomer.getFullname());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setCccd(updatedCustomer.getCccd());
        return ResponseEntity.ok(customerRepository.save(existingCustomer));
    }
}

