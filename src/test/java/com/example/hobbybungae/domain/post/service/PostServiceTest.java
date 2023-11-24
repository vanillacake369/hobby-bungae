package com.example.hobbybungae.domain.post.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hobbybungae.domain.common.TimeStamp;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({PostService.class, TimeStamp.class, PostRepository.class, Post.class})
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("취미를 검증합니다.")
    public void 취미존재여부검증() throws Exception {
        // GIVEN
        String hobby = "sdlkajlskdgjlsdakjglajk";
        // THEN
        assertThrows(NotFoundHobbyException.class, () -> {
            postService.validateHobbyExistence(hobby);
        });
    }
}