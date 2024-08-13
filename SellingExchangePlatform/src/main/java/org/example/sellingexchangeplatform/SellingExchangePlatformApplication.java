package org.example.sellingexchangeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SellingExchangePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellingExchangePlatformApplication.class, args);
    }

}
