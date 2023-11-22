package com.example.hobbybungae.profile;

import lombok.Getter;

import java.util.List;

@Getter
public class ProfileUpdateRequestDto {
    private String user_id;
    private String password;
    private String name;
    private String introduction;
    private String state;
    private List<Hobby> hobbies;
}
