package com.example.hobbybungae.domain.post.repository;

import static com.example.hobbybungae.domain.post.entity.QPost.post;

import com.example.hobbybungae.domain.post.entity.Post;
import com.example.hobbybungae.domain.post.unit.PostSelectCondition;
import com.example.hobbybungae.domain.state.entity.State;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	private JPAQuery<Post> getJPAQueryOf(PostSelectCondition condition) {
		return jpaQueryFactory.
			selectFrom(post)
			.where(
				stateEq(condition.state()),
				userEq(condition.userId()),
				titleEq(condition.title()),
				contentsEq(condition.contents())
			);
	}

	@Override
	public List<Post> findPostsBy(PostSelectCondition condition) {
		return getJPAQueryOf(condition)
			.fetch();
	}

	@Override
	public Page<Post> findPostsPaging(PostSelectCondition postSelectCondition, Pageable pageable) {
		List<Post> content = getJPAQueryOf(postSelectCondition)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(content, pageable, () ->
			getJPAQueryOf(postSelectCondition).fetch().size());
	}

	private BooleanExpression stateEq(State state) {
		if (state == null) {
			return null;
		}
		return post.state.eq(state);
	}

	private BooleanExpression userEq(Long userId) {
		if (userId == null) {
			return null;
		}
		return post.user.id.eq(userId);
	}

	private BooleanExpression titleEq(String title) {
		if (title == null) {
			return null;
		}
		return post.title.eq(title);
	}

	private BooleanExpression contentsEq(String contents) {
		if (contents == null) {
			return null;
		}
		return post.contents.contains(contents);
	}

}
