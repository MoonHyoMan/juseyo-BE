package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRejectRequestMission is a Querydsl query type for RejectRequestMission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRejectRequestMission extends EntityPathBase<RejectRequestMission> {

    private static final long serialVersionUID = -123642013L;

    public static final QRejectRequestMission rejectRequestMission = new QRejectRequestMission("rejectRequestMission");

    public final StringPath category = createString("category");

    public final StringPath childId = createString("childId");

    public final StringPath content = createString("content");

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath parentId = createString("parentId");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final StringPath startDate = createString("startDate");

    public QRejectRequestMission(String variable) {
        super(RejectRequestMission.class, forVariable(variable));
    }

    public QRejectRequestMission(Path<? extends RejectRequestMission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRejectRequestMission(PathMetadata metadata) {
        super(RejectRequestMission.class, metadata);
    }

}

