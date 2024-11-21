package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEduContents is a Querydsl query type for EduContents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEduContents extends EntityPathBase<EduContents> {

    private static final long serialVersionUID = 1404614441L;

    public static final QEduContents eduContents = new QEduContents("eduContents");

    public final StringPath category = createString("category");

    public final StringPath eduType = createString("eduType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath link = createString("link");

    public final ListPath<EduQuiz, QEduQuiz> quiz = this.<EduQuiz, QEduQuiz>createList("quiz", EduQuiz.class, QEduQuiz.class, PathInits.DIRECT2);

    public final StringPath titles = createString("titles");

    public QEduContents(String variable) {
        super(EduContents.class, forVariable(variable));
    }

    public QEduContents(Path<? extends EduContents> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEduContents(PathMetadata metadata) {
        super(EduContents.class, metadata);
    }

}

