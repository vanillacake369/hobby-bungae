package com.example.hobbybungae.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto extends CommonResponseDto{
    private String msg;

    public ErrorResponseDto(String msg) {
        super("error");
        this.msg = msg;
    }

}
