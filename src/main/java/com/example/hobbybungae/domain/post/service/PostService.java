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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

	private final PostRepository postRepository;

	private final HobbyService hobbyService;

	private final StateService stateService;

	static void validateUserIsAuthor(Long postAuthorId, Long loggedInUserId) throws InvalidPostModifierException {
		if (!postAuthorId.equals(loggedInUserId)) {
			throw new InvalidPostModifierException("postAuthor", postAuthorId.toString(),
				"사용자는 이 게시물을 업데이트/삭제할 권한이 없습니다.");
		}
	}

	void validateHobbiesExistence(List<Hobby> hobbies) throws NotFoundHobbyException {
		log.info("취미 검증 시작");
		for (Hobby hobby : hobbies) {
			hobbyService.validateHobbyExistence(hobby);
		}
		log.info("취미 검증 통과");
	}

	public PostResponseDto addPost(PostRequestDto requestDto, User user)
		throws NotFoundHobbyException, NotFoundStateException {
		log.info("Post Service :: addPost");

		// 취미카테고리 & 지역 데이터 존재여부 검증
		try {
			validateHobbiesExistence(requestDto.hobbies());
		} catch (NotFoundHobbyException exception) {
			log.error(exception.getMessage());
			log.error(exception.toString());
			throw exception;
		}
		try {
			stateService.validateStateExistence(requestDto.state());
		} catch (NotFoundStateException exception) {
			log.error(exception.getMessage());
			log.error(exception.toString());
			throw exception;
		}

		// Dto -> Entity
		Post post = Post.of(requestDto, user);
		Post savePost = postRepository.save(post);
		log.info("Post Service **COMPLETED** :: addPost");
		return new PostResponseDto(savePost);
	}

	@Transactional(readOnly = true)
	public PostResponseDto getPost(Long postId) {
		Post post = getPostById(postId);
		return new PostResponseDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Post> postList = postRepository.findAll(pageable);
		List<PostResponseDto> responseDtos = postList.stream()
			.map(PostResponseDto::new)
			.toList();
		return responseDtos;
	}

	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, User user)
		throws InvalidPostModifierException {
		Post post = getPostById(postId);
		validateUserIsAuthor(post.getUser().getId(), user.getId());
		post.update(requestDto, user);
		return new PostResponseDto(post);
	}

	public void deletePost(Long postId, User user) {
		Post post = getPostById(postId);
		validateUserIsAuthor(post.getUser().getId(), user.getId());
		postRepository.delete(post);
	}

	@Transactional(readOnly = true)
	public Post getPostById(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new NotFoundPostException("postId", postId.toString(), "주어진 id에 해당하는 게시글이 존재하지 않음"));
	}

}
