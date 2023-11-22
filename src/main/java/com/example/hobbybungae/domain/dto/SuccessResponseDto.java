package com.example.hobbybungae.domain.dto;

import lombok.Getter;

@Getter
public class SuccessResponseDto extends CommonResponseDto {
    private final Object data;

    public SuccessResponseDto(Object data) {
        super("success");
        this.data = data;
    }
}
