package com.example.hobbybungae.domain.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto extends CommonResponseDto {
    private final String msg;

    public ErrorResponseDto(String msg) {
        super("error");
        this.msg = msg;
    }

}
