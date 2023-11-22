package com.example.hobbybungae.domain.dto;

public class ErrorResponseDto extends CommonResponseDto{
    private String msg;

    public ErrorResponseDto(String msg) {
        super("error");
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorResponseDto{" +
            "msg='" + msg + '\'' +
            '}';
    }
}
