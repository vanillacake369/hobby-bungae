package com.example.hobbybungae.hobby;

import com.example.hobbybungae.dto.CommonResponseDto;
import com.example.hobbybungae.dto.ErrorResponseDto;
import com.example.hobbybungae.dto.SuccessResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponseDto> postHobby(@RequestBody @Valid HobbyRequestDto requestDto){
        HobbyResponseDto responseDto = hobbyService.postHobby(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
            new SuccessResponseDto(responseDto)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponseDto> duplicateHobbyName(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new ErrorResponseDto(
                ex.getMessage()
            )
        );
    }
}
