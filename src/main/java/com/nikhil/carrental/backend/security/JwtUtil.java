//package com.nikhil.carrental.backend.security;
//
//import com.nikhil.carrental.backend.entity.User;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtUtil {
//
//    private final SecretKey key = Keys.hmacShaKeyFor(
//            "mysecretkeymysecretkeymysecretkey".getBytes()
//    );
//
//    // ✅ GENERATE TOKEN
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .setSubject(user.getEmail())
//                .claim("role", user.getRole().name())
//                .signWith(key)
//                .compact();
//    }
//
//    // ✅ EXTRACT EMAIL
//    public String extractEmail(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public String generateToken(String token, UserDetails userDetails) {
//        return token;
//    }
//
//    public String extractUsername(String token) {
//        return token;
//    }
//}
package com.nikhil.carrental.backend.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Generate strong key automatically
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 🔥 Generate Token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key)
                .compact();
    }

    // 🔥 Extract Email
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        return validateToken(token,email);
    }

    public String extractUsername(String token) {
        return extractEmail(token);
    }
}