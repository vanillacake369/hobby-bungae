package com.example.hobbybungae.Dto;


import com.example.hobbybungae.entity.PostEntity;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String author,
        String content,
        String region,
        LocalDateTime createdAt
) {
    public PostResponseDto(PostEntity savePost) {
        this(
                savePost.getId(),
                savePost.getTitle(),
                savePost.getAuthor(),
                savePost.getContents(),
                savePost.getRegion(),
                savePost.getCreatedAt()
        );
    }
}
