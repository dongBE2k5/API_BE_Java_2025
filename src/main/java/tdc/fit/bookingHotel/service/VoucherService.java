package tdc.fit.bookingHotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tdc.fit.bookingHotel.entity.Voucher;
import tdc.fit.bookingHotel.repository.VoucherRepository;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    // Lấy tất cả RoomType
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(voucherRepository.findAll());
    }

    // Lấy RoomType theo ID
    public ResponseEntity<?> find(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));
        return ResponseEntity.ok(voucher);
    }


    public ResponseEntity<?> create(Voucher voucher) {
        System.out.println("Voucher Code: " + voucher.getCode());
        Voucher voucherSave = voucherRepository.save(voucher);
        return ResponseEntity.ok(voucherSave);
    }

    public ResponseEntity<?> findByCode(String code) {
        Voucher voucherFind = voucherRepository.findByCode(code);
        return ResponseEntity.ok(voucherFind);
    }

    // Cập nhật RoomType
    public ResponseEntity<?> update(Long id, Voucher voucher) {
        Voucher voucherFind = voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        voucherFind.setCode(voucher.getCode());
        voucherFind.setDescription(voucher.getDescription());
        voucherFind.setPercent(voucher.getPercent());
        voucherFind.setQuantity(voucher.getQuantity());

        Voucher updatedVoucher = voucherRepository.save(voucherFind);
        return ResponseEntity.ok(updatedVoucher);
    }

    // Xoá RoomType
    public ResponseEntity<?> delete(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));
        voucherRepository.delete(voucher);
        return ResponseEntity.ok("RoomType deleted successfully");
    }
}
