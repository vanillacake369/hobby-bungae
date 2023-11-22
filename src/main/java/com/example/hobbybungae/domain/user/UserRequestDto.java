package com.example.hobbybungae.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$") // 특수문자 X
        String idName,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]*$")
        String name,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z0-9\\S]*$") // 특수문자 O
        String password) {
}
