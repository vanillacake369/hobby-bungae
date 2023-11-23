package com.example.hobbybungae.domain.user;

import com.example.hobbybungae.response.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobby-bungae/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> signUp(@RequestBody @Valid UserRequestDto requestDto) {
        return userService.signUp(requestDto);
    }
}
