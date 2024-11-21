package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEduQuiz is a Querydsl query type for EduQuiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEduQuiz extends EntityPathBase<EduQuiz> {

    private static final long serialVersionUID = 976543204L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEduQuiz eduQuiz = new QEduQuiz("eduQuiz");

    public final StringPath answer = createString("answer");

    public final StringPath contents = createString("contents");

    public final StringPath eduType = createString("eduType");

    public final QEduContents group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QEduQuiz(String variable) {
        this(EduQuiz.class, forVariable(variable), INITS);
    }

    public QEduQuiz(Path<? extends EduQuiz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEduQuiz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEduQuiz(PathMetadata metadata, PathInits inits) {
        this(EduQuiz.class, metadata, inits);
    }

    public QEduQuiz(Class<? extends EduQuiz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QEduContents(forProperty("group")) : null;
    }

}

