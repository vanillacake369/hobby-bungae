package com.example.hobbybungae.domain.dto;

public class SuccessResponseDto extends CommonResponseDto{
    private Object data;

    public SuccessResponseDto(Object data) {
        super("success");
        this.data = data;
    }
}
