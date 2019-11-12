package com.accenture.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BussinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BussinessServiceApplication.class, args);
    }

}
