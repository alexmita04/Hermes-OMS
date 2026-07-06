package com.alexmita.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableCaching
@EnableKafka
public class OrderManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementSystemApplication.class, args);

        System.out.println("APP RUNS");
    }

}
