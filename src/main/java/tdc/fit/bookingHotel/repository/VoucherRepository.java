package tdc.fit.bookingHotel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tdc.fit.bookingHotel.entity.Voucher;


@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Voucher findByCode(String code);
}

