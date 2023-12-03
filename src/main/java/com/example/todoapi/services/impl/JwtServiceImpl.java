package com.example.todoapi.services.impl;

import com.example.todoapi.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET_KEY = "T7oZOEyH0TQyW+MJ0BFl2/b8K1N2sf7xYuwcFBeV0a+55iWW0F+jerqOEz1sIY++n6eRg87A6oWXLNrNvwtoXGeE7nhUEtsWZ1akY6K3B+sKCuVonQCZ+cd8tHxxXg7u2kF9QJTWyS9cQghIMetI9iigH0xhX2XnxEuy0hu9Z1E9wVBu76Xuvcty7jjRKHkGRt1ay3LGuy+1JNCeQS2c/nV6O7xbnD4pmIVb1RIoYiUgBagBhacgCp3xk4MkhwfXsbX3YzGsOgEu8il0nGl8iv4GM6BGL+FK36bDcUIZdMWezkCke8NkjnnZKugq8GK5/WOIMd76u4u83VNouS9doqA+OJLznzixQOiQj2gucgE=\n";

    @Override
    public String GenerateAccessToken(Long userId, String username) {
        byte[] keyBytes = SECRET_KEY.getBytes();
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        Date currentDate = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date expiredDate = c.getTime();

        return Jwts.builder()
                .setSubject("access_token")
                .setIssuedAt(currentDate)
                .setExpiration(expiredDate)
                .claim("user_id", userId)
                .claim("username", username)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractTokenFromHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring("Bearer ".length());
            }

            return null;
        }

        return null;
    }

    @Override
    public String GetUsername() {
        var token = extractTokenFromHeader();
        if (token == null){
            return null;
        }

        byte[] keyBytes = SECRET_KEY.getBytes();
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.get("username", String.class);
    }

    @Override
    public Long GetUserId() {
        var token = extractTokenFromHeader();
        if (token == null){
            return null;
        }

        byte[] keyBytes = SECRET_KEY.getBytes();
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.get("user_id", Long.class);
    }

    @Override
    public boolean IsTokenValid(String token, UserDetails userDetails) {
        final String username = this.GetUsername();

        return username.equals(userDetails.getUsername()) && !IsTokenExpired(token);
    }

    private boolean IsTokenExpired(String token) {
        byte[] keyBytes = SECRET_KEY.getBytes();
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Claims claims = parser.parseClaimsJws(token).getBody();

        return claims.getExpiration().before(new Date());
    }
}