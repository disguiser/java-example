package org.example.service;

import org.example.model.CustomerUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new CustomerUserDetails("user", "password", List.of("User"));
        } else {
            return new CustomerUserDetails("admin", "password", List.of("Admin"));
        }
    }
}
