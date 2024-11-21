package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMission is a Querydsl query type for Mission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMission extends EntityPathBase<Mission> {

    private static final long serialVersionUID = -371061691L;

    public static final QMission mission = new QMission("mission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QMission(String variable) {
        super(Mission.class, forVariable(variable));
    }

    public QMission(Path<? extends Mission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMission(PathMetadata metadata) {
        super(Mission.class, metadata);
    }

}

