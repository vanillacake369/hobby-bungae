package com.example.hobbybungae.domain.state.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QState is a Querydsl query type for State
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QState extends EntityPathBase<State> {

    private static final long serialVersionUID = 688679664L;

    public static final QState state = new QState("state");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath stateDo = createString("stateDo");

    public final StringPath stateGu = createString("stateGu");

    public final StringPath stateSi = createString("stateSi");

    public QState(String variable) {
        super(State.class, forVariable(variable));
    }

    public QState(Path<? extends State> path) {
        super(path.getType(), path.getMetadata());
    }

    public QState(PathMetadata metadata) {
        super(State.class, metadata);
    }

}

