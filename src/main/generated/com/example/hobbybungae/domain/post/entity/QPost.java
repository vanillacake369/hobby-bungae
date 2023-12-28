package com.example.hobbybungae.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1969800982L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.example.hobbybungae.domain.common.QTimeStamp _super = new com.example.hobbybungae.domain.common.QTimeStamp(this);

    public final ListPath<com.example.hobbybungae.domain.comment.entity.Comment, com.example.hobbybungae.domain.comment.entity.QComment> comments = this.<com.example.hobbybungae.domain.comment.entity.Comment, com.example.hobbybungae.domain.comment.entity.QComment>createList("comments", com.example.hobbybungae.domain.comment.entity.Comment.class, com.example.hobbybungae.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<PostHobby, QPostHobby> postHobbies = this.<PostHobby, QPostHobby>createList("postHobbies", PostHobby.class, QPostHobby.class, PathInits.DIRECT2);

    public final com.example.hobbybungae.domain.state.entity.QState state;

    public final StringPath title = createString("title");

    public final com.example.hobbybungae.domain.user.entity.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.state = inits.isInitialized("state") ? new com.example.hobbybungae.domain.state.entity.QState(forProperty("state")) : null;
        this.user = inits.isInitialized("user") ? new com.example.hobbybungae.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

