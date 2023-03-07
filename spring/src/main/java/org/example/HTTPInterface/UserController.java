package org.example.HTTPInterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/list")
    public List<User> list() {
        return IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new User(i, "User" + i))
                .collect(Collectors.toList());
    }
}
