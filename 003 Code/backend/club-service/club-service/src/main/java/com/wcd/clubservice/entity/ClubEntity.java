package com.wcd.clubservice.entity;

import com.wcd.clubservice.enums.ApprovalMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="club")
public class ClubEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
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
    private List<ClubMemberEntity> clubMemberList;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagEntity> tagList;
}
