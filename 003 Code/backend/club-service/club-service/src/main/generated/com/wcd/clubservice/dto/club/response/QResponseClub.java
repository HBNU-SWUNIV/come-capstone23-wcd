package com.wcd.clubservice.dto.club.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wcd.clubservice.dto.club.response.QResponseClub is a Querydsl Projection type for ResponseClub
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResponseClub extends ConstructorExpression<ResponseClub> {

    private static final long serialVersionUID = 245441197L;

    public QResponseClub(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Long> hostId, com.querydsl.core.types.Expression<String> clubName, com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> mainImageUrl, com.querydsl.core.types.Expression<com.wcd.clubservice.enums.ApprovalMethod> approvalMethod, com.querydsl.core.types.Expression<Integer> maximumPeople, com.querydsl.core.types.Expression<Boolean> recruitment, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ResponseClub.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, com.wcd.clubservice.enums.ApprovalMethod.class, int.class, boolean.class, java.time.LocalDateTime.class}, id, hostId, clubName, category, description, mainImageUrl, approvalMethod, maximumPeople, recruitment, createdAt);
    }

}

