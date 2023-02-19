package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	@Bean
	public OrderService orderService1() {
		return new OrderService();
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}