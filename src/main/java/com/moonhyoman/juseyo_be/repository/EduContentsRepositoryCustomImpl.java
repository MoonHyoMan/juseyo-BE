package com.moonhyoman.juseyo_be.repository;
import com.moonhyoman.juseyo_be.domain.QEduContents;
import com.moonhyoman.juseyo_be.domain.QEduQuiz;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.moonhyoman.juseyo_be.domain.EduContents;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class EduContentsRepositoryCustomImpl implements EduContentsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EduContents> findAllByEduType(String eduType) {
        QEduContents eduContents = QEduContents.eduContents;
        QEduQuiz eduQuiz = QEduQuiz.eduQuiz;

        return jpaQueryFactory.selectFrom(eduContents)
                .leftJoin(eduContents.quiz, eduQuiz).fetchJoin()
                .where(eduContents.eduType.eq(eduType))
                .distinct()
                .fetch();
    }
}
