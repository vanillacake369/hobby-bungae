package com.example.hobbybungae.domain.post.repository;

import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

	List<Post> findPostsBy(PostSelectCondition postSelectCondition);

	Page<Post> findPostsPaging(PostSelectCondition postSelectCondition, Pageable pageable);
}
