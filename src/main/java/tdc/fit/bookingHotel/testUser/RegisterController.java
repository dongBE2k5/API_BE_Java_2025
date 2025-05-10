package tdc.fit.bookingHotel.testUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Trả về file register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username đã tồn tại");
            return "register";
        }
        System.out.println("Username received: " + user.getUsername());
//        System.out.printf("test " ,user.toString());
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);

        model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "login";
    }
}
