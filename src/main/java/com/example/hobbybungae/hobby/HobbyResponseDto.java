package com.example.hobbybungae.hobby;

import com.example.hobbybungae.hobby.Hobby;
import lombok.Getter;

@Getter
public class HobbyResponseDto {
    private Long hobbyId;

    private String hobbyName;

    public HobbyResponseDto(Hobby hobby) {
        this.hobbyId = hobby.getHobbyId();
        this.hobbyName = hobby.getHobbyName();
    }
}
