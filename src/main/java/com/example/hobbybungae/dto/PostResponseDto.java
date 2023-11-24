package com.example.hobbybungae.dto;


import com.example.hobbybungae.entity.PostEntity;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        String state,
        String hobby,
        LocalDateTime createdAt
) {
    public PostResponseDto(PostEntity savePost) {
        this(
                savePost.getId(),
                savePost.getTitle(),
                savePost.getContents(),
                savePost.getState(),
                savePost.getHobby(),
                savePost.getCreatedAt()
        );
    }
}
