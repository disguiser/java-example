package org.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloRestController {

    @GetMapping("user")
    public String helloUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "Hello User";
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

}
