package com.topica.zyot.shyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
