package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getSubject());
            System.out.println(claims.getExpiration());
        } catch (ExpiredJwtException e) {
            System.out.println(402);
            throw e;
        } catch (JwtException e) {
            System.out.println(403);
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}