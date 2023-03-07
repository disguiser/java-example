package org.example.HTTPInterface;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        WebClient client = WebClient.builder().baseUrl("http://localhost:8080/user/").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
        UserApiService service = factory.createClient(UserApiService.class);
        List<User> users = service.getUsers();
        for (User user: users) {
            System.out.println(user);
        }
    }
}
