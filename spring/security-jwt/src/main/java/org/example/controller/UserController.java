package org.example.controller;

import org.example.JwtUtilities;
import org.example.dto.LoginDto;
import org.example.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtilities jwtUtilities) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilities = jwtUtilities;
    }

    private final JwtUtilities jwtUtilities;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
//        Authentication authentication= authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginDto.getUsername(),
//                        loginDto.getPassword()
//                )
//        );
//        System.out.println(authentication);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtilities.generateToken(loginDto.getUsername(), List.of("Admin"));
        return ResponseEntity.ok(token);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(passwordEncoder.encode(registerDto.getPassword()));
    }
}
