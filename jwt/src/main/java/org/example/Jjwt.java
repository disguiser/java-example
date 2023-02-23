package org.example;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;

public class Jjwt {
    public static void main(String[] args) {
        String secret = "8677df7fc3a34e26a61c034d5ec8245d";
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJyb2xlc1wiOltcIkFkbWluXCJdLFwiZGVwdElkXCI6bnVsbCxcImlkXCI6MSxcInVzZXJOYW1lXCI6XCJhZG1pblwifSIsImlzcyI6IuWRqOaYjuW4hSIsImlhdCI6MTY3NTU5ODgzNSwiZXhwIjoxNjc1Njg1MjM1fQ.4cDpo44sqiTx2FgTxZ5CYtevTWCumWmPC8hQu7TiiXU";
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7YToxfSIsImlzcyI6IuWRqOaYjuW4hSIsImV4cCI6MTY3NTE4MDgwMH0.tTHV6A1evUyElF-u6SPe6caymJtbHBt3qbKTpbRa2BI";
        String token = "";
        try {
            byte[] encodedKey = secret.getBytes();
            Key key = Keys.hmacShaKeyFor(encodedKey);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getSubject());
            System.out.println(claims.getExpiration());
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}