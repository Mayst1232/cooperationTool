package com.example.cooperationtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CooperationToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooperationToolApplication.class, args);
    }

}
