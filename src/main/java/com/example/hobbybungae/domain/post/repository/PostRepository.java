package com.example.hobbybungae.domain.post.repository;


import com.example.hobbybungae.domain.post.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByTitle(String title);

	List<Post> findAllByOrderByCreatedAtDesc();
}