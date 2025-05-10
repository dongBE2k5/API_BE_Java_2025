package tdc.fit.bookingHotel.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tdc.fit.bookingHotel.entity.User;
import tdc.fit.bookingHotel.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Hàm Spring Security gọi khi người dùng login → tải user từ DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong DB theo username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

        // Chuyển chuỗi roles thành list GrantedAuthority
        List<GrantedAuthority> authorities = Arrays.stream(user.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        // Trả về đối tượng UserDetails cho Spring Security kiểm tra
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), authorities
        );
    }
}
