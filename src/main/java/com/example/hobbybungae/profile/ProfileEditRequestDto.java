package com.example.hobbybungae.profile;

import lombok.Getter;

import java.util.List;

@Getter
public class ProfileEditRequestDto {
    private Long profileId;
    private Long user_id;
    private String name;
    private String password;
    private String introduction;
    private String state;
    private List<Hobby> hobbies;

}
