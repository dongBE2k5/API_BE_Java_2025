//package tdc.fit.bookingHotel.security;
//
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
//
//
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//
//
//@Configuration
//@EnableMethodSecurity
//public class MethodSecurityConfig {
//	
//    @Bean
//	static RoleHierarchy roleHierarchy() {
//	    return RoleHierarchyImpl.withDefaultRolePrefix()
//	        .role("ROLE_SUPERADMIN").implies("ROLE_ADMIN")
//	        .role("ROLE_ADMIN").implies("ROLE_USER")
//	        .build();
//	}
//    @Bean
//    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
//        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        return expressionHandler;
//    }
//}
//
