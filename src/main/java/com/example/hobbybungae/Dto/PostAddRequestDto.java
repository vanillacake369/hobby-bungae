package com.example.hobbybungae.Dto;


import lombok.Getter;

@Getter
public class PostAddRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
    private String region;
}
