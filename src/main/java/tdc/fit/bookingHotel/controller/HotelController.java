package tdc.fit.bookingHotel.controller;


import tdc.fit.bookingHotel.entity.Hotel;
import tdc.fit.bookingHotel.entity.Location;
import tdc.fit.bookingHotel.repository.HotelRepository;
import tdc.fit.bookingHotel.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping
    public String listHotels(Model model) {
        List<Hotel> hotels = hotelRepository.findAll();
        model.addAttribute("hotels", hotels);
        return "hotels";
    }

    @GetMapping("/new")
    public String newHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        model.addAttribute("locations", locationRepository.findAll());
        return "hotel-form";
    }

    @PostMapping
    public String createHotel(@RequestParam String name, @RequestParam Long locationId, @RequestParam String address, @RequestParam String phone, @RequestParam String email, RedirectAttributes redirectAttributes) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + locationId));
        hotel.setLocationId(location);
        hotel.setAddress(address);
    
        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("message", "Khách sạn được tạo thành công");
        return "redirect:/hotels";
    }

    @GetMapping("/{id}/edit")
    public String editHotelForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        model.addAttribute("hotel", hotel);
        model.addAttribute("locations", locationRepository.findAll());
        return "hotel-form";
    }

    @PostMapping("/{id}")
    public String updateHotel(@PathVariable Long id, @RequestParam String name, @RequestParam Long locationId, @RequestParam String address, @RequestParam String phone, @RequestParam String email, RedirectAttributes redirectAttributes) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        hotel.setName(name);
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new IllegalArgumentException("Invalid location Id:" + locationId));
        hotel.setLocationId(location);
        hotel.setAddress(address);

        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("message", "Khách sạn được cập nhật thành công");
        return "redirect:/hotels";
    }

    @PostMapping("/{id}/delete")
    public String deleteHotel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        hotelRepository.delete(hotel);
        redirectAttributes.addFlashAttribute("message", "Khách sạn được xóa thành công");
        return "redirect:/hotels";
    }
}