package com.example.hobbybungae.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 582579904L;

    public static final QUser user = new QUser("user");

    public final com.example.hobbybungae.domain.common.QTimeStamp _super = new com.example.hobbybungae.domain.common.QTimeStamp(this);

    public final ListPath<com.example.hobbybungae.domain.comment.entity.Comment, com.example.hobbybungae.domain.comment.entity.QComment> comments = this.<com.example.hobbybungae.domain.comment.entity.Comment, com.example.hobbybungae.domain.comment.entity.QComment>createList("comments", com.example.hobbybungae.domain.comment.entity.Comment.class, com.example.hobbybungae.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idName = createString("idName");

    public final StringPath introduction = createString("introduction");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final ListPath<com.example.hobbybungae.domain.post.entity.Post, com.example.hobbybungae.domain.post.entity.QPost> posts = this.<com.example.hobbybungae.domain.post.entity.Post, com.example.hobbybungae.domain.post.entity.QPost>createList("posts", com.example.hobbybungae.domain.post.entity.Post.class, com.example.hobbybungae.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final ListPath<com.example.hobbybungae.domain.userProfile.entity.UserHobby, com.example.hobbybungae.domain.userProfile.entity.QUserHobby> userHobbies = this.<com.example.hobbybungae.domain.userProfile.entity.UserHobby, com.example.hobbybungae.domain.userProfile.entity.QUserHobby>createList("userHobbies", com.example.hobbybungae.domain.userProfile.entity.UserHobby.class, com.example.hobbybungae.domain.userProfile.entity.QUserHobby.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

