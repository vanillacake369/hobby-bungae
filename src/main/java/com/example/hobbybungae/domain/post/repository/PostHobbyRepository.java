package com.example.hobbybungae.domain.post.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHobbyRepository extends JpaRepository<PostHobby, Long> {

	Optional<PostHobby> findPostHobbyByPost(Post post);

	Optional<PostHobby> findPostHobbyByHobby(Hobby hobby);
}
