package com.wcd.clubservice.repository.club;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.dto.club.response.QResponseClub;
import com.wcd.clubservice.entity.Club;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.wcd.clubservice.entity.QClub.*;

public class ClubRepositoryImpl implements ClubCustomRepository {

    private JPAQueryFactory queryFactory;

    public ClubRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ResponseClub> getClubs(ClubSearchCondition condition, Pageable pageable) {
        List<ResponseClub> content = queryFactory
                .select(new QResponseClub(
                        club.id,
                        club.hostId,
                        club.clubName,
                        club.category,
                        club.description,
                        club.mainImageUrl,
                        club.approvalMethod,
                        club.maximumPeople,
                        club.recruitment,
                        club.createdAt
                ))
                .from(club)
                .where(
                        search(condition.getSearch()),
                        categoryEq(condition.getCategory())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Club> countQuery = queryFactory
                .select(club)
                .from(club)
                .where(
                        search(condition.getSearch()),
                        categoryEq(condition.getCategory())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression search(String search) {
        return StringUtils.hasText(search) ? club.clubName.likeIgnoreCase("%" + search + "%") : null;
    }

    private BooleanExpression categoryEq(String category) {
        return StringUtils.hasText(category) ? club.category.eq(category): null;
    }
}
