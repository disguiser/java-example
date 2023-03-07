package org.example.HTTPInterface;

import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserApiService {
    @GetExchange("/list")
    List<User> getUsers();
}
