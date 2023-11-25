package com.example.hobbybungae.domain.userProfile.dto;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import lombok.Getter;

import java.util.List;

@Getter
public record UserProfileUpdateRequestDto<Hobby> (
        String idName,
        String name,
        String passwordReconfirm,
        String password,
        String introduction,
        List<Hobby> hobbyList
) {

}