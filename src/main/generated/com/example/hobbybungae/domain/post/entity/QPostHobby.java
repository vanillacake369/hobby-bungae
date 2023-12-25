package com.example.hobbybungae.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostHobby is a Querydsl query type for PostHobby
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostHobby extends EntityPathBase<PostHobby> {

    private static final long serialVersionUID = -1633396984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostHobby postHobby = new QPostHobby("postHobby");

    public final com.example.hobbybungae.domain.hobby.entity.QHobby hobby;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public QPostHobby(String variable) {
        this(PostHobby.class, forVariable(variable), INITS);
    }

    public QPostHobby(Path<? extends PostHobby> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostHobby(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostHobby(PathMetadata metadata, PathInits inits) {
        this(PostHobby.class, metadata, inits);
    }

    public QPostHobby(Class<? extends PostHobby> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hobby = inits.isInitialized("hobby") ? new com.example.hobbybungae.domain.hobby.entity.QHobby(forProperty("hobby")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

