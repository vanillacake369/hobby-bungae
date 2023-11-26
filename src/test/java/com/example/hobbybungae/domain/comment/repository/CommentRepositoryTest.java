package com.example.hobbybungae.domain.comment.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.exception.NotFoundPostException;
import com.example.hobbybungae.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	private Post savedPost = null;

	@BeforeEach
	void setUp() {
		Post post = Post.builder().title("포스트").contents("포스트내용").build();
		savedPost = postRepository.save(post);
	}

	@Test
	@DisplayName("Post를 입력받아 Comment 생성 및 업데이트합니다.")
	public void Post에_대한_Comment생성() {
		assert savedPost != null;
		// GIVEN
		Comment comment = Comment.builder().text("댓글").build();

		// WHEN
		comment.setPost(savedPost);
		commentRepository.save(comment);

		// THEN
		Comment foundComment = commentRepository.findByPost(savedPost)
			.orElseThrow(() -> new NotFoundPostException("post", savedPost.toString()));
		assertEquals(foundComment, comment);
	}
}