package com.example.hobbybungae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HobbyBungaeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HobbyBungaeApplication.class, args);
    }

}
