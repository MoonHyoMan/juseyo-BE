package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFailMission is a Querydsl query type for FailMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFailMission extends EntityPathBase<FailMission> {

    private static final long serialVersionUID = -1401410873L;

    public static final QFailMission failMission = new QFailMission("failMission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath endDate = createString("endDate");

    public final StringPath failDate = createString("failDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QFailMission(String variable) {
        super(FailMission.class, forVariable(variable));
    }

    public QFailMission(Path<? extends FailMission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFailMission(PathMetadata metadata) {
        super(FailMission.class, metadata);
    }

}

