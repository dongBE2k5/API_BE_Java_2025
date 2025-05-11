package tdc.fit.bookingHotel.testUser;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

	 private final PostRepository postRepository;
	 
	 

	    @Autowired
	    public CustomPermissionEvaluator(PostRepository postRepository) {
	        this.postRepository = postRepository;
	        
	    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
       
        auth.getAuthorities().forEach(a -> System.out.println("Authority: " + a.getAuthority()));
        if ("delete".equals(permission)) {
            Long postId = (Long) targetDomainObject;
            
            Optional<Post> postOpt = postRepository.findById(postId);
        
            if (postOpt.isEmpty()) {
                System.out.println("Post not found.");
                return false;
            }

            Post post = postOpt.get();
            System.out.println("Post owner username: " + post.getOwnerUsername());
            System.out.println("name"+ auth.getName());
            boolean isOwner = auth.getName().equals(post.getOwnerUsername());
            boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            System.out.println("Is owner? " + isOwner);
            System.out.println("Is admin? " + isAdmin);

            return isOwner || isAdmin;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        return false;
    }
}
