package com.example.hobbybungae.domain.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRequestDto(
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$") // 특수문자 X
        @Min(5)
        @Max(16)
        String idName,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]*$")
        String name,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z0-9\\S]*$") // 특수문자 O
        @Min(8)
        @Max(32)
        String password) {
}
