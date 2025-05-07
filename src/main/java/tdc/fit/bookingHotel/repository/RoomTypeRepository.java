package tdc.fit.bookingHotel.repository;
import tdc.fit.bookingHotel.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
}
