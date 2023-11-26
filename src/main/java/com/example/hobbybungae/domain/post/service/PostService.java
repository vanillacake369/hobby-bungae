package com.example.hobbybungae.domain.post.service;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.hobby.exception.NotFoundHobbyException;
import com.example.hobbybungae.domain.hobby.service.HobbyService;
import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.InvalidPostModifierException;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import com.example.hobbybungae.domain.state.exception.NotFoundStateException;
import com.example.hobbybungae.domain.state.service.StateService;
import com.example.hobbybungae.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

	private final PostRepository postRepository;

	private final HobbyService hobbyService;

	private final StateService stateService;

	public PostResponseDto addPost(PostRequestDto requestDto) throws NotFoundHobbyException, NotFoundStateException {
		// 취미카테고리 & 지역 데이터 존재여부 검증
		validateHobbiesExistence(requestDto.getHobbies());
		stateService.validateStateExistence(requestDto.getState());

		// Dto -> Entity
		Post post = Post.of(requestDto);
		Post savePost = postRepository.save(post);
		return new PostResponseDto(savePost);
	}

	void validateHobbiesExistence(List<Hobby> hobbies) throws NotFoundHobbyException {
		for (Hobby hobby : hobbies) {
			hobbyService.validateHobbyExistence(hobby);
		}
	}

	@Transactional(readOnly = true)
	public PostResponseDto getPost(Long postId) {
		Post post = getPostById(postId);
		return new PostResponseDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponseDto> getPosts() {
		return postRepository.findAllByOrderByCreatedAtDesc().stream()
			.map(PostResponseDto::new)
			.collect(Collectors.toList());
	}

	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user)
		throws InvalidPostModifierException {
		Post post = getPostById(postId);
		validateUserIsAuthor(post.getUser().getId(), user.getId());
		post.update(requestDto);
		return new PostResponseDto(post);
	}

	public void deletePost(Long postId, User user) {
		Post post = getPostById(postId);
		postRepository.delete(post);
	}

	@Transactional(readOnly = true)
	public Post getPostById(Long postId) {
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
