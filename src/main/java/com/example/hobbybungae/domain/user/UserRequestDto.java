package com.example.hobbybungae.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]*$")
        String idName,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]*$")
        String name,
        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]*$")
        String password) {
}
