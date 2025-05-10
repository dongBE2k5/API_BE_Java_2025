package tdc.fit.bookingHotel.controllerAPI;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tdc.fit.bookingHotel.Util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerAPI {
	
	   @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(username, password)
	        );

	        String jwt = jwtUtil.generateToken(username);

	        Map<String, String> response = new HashMap<>();
	        response.put("token", jwt);
	        return ResponseEntity.ok(response);
	    }

}
