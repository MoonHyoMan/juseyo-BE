package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRequestMission is a Querydsl query type for RequestMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequestMission extends EntityPathBase<RequestMission> {

    private static final long serialVersionUID = 2105711396L;

    public static final QRequestMission requestMission = new QRequestMission("requestMission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QRequestMission(String variable) {
        super(RequestMission.class, forVariable(variable));
    }

    public QRequestMission(Path<? extends RequestMission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequestMission(PathMetadata metadata) {
        super(RequestMission.class, metadata);
    }

}

