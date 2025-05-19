package tdc.fit.bookingHotel.controllerAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tdc.fit.bookingHotel.Util.JwtUtil;
import tdc.fit.bookingHotel.entity.Customer;
import tdc.fit.bookingHotel.entity.DTO.CustomerDTO;
import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.entity.DTO.HotelDTO;
import tdc.fit.bookingHotel.entity.Response.UserReponse;
import tdc.fit.bookingHotel.repository.CustomerRepository;
import tdc.fit.bookingHotel.repository.UserRepository;
import tdc.fit.bookingHotel.service.CustomerService;

@RestController
@RequestMapping("api/customers")
public class CustomerControllerAPI {
	@Autowired
	private CustomerService customerService;

	@GetMapping
	public List<Customer> all() {
		return customerService.getAllCustomers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.getCustomerById(id)) ;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Customer customer,Authentication authentication) {

		return customerService.createCustomer(customer,authentication);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		return customerService.editCustomer(id, customer);
	}

	@PutMapping("/customer-new/{id}")
	public ResponseEntity<?> updateCustomerNew(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
		return ResponseEntity.ok(customerService.editCustomerNew(id, customerDTO));
	}

//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
//	        return hotelRepository.findById(id)
//	                .map(hotel -> {
//	                    hotelRepository.delete(hotel);
//	                    return ResponseEntity.ok().<Void>build();
//	                })
//	                .orElseGet(() -> ResponseEntity.notFound().build());
//	    }

//	@PostMapping("/register")
//	public Customer register(@RequestBody User user) {
//		return repo.save(user);
//	}

//	@PostMapping("/login")
//    public UserReponse login(@RequestBody Customer customer) {
//		User userDB = repo.findByUsername(User.getUsername());
//        if (userDB.getUsername() != null && userDB.getPassword().equals(userDB.getPassword())) {
//
//        	String token = JwtUtil.generateToken(userDB.getUsername(), userDB.getId());
//        	Long userId = JwtUtil.getUserId(token);
//        	System.out.println("Userid : " + userId);
//        	UserReponse userReponse = new UserReponse(token, userId, "200");
//
//            return userReponse;
//        }
//        return null;
//    }

}
