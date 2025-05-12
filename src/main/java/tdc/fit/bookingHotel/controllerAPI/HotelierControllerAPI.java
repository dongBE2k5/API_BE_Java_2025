package tdc.fit.bookingHotel.controllerAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tdc.fit.bookingHotel.entity.Hotelier;
import tdc.fit.bookingHotel.service.HotelierService;

@RestController
@RequestMapping("/api/hoteliers")
public class HotelierControllerAPI {
	@Autowired
    private HotelierService hotelierService;

    @GetMapping
    public ResponseEntity<?> getAllHoteliers() {
        return hotelierService.getAllHoteliers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelierById(@PathVariable Long id) {
        return hotelierService.getHotelierById(id);
    }

    @PostMapping
    public ResponseEntity<?> createHotelier(@RequestBody Hotelier hotelier) {
        return hotelierService.createHotelier(hotelier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotelier(@PathVariable Long id, @RequestBody Hotelier updatedHotelier) {
        return hotelierService.updateHotelier(id, updatedHotelier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotelier(@PathVariable Long id) {
        return hotelierService.deleteHotelier(id);
    }
}
