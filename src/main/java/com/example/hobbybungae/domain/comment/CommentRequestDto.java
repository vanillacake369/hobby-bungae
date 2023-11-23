package com.example.hobbybungae.domain.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank
    private String text;
}
