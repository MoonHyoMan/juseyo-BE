package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSuccessMission is a Querydsl query type for SuccessMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSuccessMission extends EntityPathBase<SuccessMission> {

    private static final long serialVersionUID = -68943056L;

    public static final QSuccessMission successMission = new QSuccessMission("successMission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath doneDate = createString("doneDate");

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QSuccessMission(String variable) {
        super(SuccessMission.class, forVariable(variable));
    }

    public QSuccessMission(Path<? extends SuccessMission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSuccessMission(PathMetadata metadata) {
        super(SuccessMission.class, metadata);
    }

}

