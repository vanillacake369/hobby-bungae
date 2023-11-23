package com.example.hobbybungae.Dto;


import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String author;
    private String content;
    private String password;
    private String region;
}
