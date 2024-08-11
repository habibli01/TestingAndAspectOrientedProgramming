package com.example.redirectregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RedirectRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedirectRegistrationApplication.class, args);
    }

}
