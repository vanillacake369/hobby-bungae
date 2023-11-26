package com.example.hobbybungae.domain.post.dto;


import com.example.hobbybungae.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        String state,
        String hobby,
        String author,
        LocalDateTime createdAt
) {
    public PostResponseDto(Post savePost) {
        this(
                savePost.getId(),
                savePost.getTitle(),
                savePost.getContents(),
                savePost.getState(),
                savePost.getHobby(),
                savePost.getAuthor(),
                savePost.getCreatedAt()
        );
    }
}
