package com.example.hobbybungae.domain.comment.repository;


import com.example.hobbybungae.domain.comment.entity.Comment;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPost(Post post);

	Optional<Comment> findByPost(Post post);

	Optional<Comment> findByUser(User user);
}
