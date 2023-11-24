package com.example.hobbybungae.domain.hobby.controller;

import com.example.hobbybungae.domain.hobby.dto.HobbyRequestDto;
import com.example.hobbybungae.domain.hobby.dto.HobbyResponseDto;
import com.example.hobbybungae.domain.hobby.service.HobbyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobby-bungae/v1/categories")
public class HobbyController {

    private final HobbyService hobbyService;

    @PostMapping
    public ResponseEntity<HobbyResponseDto> postHobby(@RequestBody @Valid HobbyRequestDto requestDto) {
        HobbyResponseDto responseDto = hobbyService.postHobby(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
