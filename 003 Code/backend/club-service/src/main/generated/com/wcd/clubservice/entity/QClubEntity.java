package com.wcd.clubservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubEntity is a Querydsl query type for ClubEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubEntity extends EntityPathBase<ClubEntity> {

    private static final long serialVersionUID = 134131410L;

    public static final QClubEntity clubEntity = new QClubEntity("clubEntity");

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

    public QClubEntity(String variable) {
        super(ClubEntity.class, forVariable(variable));
    }

    public QClubEntity(Path<? extends ClubEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubEntity(PathMetadata metadata) {
        super(ClubEntity.class, metadata);
    }

}

