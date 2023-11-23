package com.example.hobbybungae.response;

import lombok.Getter;

@Getter
public class SuccessResponseDto extends CommonResponseDto {
    private final Object data;

    public SuccessResponseDto(Object data) {
        super("success");
        this.data = data;
    }
}
