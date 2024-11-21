package com.moonhyoman.juseyo_be.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserAccount is a Querydsl query type for UserAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAccount extends EntityPathBase<UserAccount> {

    private static final long serialVersionUID = 726856219L;

    public static final QUserAccount userAccount = new QUserAccount("userAccount");

    public final StringPath accountNum = createString("accountNum");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath depositer = createString("depositer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QUserAccount(String variable) {
        super(UserAccount.class, forVariable(variable));
    }

    public QUserAccount(Path<? extends UserAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAccount(PathMetadata metadata) {
        super(UserAccount.class, metadata);
    }

}

