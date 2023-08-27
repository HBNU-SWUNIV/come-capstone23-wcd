package com.wcd.clubservice.entity;

import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.enums.ApprovalMethod;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false, unique = true)
    private String clubName;

    @Column(nullable = false)
    private String category;

    @Lob
    private String description;

    private String mainImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalMethod approvalMethod;

    @Column(nullable = false)
    private int maximumPeople;

    private boolean recruitment;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubMember> clubMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();

    @Builder
    public Club(Long hostId, String clubName, String category, String description, String mainImageUrl, ApprovalMethod approvalMethod, int maximumPeople, boolean recruitment, LocalDateTime createdAt) {
        this.hostId = hostId;
        this.clubName = clubName;
        this.category = category;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.approvalMethod = approvalMethod;
        this.maximumPeople = maximumPeople;
        this.recruitment = recruitment;
        this.createdAt = createdAt;
    }

    // 연관관계 편의 메서드
    public void addTag(Tag tag) {
        this.tagList.add(tag);
        tag.changeClub(this);
    }

    // 모임 정보 업데이트
    public void updateClub(RequestUpdateClub requestUpdateClub, String mainImageUrl) {
        if (StringUtils.hasText(requestUpdateClub.getClubName())) {
            this.clubName = requestUpdateClub.getClubName();
        }

        if (StringUtils.hasText(requestUpdateClub.getCategory())) {
            this.category = requestUpdateClub.getCategory();
        }

        if (StringUtils.hasText(requestUpdateClub.getClubName())) {
            this.description = requestUpdateClub.getDescription();
        }

        if (StringUtils.hasText(requestUpdateClub.getClubName())) {
            this.mainImageUrl = mainImageUrl;
        }

        if (StringUtils.hasText(requestUpdateClub.getClubName())) {
            this.maximumPeople = requestUpdateClub.getMaximumPeople();
        }

        if (StringUtils.hasText(requestUpdateClub.getClubName())) {
            this.recruitment = requestUpdateClub.isRecruitment();
        }
    }

    public ResponseClub toResponseClub(String hostName) {
        return ResponseClub.builder()
                .id(this.id)
                .hostId(this.hostId)
                .hostName(hostName)
                .clubName(this.clubName)
                .category(this.category)
                .description(this.description)
                .mainImageUrl(this.mainImageUrl)
                .approvalMethod(this.approvalMethod)
                .maximumPeople(this.maximumPeople)
                .recruitment(this.recruitment)
                .createdAt(this.createdAt)
                .build();

    }
}
