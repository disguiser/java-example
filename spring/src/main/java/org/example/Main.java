package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        System.out.println(applicationContext.getBean("orderService"));
        System.out.println(applicationContext.getBean("orderService1"));

//        UserService userService = (UserService) applicationContext.getBean("userService");
//        System.out.println(userService.getOrderService());
        UserService userService = new UserService();
        System.out.println(userService.getOrderService());
    }
}