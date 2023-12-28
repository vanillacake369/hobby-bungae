package com.example.hobbybungae.domain.userProfile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHobby is a Querydsl query type for UserHobby
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserHobby extends EntityPathBase<UserHobby> {

    private static final long serialVersionUID = -468633927L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHobby userHobby = new QUserHobby("userHobby");

    public final com.example.hobbybungae.domain.hobby.entity.QHobby hobby;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.hobbybungae.domain.user.entity.QUser user;

    public QUserHobby(String variable) {
        this(UserHobby.class, forVariable(variable), INITS);
    }

    public QUserHobby(Path<? extends UserHobby> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHobby(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHobby(PathMetadata metadata, PathInits inits) {
        this(UserHobby.class, metadata, inits);
    }

    public QUserHobby(Class<? extends UserHobby> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hobby = inits.isInitialized("hobby") ? new com.example.hobbybungae.domain.hobby.entity.QHobby(forProperty("hobby")) : null;
        this.user = inits.isInitialized("user") ? new com.example.hobbybungae.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

