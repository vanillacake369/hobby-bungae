package com.example.hobbybungae.domain.post.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHobbyRepository extends JpaRepository<PostHobby, Long> {

	List<PostHobby> findPostHobbyByPost(Post post);

	List<PostHobby> findPostHobbyByHobby(Hobby hobby);
}
