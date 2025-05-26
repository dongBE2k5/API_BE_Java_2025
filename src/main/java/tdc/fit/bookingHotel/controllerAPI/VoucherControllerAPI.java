package tdc.fit.bookingHotel.controllerAPI;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tdc.fit.bookingHotel.entity.Voucher;
import tdc.fit.bookingHotel.service.VoucherService;

@RestController
@RequestMapping("/api/voucher")
public class VoucherControllerAPI {
    @Autowired
    private VoucherService voucherService;

    // Lấy tất cả RoomType
    @GetMapping
    public ResponseEntity<?> all() {
        return voucherService.all();
    }

    // Lấy RoomType theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        System.out.println("Voucher id: " + id);
        return voucherService.find(id);
    }

    @GetMapping("/findByCode/{code}")
    public ResponseEntity<?> findVoucherByCode(@PathVariable String code)  {
        System.out.println("Voucher code: " + code);
        return ResponseEntity.ok(voucherService.findByCode(code));
    }



    // Tạo mới RoomType
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@RequestBody Voucher voucher) {
        System.out.println("Voucher Code: " + voucher.getCode());
        return ResponseEntity.ok(voucherService.create(voucher));
    }

    // Cập nhật RoomType
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
        return voucherService.update(id, voucher);
    }

    // Xoá RoomType
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Long id) {
        return voucherService.delete(id);
    }
}
