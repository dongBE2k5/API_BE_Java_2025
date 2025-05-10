package tdc.fit.bookingHotel.Permission;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import tdc.fit.bookingHotel.entity.Booking;
import tdc.fit.bookingHotel.repository.BookingRepository;

public class BookingPermission implements PermissionEvaluator {
	
 private BookingRepository bookingRepository;
 
	@Autowired
public BookingPermission(BookingRepository bookingRepository) {

	this.bookingRepository = bookingRepository;
}

	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
		if("delete".equals(permission)|| "edit".equals(permission)) {
			Integer bookingID= (Integer) targetDomainObject;
			Optional<Booking> bookingOpt= bookingRepository.findById(bookingID);
			if(bookingOpt.isEmpty()) {
				return false;
			}
		
			Booking booking=bookingOpt.get();
			boolean isOwer = auth.getName().equals(booking.getCustomer().getUserId().getUsername());
			boolean isAdmin=auth.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
			boolean isSuperAdmin=auth.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SUPERADMIN"));
			if("edit".equals(permission)) {
				return isAdmin||isSuperAdmin;
			}
			return isOwer|| isAdmin||isSuperAdmin;
			
		}
		
		
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}
 

 
 
 
 
}


