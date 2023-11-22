package com.example.hobbybungae.hobby;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class HobbyRequestDto {
    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z]*$")
    private String hobbyName;
}
