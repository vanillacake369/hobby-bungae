package com.example.hobbybungae.domain.post.dto;


import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import com.example.hobbybungae.domain.state.entity.State;
import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        State state,
        List<Hobby> hobbies,
        LocalDateTime createdAt
) {
    public PostResponseDto(Post savePost) {
        this(
                savePost.getId(),
                savePost.getTitle(),
                savePost.getContents(),
                savePost.getState(),
                savePost.getPostHobbyList().stream()
                    .map(PostHobby::getHobby).toList(),
                savePost.getCreatedAt()
        );
    }
}
