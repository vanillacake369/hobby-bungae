package com.example.hobbybungae.domain.user;


import com.example.hobbybungae.response.SuccessResponseDto;

public record UserResponseDto(
        String idName,
        String name) {
    public static SuccessResponseDto successResponseOf(User newUser) {
        UserResponseDto userResponseDto = new UserResponseDto(newUser.getIdName(), newUser.getName());
        return new SuccessResponseDto(userResponseDto);
    }
}
