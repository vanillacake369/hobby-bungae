package com.example.hobbybungae.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HobbyBungaeApplication {

    public static void main(String[] args) {
//        CommonResponseDTO dto = new UserResponseDTO();
//        CommonResponseDTO dto = new PostResponseDTO();
//        CommonResponseDTO dto = new PostResponseDTO();
        SpringApplication.run(HobbyBungaeApplication.class, args);
    }

}
