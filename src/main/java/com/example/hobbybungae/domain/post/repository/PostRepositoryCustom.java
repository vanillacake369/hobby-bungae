package com.example.hobbybungae.domain.post.repository;

import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import java.util.List;

public interface PostRepositoryCustom {

	List<Post> findPostsBy(PostSelectCondition postSelectCondition);
}
