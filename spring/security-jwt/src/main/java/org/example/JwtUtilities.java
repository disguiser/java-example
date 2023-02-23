package org.example;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.CheckResult;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtilities {

    private String secret = "ye874kte5874zsdal54FKpqa8425rceS";
    private static final long  jwtExpiration = 1000 * 60 * 60 * 10 ;



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {
        return parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email , List<String> roles) {
        return Jwts.builder().setSubject(email).claim("role",roles).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(generalKey(secret), SignatureAlgorithm.HS256).compact();
    }

    public Key generalKey(String secret) {
        byte[] encodedKey = secret.getBytes();
        Key key = Keys.hmacShaKeyFor(encodedKey);
        return key;
    }

    public Jws<Claims> parseClaimsJws(String token) {
        Key key = generalKey(secret);
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
    }
    public CheckResult validateToken(String token) {
        CheckResult checkResult = new CheckResult();
        checkResult.setSuccess(false);
        try {
            parseClaimsJws(token);
            checkResult.setSuccess(true);
        } catch (SignatureException e) {
            checkResult.setErrMsg("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            checkResult.setErrMsg("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            checkResult.setErrMsg("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            checkResult.setErrMsg("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            checkResult.setErrMsg("JWT token compact of handler are invalid.");
        }
        return checkResult;
    }

    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7,bearerToken.length()); } // The part after "Bearer "
        return null;
    }

}
