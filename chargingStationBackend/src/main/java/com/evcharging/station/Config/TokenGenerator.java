package com.evcharging.station.Config;

import com.evcharging.station.RuntimeException.AuthException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class TokenGenerator {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String username,int id) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 5 * 60 * 60 * 1000);

        return Jwts.builder()
                .setSubject(username)
                .setSubject(String.valueOf(id))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean isValidToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token==""){
            throw new AuthException("station", "not logged in");
        }
        try {

            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            System.out.println("Token Is valid");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
