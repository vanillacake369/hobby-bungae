package com.example.hobbybungae.domain.post.repository;

import com.example.hobbybungae.domain.hobby.entity.Hobby;
import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.entity.PostHobby;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostHobbyRepository extends JpaRepository<PostHobby, Long> {

	List<PostHobby> findPostHobbyByPost(Post post);

	List<PostHobby> findPostHobbyByHobby(Hobby hobby);

	@EntityGraph(attributePaths = {"post", "hobby"})
	@Query("select ph from PostHobby ph")
	List<PostHobby> findALlEntityGraph();

	@Modifying
	@Query("DELETE FROM PostHobby ph")
		// 쿼리를 객체지향적으로 생성 => 각 sql에 맞는 쿼리로 변환해서 실행
	void deleteAll();
}
