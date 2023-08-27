package com.wcd.clubservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = 851633679L;

    public static final QClub club = new QClub("club");

    public final EnumPath<com.wcd.clubservice.enums.ApprovalMethod> approvalMethod = createEnum("approvalMethod", com.wcd.clubservice.enums.ApprovalMethod.class);

    public final StringPath category = createString("category");

    public final ListPath<ClubMember, QClubMember> clubMemberList = this.<ClubMember, QClubMember>createList("clubMemberList", ClubMember.class, QClubMember.class, PathInits.DIRECT2);

    public final StringPath clubName = createString("clubName");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mainImageUrl = createString("mainImageUrl");

    public final NumberPath<Integer> maximumPeople = createNumber("maximumPeople", Integer.class);

    public final BooleanPath recruitment = createBoolean("recruitment");

    public final ListPath<Tag, QTag> tagList = this.<Tag, QTag>createList("tagList", Tag.class, QTag.class, PathInits.DIRECT2);

    public QClub(String variable) {
        super(Club.class, forVariable(variable));
    }

    public QClub(Path<? extends Club> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClub(PathMetadata metadata) {
        super(Club.class, metadata);
    }

}

