package com.example.hobbybungae.domain.hobby;

import com.example.hobbybungae.domain.user.entity.User;
import com.example.hobbybungae.response.ErrorResponseDto;
import com.example.hobbybungae.response.SuccessResponseDto;
import com.example.hobbybungae.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ResponseEntity<SuccessResponseDto> postHobby(@RequestBody @Valid HobbyRequestDto requestDto) {
        HobbyResponseDto responseDto = hobbyService.postHobby(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponseDto(responseDto)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> duplicateHobbyName(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ErrorResponseDto(
                        ex.getMessage()
                )
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDto> postHobby2(@RequestBody @Valid HobbyRequestDto requestDto,
                                                         @AuthenticationPrincipal
                                                         UserDetailsImpl userDetails) {
        // 생성,수정,삭제
        // 사용자 검증
        User user = userDetails.getUser();
        Long id = user.getId();

        // 도메인 내에서 사용자 검증
        HobbyResponseDto responseDto = hobbyService.postHobby(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponseDto(responseDto)
        );
    }
}
