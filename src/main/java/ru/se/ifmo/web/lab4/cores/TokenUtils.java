package ru.se.ifmo.web.lab4.cores;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ru.se.ifmo.web.lab4.beans.User;

public class TokenUtils {
    public static String generateUserToken(User user) {
        String jwt = Jwts.builder()
                .claim("login", user.getLogin())
                .claim("password", user.getPassword())
                .signWith(Keys.hmacShaKeyFor("WH3NY0U4R3SUFF3R1NGKN0WTH4T1H4V3B3TR4Y3DY0U".getBytes()))
                .compact();
        return jwt;
    }
    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor("WH3NY0U4R3SUFF3R1NGKN0WTH4T1H4V3B3TR4Y3DY0U".getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (JwtException e) {
            throw new RuntimeException("Invalid token!");
        }
    }
}
