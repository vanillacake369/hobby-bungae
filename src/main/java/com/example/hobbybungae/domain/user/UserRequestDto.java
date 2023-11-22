package com.example.hobbybungae.domain.user;

import lombok.Getter;

@Getter
public record UserRequestDto(
    String idName,
    String name,
    String password) {
}
