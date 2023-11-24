package com.example.hobbybungae.domain.post.service;

import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.entity.PostEntity;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import com.example.hobbybungae.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto addPost(PostRequestDto requestDto) {
        // Dto -> Entity
        PostEntity postEntity = new PostEntity(requestDto);
        PostEntity savePost = postRepository.save(postEntity);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto getPost(Long postId) {
        PostEntity postEntity = getPostEntity(postId);
        return new PostResponseDto(postEntity);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        PostEntity postEntity = getPostEntity(postId);
//        verifyPassword(postEntity, requestDto.getPassword());
        postEntity.update(requestDto);
        return new PostResponseDto(postEntity);
    }

    public void deletePost(Long postId, User user) {
        PostEntity postEntity = getPostEntity(postId);
        postRepository.delete(postEntity);
    }

    private PostEntity getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("postId", postId.toString(), "주어진 id에 해당하는 게시글이 존재하지 않음"));
    }
}
