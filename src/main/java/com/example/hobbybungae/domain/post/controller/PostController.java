package com.example.hobbybungae.domain.post.controller;

import com.example.hobbybungae.domain.post.dto.PostRequestDto;
import com.example.hobbybungae.domain.post.dto.PostResponseDto;
import com.example.hobbybungae.domain.post.service.PostService;
import com.example.hobbybungae.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hobby-bungae/v1/hobbies")
public class PostController {

	private final PostService postService;

	@GetMapping("/{postId}")
	public ResponseEntity<PostResponseDto> getPost(
		@PathVariable Long postId
	) {
		PostResponseDto responseDto = postService.getPost(postId);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping
	public ResponseEntity<List<PostResponseDto>> getPosts() {
		List<PostResponseDto> responseDto = postService.getPosts();
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping
	public ResponseEntity<PostResponseDto> addPost(@RequestBody PostRequestDto requestDto) {
		PostResponseDto responseDto = postService.addPost(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostResponseDto> updatePost(
		@PathVariable Long postId,
		@Valid @RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		PostResponseDto responseDto = postService.updatePost(postId, requestDto, userDetails.getUser());
		return ResponseEntity.ok(responseDto);
	}


	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(
		@PathVariable Long postId,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		postService.deletePost(postId, userDetails.getUser());
		return ResponseEntity.noContent().build();
	}
}
