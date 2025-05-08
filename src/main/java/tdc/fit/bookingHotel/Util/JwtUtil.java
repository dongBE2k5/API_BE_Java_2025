package tdc.fit.bookingHotel.Util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private static final String SECRET = "my-super-secret-key-my-super-secret-key"; // Ít nhất 32 bytes
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(String username, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("USER_ID", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getUsername(String token) {
        return parseAllClaims(token).getSubject();
    }

    public static Long getUserId(String token) {
        return parseAllClaims(token).get("USER_ID", Long.class);
    }

    private static Claims parseAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
