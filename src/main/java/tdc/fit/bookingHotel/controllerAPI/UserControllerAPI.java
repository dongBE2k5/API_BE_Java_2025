package tdc.fit.bookingHotel.controllerAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tdc.fit.bookingHotel.Util.JwtUtil;
import tdc.fit.bookingHotel.entity.Customer;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.entity.Response.UserReponse;
import tdc.fit.bookingHotel.repository.CustomerRepository;
import tdc.fit.bookingHotel.repository.UserRepository;


@RestController
@RequestMapping("api/users")
public class UserControllerAPI {
	@Autowired
	private UserRepository repo;
	
	@GetMapping
	public List<User> all() {
		return repo.findAll();
	}
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return repo.save(user);
	}
	
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
