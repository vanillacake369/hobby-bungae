package com.example.hobbybungae.domain.hobby.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHobby is a Querydsl query type for Hobby
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHobby extends EntityPathBase<Hobby> {

    private static final long serialVersionUID = 1481718928L;

    public static final QHobby hobby = new QHobby("hobby");

    public final StringPath hobbyName = createString("hobbyName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.example.hobbybungae.domain.post.entity.PostHobby, com.example.hobbybungae.domain.post.entity.QPostHobby> postHobbies = this.<com.example.hobbybungae.domain.post.entity.PostHobby, com.example.hobbybungae.domain.post.entity.QPostHobby>createList("postHobbies", com.example.hobbybungae.domain.post.entity.PostHobby.class, com.example.hobbybungae.domain.post.entity.QPostHobby.class, PathInits.DIRECT2);

    public final ListPath<com.example.hobbybungae.domain.userProfile.entity.UserHobby, com.example.hobbybungae.domain.userProfile.entity.QUserHobby> userHobbies = this.<com.example.hobbybungae.domain.userProfile.entity.UserHobby, com.example.hobbybungae.domain.userProfile.entity.QUserHobby>createList("userHobbies", com.example.hobbybungae.domain.userProfile.entity.UserHobby.class, com.example.hobbybungae.domain.userProfile.entity.QUserHobby.class, PathInits.DIRECT2);

    public QHobby(String variable) {
        super(Hobby.class, forVariable(variable));
    }

    public QHobby(Path<? extends Hobby> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHobby(PathMetadata metadata) {
        super(Hobby.class, metadata);
    }

}

