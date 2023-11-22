package com.example.hobbybungae.dto;

import lombok.Getter;

@Getter
public class SuccessResponseDto extends CommonResponseDto{
    private Object data;

    public SuccessResponseDto(Object data) {
        super("success");
        this.data = data;
    }
}
