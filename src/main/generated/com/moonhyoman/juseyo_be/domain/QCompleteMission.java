package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompleteMission is a Querydsl query type for CompleteMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompleteMission extends EntityPathBase<CompleteMission> {

    private static final long serialVersionUID = 2050590668L;

    public static final QCompleteMission completeMission = new QCompleteMission("completeMission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath doneDate = createString("doneDate");

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QCompleteMission(String variable) {
        super(CompleteMission.class, forVariable(variable));
    }

    public QCompleteMission(Path<? extends CompleteMission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompleteMission(PathMetadata metadata) {
        super(CompleteMission.class, metadata);
    }

}

