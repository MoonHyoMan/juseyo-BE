package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChildLevel is a Querydsl query type for ChildLevel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChildLevel extends EntityPathBase<ChildLevel> {

    private static final long serialVersionUID = -1916807569L;

    public static final QChildLevel childLevel = new QChildLevel("childLevel");

    public final NumberPath<Integer> exp = createNumber("exp", Integer.class);

    public final StringPath id = createString("id");

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public QChildLevel(String variable) {
        super(ChildLevel.class, forVariable(variable));
    }

    public QChildLevel(Path<? extends ChildLevel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChildLevel(PathMetadata metadata) {
        super(ChildLevel.class, metadata);
    }

}

