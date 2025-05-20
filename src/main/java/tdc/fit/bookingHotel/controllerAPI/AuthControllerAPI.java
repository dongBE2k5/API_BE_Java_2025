package tdc.fit.bookingHotel.controllerAPI;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tdc.fit.bookingHotel.Util.JwtUtil;
import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerAPI {
	
	   @Autowired
	    private AuthenticationManager authenticationManager;
	   
	   @Autowired
	   private UserRepository userRepository;

	   @Autowired
	   private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(username, password)
	        );
	        User newUser = userRepository.findByUsername(username)
	        		.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	   
	        String jwt = jwtUtil.generateToken(username);
	        
	        Map<String, String> response = new HashMap<>();
	        response.put("token", jwt);
	        response.put("username", username);
	        response.put("id",newUser.getUserId().toString());
	        return ResponseEntity.ok(response);
	    }
	    
	    @PostMapping("/register")
	    public ResponseEntity<?> register(
	            @RequestParam String username,
	            @RequestParam String password
	         
	    ) {
	        // Kiểm tra trùng username
	        if (userRepository.existsByUsername(username)) {
	            return ResponseEntity.badRequest().body("Username already exists");
	        }

	        // Tạo user mới
	        User newUser = new User();
	        newUser.setUsername(username);
	        newUser.setPassword(passwordEncoder.encode(password)); // mã hoá mật khẩu
	     
	        newUser.setRoles("ROLE_USER"); // hoặc "HOTELIER" tuỳ theo hệ thống của bạn

	        userRepository.save(newUser);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Registration successful");
	        response.put("username", username);

	        return ResponseEntity.ok(response);
	    }


}
