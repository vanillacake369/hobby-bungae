package com.example.hobbybungae.domain.post.repository;

import static com.example.hobbybungae.domain.post.entity.QPost.post;

import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import com.example.hobbybungae.domain.state.entity.State;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Post> findPostsBy(PostSelectCondition condition) {
		return jpaQueryFactory.
			selectFrom(post)
			.where(
				stateEq(condition.state()),
				userEq(condition.userId()),
				titleEq(condition.title()),
				contentsEq(condition.contents())
			)
			.fetch();
	}

	private BooleanExpression stateEq(State state) {
		if (state == null)
			return null;
		return post.state.eq(state);
	}

	private BooleanExpression userEq(Long userId) {
		if (userId == null)
			return null;
		return post.user.id.eq(userId);
	}

	private BooleanExpression titleEq(String title) {
		if (title == null)
			return null;
		return post.title.eq(title);
	}

	private BooleanExpression contentsEq(String contents) {
		if (contents == null)
			return null;
		return post.contents.contains(contents);
	}

}
