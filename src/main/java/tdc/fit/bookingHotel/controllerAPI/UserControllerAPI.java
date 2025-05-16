package tdc.fit.bookingHotel.controllerAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
	
    
    
    @Autowired
    private AuthenticationManager authenticationManager;

	@GetMapping
	public List<User> all() {
		return repo.findAll();
	}
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Xác thực username/password
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            // Thành công → trả username và roles cho Android lưu
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("username", userDetails.getUsername());
            response.put("roles", roles);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai username hoặc password");
        }
    }
    
    

		
		@PostMapping("/register")
		public User register(@RequestPart String username, @RequestPart String password) {
			 User user = new User();
			    user.setUsername(username);
			    user.setPassword(passwordEncoder.encode(user.getPassword()));
			    user.setRoles("ROLE_USER");
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
