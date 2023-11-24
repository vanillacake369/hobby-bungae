package com.example.hobbybungae.domain.post.service;

import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.NotFoundHobbyException;
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
    private final HobbyRepository hobbyRepository;

    public PostResponseDto addPost(PostRequestDto requestDto) {
        // 취미카테고리 & 지역 데이터 존재여부 검증
        validateHobbyExistence(requestDto.getHobby());

        // Dto -> Entity
        Post post = new Post(requestDto);
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    void validateHobbyExistence(String hobby) {
        boolean hasNotHobby = hobbyRepository.findByHobbyName(hobby).isEmpty();
        if (hasNotHobby) {
            throw new NotFoundHobbyException("hobby", hobby, "선택한 취미 카테고리가 없습니다");
        }
    }

    public PostResponseDto getPost(Long postId) {
        Post post = getPostEntity(postId);
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = getPostEntity(postId);
//        verifyPassword(postEntity, requestDto.getPassword());
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    public void deletePost(Long postId, User user) {
        Post post = getPostEntity(postId);
        postRepository.delete(post);
    }

    public Post getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("postId", postId.toString(), "주어진 id에 해당하는 게시글이 존재하지 않음"));
    }
}
