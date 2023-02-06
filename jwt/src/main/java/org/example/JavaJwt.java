package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JavaJwt {
    static String secret = "8677df7fc3a34e26a61c034d5ec8245d";

    static String issuer = "周明帅";

    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJyb2xlc1wiOltcIkFkbWluXCJdLFwiZGVwdElkXCI6bnVsbCxcImlkXCI6MSxcInVzZXJOYW1lXCI6XCJhZG1pblwifSIsImlzcyI6IuWRqOaYjuW4hSIsImlhdCI6MTY3NTU5ODgzNSwiZXhwIjoxNjc1Njg1MjM1fQ.4cDpo44sqiTx2FgTxZ5CYtevTWCumWmPC8hQu7TiiXU";
        String token = createToken();
        decodeToken(token);
    }
    public static void decodeToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer(issuer)
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
            System.out.println(decodedJWT.getSubject());
            System.out.println(decodedJWT.getExpiresAt());
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
        }
    }
    public static String createToken() {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withSubject("{a:1}")
                    .withExpiresAt(new Date())
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (JWTCreationException exception){

        }
        return token;
    }
}
