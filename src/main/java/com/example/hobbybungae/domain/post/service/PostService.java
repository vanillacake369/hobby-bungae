package com.example.hobbybungae.domain.post.service;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.repository.HobbyRepository;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.InvalidPostModifierException;
import com.example.hobbybungae.domain.post.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.post.repository.PostHobbyRepository;
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

	private final PostHobbyRepository postHobbyRepository;

	public PostResponseDto addPost(PostRequestDto requestDto) throws NotFoundHobbyException {
		// 취미카테고리 & 지역 데이터 존재여부 검증
		validateHobbiesExistence(requestDto.getHobbies());

		// Dto -> Entity
		Post post = new Post(requestDto);
		Post savePost = postRepository.save(post);
		return new PostResponseDto(savePost);
	}

	void validateHobbiesExistence(List<Hobby> hobbies) throws NotFoundHobbyException {
		for (Hobby hobby : hobbies) {
			validateHobbyExistence(hobby);
		}
	}

	void validateHobbyExistence(Hobby hobby) {
		if (hobbyRepository.findByHobbyName(hobby.getHobbyName()).isEmpty()) {
			throw new NotFoundHobbyException("hobby", hobby.getHobbyName(), "선택한 취미 카테고리가 없습니다");
		}
	}

	@Transactional(readOnly = true)
	public PostResponseDto getPost(Long postId) {
		Post post = getPostEntity(postId);
		return new PostResponseDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponseDto> getPosts() {
		return postRepository.findAllByOrderByCreatedAtDesc().stream()
			.map(PostResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user)
		throws InvalidPostModifierException {
		Post post = getPostEntity(postId);
		validateUserIsAuthor(post.getUser().getId(), user.getId());
		post.update(requestDto);
		return new PostResponseDto(post);
	}

	public void deletePost(Long postId, User user) {
		Post post = getPostEntity(postId);
		postRepository.delete(post);
	}

	@Transactional(readOnly = true)
	public Post getPostEntity(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException("postId", postId.toString(), "주어진 id에 해당하는 게시글이 존재하지 않음"));
	}

	void validateUserIsAuthor(Long postAuthorId, Long loggedInUserId) throws InvalidPostModifierException {
		if (!postAuthorId.equals(loggedInUserId)) {
			throw new InvalidPostModifierException("postAuthor", postAuthorId.toString(),
				"사용자는 이 게시물을 업데이트/삭제할 권한이 없습니다.");
		}
	}
}
